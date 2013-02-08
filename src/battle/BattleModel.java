package battle;

import graphics.BattleFX;
import graphics.FXRenderer;
import item.ItemType;
import item.component.ItemComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.GameModel;
import model.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import overworld.MonsterGroup;
import overworld.MonsterSpec;
import util.GameConstants;
import util.Pair;
import util.U;
import NetworkUtil.Action;
import ability.effect.AbilityEffect;
import audio.SFXPlayer;
import client.ActionType;
import client.EpicGameContainer;
import content.BattleMapLoader;


/** The model in our MVC design that represents a battle. <br>
 * Each battle will have one instance of this class. <br>
 * Contains data describing the exact state of the battle. <br>
 * Object is destroyed when the battle is over (when no players are left in the battle arena). <br>
 * 
 * There can only be one battle model in existence at once, so this is a singleton. <br>
 */
public class BattleModel {
	/** The singleton battle model. There should not be more than one battle model active at one time. */
	public static BattleModel MAIN;
	
	/** Map of creature IDs to creatures. */
	public HashMap<Integer, BattleCreature> creatures;
	/** Map of projectile IDs to projectiles. */
	public HashMap<Integer, AbilityEffect> abilityEffects;

	/** List of FXs to render */
	public FXRenderer fxRenderer;
	
	/** The battle map we're currently using. */
	public BattleTileMap map;
	
	
	/** The items this player has gained during the battle, for the winning screen at the end */
	public ArrayList<String> itemsGainedThisBattle; 
	
	/** The monster group that spawned this battle. */
	public final MonsterGroup mg;
	/** True if the player has finished channeling his retreat ability. <br>
	 * On the next update, the battle should end. */
	public boolean retreatFlag;
	/** Tick on which the battle ended. -1 if it has not ended yet. */
	public int endTick;
	
	public float playerAPCostMultiplier = 1;
	public float playerAttackDamageMultiplier = 1;
	public float playerTakeDamageMultiplier = 1;
	public float playerCritChance = 0;
	public float playerCritMultiplier = 1;
	
	public BattleModel(MonsterGroup mg) {
		MAIN = this;
		
		this.creatures = new HashMap<Integer, BattleCreature>();
		this.abilityEffects = new HashMap<Integer, AbilityEffect>();
		
		this.fxRenderer = new FXRenderer();
		
		this.map = BattleMapLoader.get(mg.battleMapID, mg.zone);

		this.itemsGainedThisBattle = new ArrayList<String>();
		
		this.mg = mg;
		this.endTick = -1;
		
		BattlePlayer bpl = new BattlePlayer(GameModel.MAIN.player, mg.playerSpawnZone);
		creatures.put(bpl.id, bpl);
		for(MonsterSpec spec: mg.monsters.values()) {
			BattleCreature bcr = new BattleCreature(spec);
			creatures.put(bcr.id, bcr);
		}
		for(ItemType type: bpl.equipment) {
			if(type==null) continue;
			for(ItemComponent component: type.components) {
				component.init(bpl);
			}
		}
	}
	
	/** Adds an ability effect to the battle. */
	public void addAbilityEffect(AbilityEffect ae) {
		abilityEffects.put(ae.id, ae);
	}
	
	/** Adds an effect to draw */
	public void addFX(BattleFX fx) {
		fxRenderer.add(fx);
	}
	
	/** Removes all expired ability effects from existence. */
	public void removeExpiredAbilityEffects() {
		List<AbilityEffect> abilityEffectsToRemove = new ArrayList<AbilityEffect>();
		for(AbilityEffect ae : abilityEffects.values()) {
			if(!ae.alive)
				abilityEffectsToRemove.add(ae);
		}
		for(AbilityEffect ae: abilityEffectsToRemove) {
			abilityEffects.remove(ae.id);
		}
	}
	
	public void render(GameContainer gc, Graphics g) {
		BattleCreature me = creatures.get(EpicGameContainer.MAIN.myID);
		
		int minY = (int) Math.min(me.my(),
				map.getHeight() * BattleConstants.TILE_HEIGHT - GameConstants.SCREEN_HEIGHT/2);
		int minX = U.minmax(
				0 + GameConstants.SCREEN_WIDTH/2,
				map.getWidth() * BattleConstants.TILE_WIDTH - GameConstants.SCREEN_WIDTH/2,
				me.mx());
		
		map.render(gc, g, minX, minY);
		for(BattleCreature cr: creatures.values()) {
			cr.render(gc, g);
		}
		for(AbilityEffect ae : abilityEffects.values()) {
			ae.render(gc, g);
		}
		fxRenderer.render(gc, g);
	}
	
	/** Updates the battle one tick. <br>
	 * This method contains the battle physics engine and collision detection. <br>
	 * 
	 * Returns whether the battle is over.
	 */
	public boolean update() {
		float dt = GameConstants.GAME_SPEED/1000.0f;
		Player pl = GameModel.MAIN.player;
		
		for(BattleCreature cr: creatures.values()) {
			if(cr.alive) cr.update();
			
			// If creature is affected by an impulse, process it
			if(cr.impulse!=null) {
				float oldpx = cr.getScreenX();
				float oldpy = cr.getScreenY();
				cr.setScreenX(cr.getScreenX()+cr.impulse.vx*dt);
				cr.setScreenY(cr.getScreenY()+cr.impulse.vy*dt);
				if(map.collideRectWall(cr)) {
					cr.impulse = null;
					cr.setScreenX(oldpx);
					cr.setScreenY(oldpy);
				} else {
					if(!cr.impulse.isInEffect())
						cr.impulse = null;
				}
			} else {
				
				if(cr.alive) {
					if (cr.spellChannel != null) {
						// If there is a pending spell, process it
						SpellChannel sp = cr.spellChannel;
						if(!sp.isChanneling()) sp.castAbility();
					}
					if ((cr.type==null || cr.type.power>=15 || cr.freezeDuration==0) && (
							cr.spellChannel == null || !cr.spellChannel.isStunned())) {
						// Move left or right if the creature is trying to
						if(cr.tryingToMoveLeft || cr.tryingToMoveRight) {
							float oldpx = cr.getScreenX();
							if(!cr.inAir || BattleConstants.PLAYER_CAN_TURN_IN_MIDAIR) {
								if(cr.moonwalking)
									cr.facingRight = !cr.tryingToMoveRight;
								else
									cr.facingRight = cr.tryingToMoveRight;
							}
							cr.setScreenX(cr.getScreenX()+(cr.tryingToMoveRight?1:-1)*cr.getMoveSpeed()*dt);
							if(map.collideRectWall(cr)) {
								cr.setScreenX(oldpx);
							}
						}
						if ((cr.tryingToMoveUp || cr.tryingToMoveDown) && cr.type.flying) {
							float oldpy = cr.getScreenY();
							cr.setScreenY(cr.getScreenY()+(cr.tryingToMoveDown?1:-1)*cr.getMoveSpeed()*dt);
							if(map.collideRectWall(cr)) {
								cr.setScreenY(oldpy);
							}
						}
					}
				}
				
				// If creature is on the ground, check to see if it's still on the ground
				if(!cr.inAir) {
					if(!map.collideRectPlatformAndWall(cr.getScreenX(), cr.getScreenY()+cr.getHeight(), cr.getWidth(), BattleConstants.TILE_HEIGHT/3)) {
						cr.inAir = true;
					}
				}
				
				// If creature is in the air and can't fly, apply vertical velocity
				if(cr.inAir && !(cr.type!=null && cr.type.flying)) {
					cr.vy += BattleConstants.GRAVITY*dt;
					cr.vy -= cr.vy*BattleConstants.AIR_RESISTANCE*dt;
					float oldpy = cr.getScreenY();
					cr.setScreenY(cr.getScreenY()+cr.vy*dt);
					if(cr.vy > 0) {
						if(map.collideRectPlatformAndWall(cr.getScreenX(), oldpy+cr.getHeight(), cr.getWidth(), cr.getScreenY()-oldpy)) {
							// Creature landed on the ground
							cr.inAir = false;
							cr.airJumpCounter = 0;
							cr.setScreenY(map.getLandingHeightForRect(cr.getScreenX(), oldpy, cr.getWidth(), cr.getHeight())-cr.getHeight());
							if(cr.vy > BattleConstants.LANDING_DELAY_THRESHOLD) {
								// Apply stun from landing at too high a velocity
								int delay = (int)((cr.vy-cr.fallStunThreshold)*
										BattleConstants.LANDING_DELAY_MULTIPLIER * cr.fallStunMultiplier);
								cr.applyImpulse(0, 0, ImpulseForce.LANDING_DELAY, delay, delay);
							}
							cr.vy = 0;
						}
					} else {
						if(map.collideRectWall(cr)) {
							// Creature bumped into a barrier from below
							cr.setScreenY(oldpy);
							cr.vy = 0;
						}
					}
				}
			}
		}
		
		// Process ability effects
		List<AbilityEffect> effects = new ArrayList<AbilityEffect>();
		for(AbilityEffect ae: abilityEffects.values()) {
			effects.add(ae);
		}
		for(AbilityEffect ae: effects) {
			ae.update(this);
		}
		removeExpiredAbilityEffects();
		
		// Process graphical effects
		fxRenderer.update();
		
		// Apply passive effects from players' equipment
		for(BattleCreature cr: creatures.values()) {
			if(cr instanceof BattlePlayer) {
				BattlePlayer bpl = (BattlePlayer)cr;
				for(ItemType type: bpl.equipment) {
					if(type==null) continue;
					for(ItemComponent component: type.components) {
						component.update(bpl);
					}
				}
			}
		}
		
		// Check if the battle is over
		if(endTick==-1 && isBattleOver()) {
			endTick = GameModel.MAIN.tickNum;
			SFXPlayer.play("victory");
		}
		
		if(endTick!=-1 && GameModel.MAIN.tickNum>=endTick+BattleConstants.TICKS_AFTER_BATTLE) {
			// Remove dead creatures from overworld
			for(BattleCreature cr: creatures.values()) {
				if(cr.alive) continue;
				if(cr instanceof BattlePlayer) continue;
				
				// An enemy has been killed in battle
				GameModel.MAIN.incrementKillCount(cr.type.name);
				//pl.lootEnemy(cr); //This is now done when a creature is killed
				mg.monsters.remove(cr.specID);
			}
			
			for(BattleCreature cr: creatures.values()) {
				if(!(cr instanceof BattlePlayer)) continue;
				
				GameModel.MAIN.endBattle(retreatFlag, cr.hp);
				return true;
			}
			
		}
		return false;
	}
	
	public boolean isBattleOver() {
		if(retreatFlag) return true;
		int winningTeam = -1;
		for(BattleCreature cr: creatures.values()) {
			if(!cr.alive) continue;
			if(cr.team!=winningTeam && winningTeam!=-1) return false;
			winningTeam = cr.team;
		}
		return true;
	}
	
	/** Applies a battle action that a creature in this battle is doing. */
	public void applyAction(Action action) {
		BattleCreature cr = creatures.get(action.userID);
		if(cr.type!=null && cr.type.power<15 && cr.freezeDuration>0) return;
		
		BattleAbility ab = null;
		switch(action.type) {
		case RETREAT:
			if(ab==null) ab = ((BattlePlayer)cr).retreatAbility;
		case BASIC_ATTACK:
			if(ab==null) ab = ((BattlePlayer)cr).basicAttack;
		case DASH:
			if(ab==null) ab = ((BattlePlayer)cr).dashAbility;
			
			// The above abilities are only available to players
			if(!(cr instanceof BattlePlayer)) break;
			
		case ABILITY:
			// ALIVE check
			if(!cr.alive) break;
			
			// STUN checks
			if(cr.impulse!=null && cr.impulse.isStunInEffect()) break;
			if(cr.spellChannel != null) break;
			
			int i=0;
			if(ab==null) try {
				i = Integer.parseInt(action.arg);
			} catch(Exception e) {break;}
			
			// Valid ability check
			if(i >= cr.abilities.length) break;
			if(ab==null) ab = cr.abilities[i];
			if(ab==null) break;
			
			// AP check
			int apCost = (int)Math.ceil(ab.type.apCost*playerAPCostMultiplier);
			if(!cr.enoughAP(apCost)) break;
			
			// COOLDOWN check
			if(GameModel.MAIN.tickNum<ab.type.cooldown+ab.tickLastUsed) break;
			if(cr instanceof BattlePlayer && GameModel.MAIN.tickNum<
					cr.tickLastAbilityUsed+BattleConstants.GLOBAL_ABILITY_COOLDOWN &&
					action.type!=ActionType.DASH && action.type!=ActionType.RETREAT) break;
			
			// PAYMENT
			cr.consumeAP(apCost);
			cr.tickLastAbilityUsed = GameModel.MAIN.tickNum;
			
			// QUEUE UP
			cr.spellChannel = new SpellChannel(cr.id, ab, GameModel.MAIN.tickNum);
			
			break;
		default:
			if(!cr.alive) break;
			cr.applyMovementAction(action, this);
			break;
		}
	}
	
	/** Gets the IDs of all living creatures that collide with the given coordinate.
	 * It also returns a boolean denoting whether a damageable hitbox has been hit.
	*/
	public HashSet<Pair<Integer, Float>> collidePoint(float px, float py) {
		HashSet<Pair<Integer, Float>> ret = new HashSet<Pair<Integer, Float>>();
		for(BattleCreature cr: creatures.values()) {
			if(!cr.alive) continue;
			if (!cr.facingRight) {
				for (HitBox hb : cr.hitBoxes) {
					if (hb.pointInHitBox(px, py)) {
						ret.add(new Pair<Integer, Float>(cr.id, hb.damageMultiplier));
						break;
					}
				}
			} else {
				for (HitBox hb : cr.facingRightHitBoxes) {
					if (hb.pointInHitBox(px, py)) {
						ret.add(new Pair<Integer, Float>(cr.id, hb.damageMultiplier));
						break;
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * Gets the IDs of all living creatures that collide with the given line.
	 * @param x1 - The X coordinate of the starting point of the line
	 * @param y1 - The Y coordinate of the starting point of the line
	 * @param x2 - The X coordinate of the ending point of the line
	 * @param y2 - The Y coordinate of the ending point of the line
	 * @return - A Hashset of the creatureID's and whether it hit a damageable hit box
	 */
	public HashSet<Pair<Integer, Float>> collideLine(float x1, float y1, float x2, float y2) {
		HashSet<Pair<Integer, Float>> ret = new HashSet<Pair<Integer, Float>>();
		for(BattleCreature cr: creatures.values()) {
			HitBox nearestToSource = null;
			if(!cr.alive) continue;
			if (!cr.facingRight) {
				for (HitBox hb : cr.hitBoxes) {
					if (hb.lineInHitBox(x1, y1, x2, y2)) {
						if (nearestToSource==null) {
							nearestToSource = hb;
						} else if (nearestToSource.distanceFromHitBox(x1, y1)>hb.distanceFromHitBox(x1, y1)) {
							nearestToSource = hb;
						}
					}
				}
			} else {
				for (HitBox hb : cr.facingRightHitBoxes) {
					if (hb.lineInHitBox(x1, y1, x2, y2)) {
						if (nearestToSource==null) {
							nearestToSource = hb;
						} else if (nearestToSource.distanceFromHitBox(x1, y1)>hb.distanceFromHitBox(x1, y1)) {
							nearestToSource = hb;
						}
					}
				}
			}
			if (nearestToSource!=null) {
				ret.add(new Pair<Integer, Float>(cr.id, nearestToSource.damageMultiplier));
			}
		}
		return ret;
	}
	
	/** Get the IDs of all living creatures that collide within the given circle */
	public HashSet<Pair<Integer, Float>> collideCircle(float x1, float y1, float radius) {
		HashSet<Pair<Integer, Float>> ret = new HashSet<Pair<Integer, Float>>();
		for(BattleCreature cr: creatures.values()) {
			HitBox nearestToSource = null;
			if(!cr.alive) continue;
			if (!cr.facingRight) {
				for (HitBox hb : cr.hitBoxes) {
					if (hb.circleInHitBox(x1, y1, radius)) {
						if (nearestToSource==null) {
							nearestToSource = hb;
						} else if (nearestToSource.distanceFromHitBox(x1, y1)>hb.distanceFromHitBox(x1, y1)) {
							nearestToSource = hb;
						}
					}
				}
			} else {
				for (HitBox hb : cr.facingRightHitBoxes) {
					if (hb.circleInHitBox(x1, y1, radius)) {
						if (nearestToSource==null) {
							nearestToSource = hb;
						} else if (nearestToSource.distanceFromHitBox(x1, y1)>hb.distanceFromHitBox(x1, y1)) {
							nearestToSource = hb;
						}
					}
				}
			}
			if (nearestToSource!=null) {
				ret.add(new Pair<Integer, Float>(cr.id, nearestToSource.damageMultiplier));
			}
		}
		return ret;
	}
	
	/** Get the IDs of all living creatures that collide within the given X Region. 
	 * It ignores the Y coordinate completely 
	*/
	public HashSet<Pair<Integer, Float>> collideXRegion(float x1, float y1, float radius) {
		HashSet<Pair<Integer, Float>> ret = new HashSet<Pair<Integer, Float>>();
		for(BattleCreature cr: creatures.values()) {
			HitBox nearestToSource = null;
			if(!cr.alive) continue;
			if (!cr.facingRight) {
				for (HitBox hb : cr.hitBoxes) {
					if (hb.xRegionInHitBox(x1, radius)) {
						if (nearestToSource==null) {
							nearestToSource = hb;
						} else if (hb.damageMultiplier>0) {
							nearestToSource = hb;
						}
					}
				}
			} else {
				for (HitBox hb : cr.facingRightHitBoxes) {
					if (hb.xRegionInHitBox(x1, radius)) {
						if (nearestToSource==null) {
							nearestToSource = hb;
						} else if (hb.damageMultiplier>0) {
							nearestToSource = hb;
						}
					}
				}
			}
			if (nearestToSource==null) {
				continue;
			} else {
				ret.add(new Pair<Integer, Float>(cr.id, nearestToSource.damageMultiplier));
			}
		}
		return ret;
	}

	/** Gets the IDs of all living creatures that collide with the given rectangle */
	public HashSet<Pair<Integer, Float>> collideRectangle(float x, float y, float width, float height) {
		HashSet<Pair<Integer, Float>> ret = new HashSet<Pair<Integer, Float>>();
		for(BattleCreature cr: creatures.values()) {
			HitBox nearestToSource = null;
			if(!cr.alive) continue;
			if (!cr.facingRight) {
				for (HitBox hb : cr.hitBoxes) {
					if (hb.rectangleInHitBox(x, y, width, height)) {
						if (nearestToSource==null) {
							nearestToSource = hb;
						} else if (nearestToSource.distanceFromHitBox(x, y)>hb.distanceFromHitBox(x, y)) {
							nearestToSource = hb;
						}
					}
				}
			} else {
				for (HitBox hb : cr.facingRightHitBoxes) {
					if (hb.rectangleInHitBox(x, y, width, height)) {
						if (nearestToSource==null) {
							nearestToSource = hb;
						} else if (nearestToSource.distanceFromHitBox(x, y)>hb.distanceFromHitBox(x, y)) {
							nearestToSource = hb;
						}
					}
				}
			}
			if (nearestToSource!=null) {
				ret.add(new Pair<Integer, Float>(cr.id, nearestToSource.damageMultiplier));
			}
		}
		return ret;
	}
}
