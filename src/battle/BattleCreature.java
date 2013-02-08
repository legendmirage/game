package battle;
import java.util.ArrayList;
import java.util.Map;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import overworld.MonsterSpec;
import overworld.OverworldConstants;
import util.Logger;
import util.U;
import NetworkUtil.Action;
import battle.ai.AI;
import client.EpicGameContainer;
import enemy.EnemyType;
import graphics.BattleCreatureRenderer;
import graphics.DamageNumberFX;
import graphics.PopupRenderer;
import graphics.RenderComponent;
import graphics.SpriteFX;
import graphics.StaticFXList;



/** Represents a creature in the battle. <br>
 * Can be a player, or a monster, or a summon, or whatever. <br>
 * This is created at the beginning of the battle and destroyed at the end, it does not persist. <br>
 */
public class BattleCreature {
	/** Hidden counter to make sure each instance of this class has a unique id. <br>
	 * Start with an sufficiently large so that small numbers are reserved for players. 
	 */
	private static int objectCount = 1000000000;
	
	/** Unique identifier. */
	public int id;
	/** ID of the MonsterSpec that created this creature, or -1 if it does not exist. */
	public int specID;
	/** Team number. By convention, monsters are all on team 0 and players are all on team 1. */
	public int team;
	/** x position in pixel coordinates. */
	private float px;
	/** y position in pixel coordinates. */
	private float py;
	/** x velocity in pixels per second. */
	public float vx;
	/** y velocity in pixels per second. */
	public float vy;
	/** Pixel width of the object. Will be used for collision. */
	protected float width;
	/** Pixel height of the object. Will be used for collision. */
	protected float height;
	/** Is this object in freefall? Or is it moving on the ground or on some platform? */
	public boolean inAir;
	/** The number of times this creature has air jumped before it has hit the ground. */
	public int airJumpCounter;
	/** The number of times this creature is allowed to air jump after leaving the ground. */
	public int maxAirJumps;
	/** The horizontal speed of this creature, in pixels per second. */
	public float moveSpeed;
	
	/** Is the creature alive? */
	public boolean alive;
	/** Current health of the creature. Should always be between 0 and maxHP. */
	public int hp;
	/** Maximum health of the creature. */
	public int maxHP;
	/** Current action points of the creature. Should always be between 0 and maxAP. */
	public int ap;
	/** Maximum action points of the creature. */
	public int maxAP;
	
	/** Does the creature have a shield? */
	public boolean shield;
	/** Duration of freeze caused by Deep Freeze */
	public int freezeDuration;
	/** Multiplier to next damage from Ice Shield */
	public float iceShieldMultiplier;
	/** Damage multiplier from taunts. Should be at least 1. */
	public float tauntDamageMultiplier;
	/** Move speed multiplier from taunts. Should be at least 1. */
	public float tauntMoveSpeedMultiplier;
	/** Move speed multiplier from Hook. */
	public float hookSpeedMultiplier;
	/** Number of ticks before taunt effects are reset. */
	public int tauntTicksLeft;
	/** Boolean whether Grapple Hook is cast */
	public boolean grappleHook;
	
	/** A count for damage multiplier */
	public int damageBuffer;
	/** The knockback speed of the basic attack. Only applicable for BattlePlayers. */
	public float basicAttackKnockbackSpeed;
	/** The knockback duration of the basic attack. Only applicable for BattlePlayers. */
	public int basicAttackKnockbackDuration;
	/** AP cost of air jumping. */
	public int airJumpCost;
	/** AP cost of dashing. */
	public int dashCost;
	/** Damage of the basic attack. Only applicable for BattlePlayers. */
	public int basicAttackDamage;
	/** The multiplier for move speed */
	public float moveSpeedMultiplier;
	/** The multiplier for move speed from Slow effects */
	public float slowEffectMultiplier;
	/** Multiplier applied to all damage dealt to this creature. */
	public float damageMultiplier;
	/** Number of ticks that the dash lasts. */
	public int dashDuration;
	/** Landing speed above which the creature is stunned on land impact. */
	public int fallStunThreshold;
	/** Multiplier to the stun duration of the fall stun. */
	public float fallStunMultiplier;
	/** Number of ticks all damage based impulses are reduced by. */
	public int stunReduction;
	/** Amount of damage reduced before multipliers are used. */
	public int damageReduction;
	/** The jump speed of the creature */
	public float jumpSpeed;
	
	/** Is the creature wanting to moonwalk? */
	public boolean moonwalking;
	/** True if creature is facing right, false if it's facing left. */
	public boolean facingRight;
	/** Does the player have the go left key press down? */
	public boolean tryingToMoveLeft;
	/** Does the player have the go right key press down? */
	public boolean tryingToMoveRight;
	/** Is the creature trying to move up? */
	public boolean tryingToMoveUp;
	/** Is the creature trying to move down? */
	public boolean tryingToMoveDown;
	/** The impulses currently affecting the creature. There can only be one. */
	public ImpulseForce impulse;
	/** The ability currently being channeled. There can only be one. */
	public SpellChannel spellChannel;
	/** The tick on which the last ability was used. */
	public int tickLastAbilityUsed;
	
	/** The type of enemy this is. */
	public EnemyType type;
	/** Any special item that this enemy always drops on death. */
	public String specialDrop;
	/** The AI instance controlling the behavior of this creature. */
	public AI ai;
	/** The abilities that the creature can use in battle. */
	public BattleAbility[] abilities;
	
	
	/** An array list of battle hit boxes when facing left*/
	public ArrayList<HitBox> hitBoxes;
	/** An array list of battle hit boxes when facing right*/
	public ArrayList<HitBox> facingRightHitBoxes;
	
	/** Renderable object of the creature. This will need to be expanded to capture image states, etc. */
	public RenderComponent renderer;
	
	public BattleCreature(MonsterSpec spec) {
		this(spec.spawnZone, spec.type.maxHP, spec.type.maxHP, spec.type.animationMap, spec.type);
		this.specID = spec.id;
		this.specialDrop = spec.specialDrop;
		this.moveSpeed = OverworldConstants.PLAYER_BASE_MOVE_SPEED * 
				BattleConstants.PLAYER_MOVE_SPEED / type.overworldSpeed;
		this.jumpSpeed = type.jumpVelocity;
		this.stunReduction = (int)Math.pow(1.5, type.power);
		this.abilities = new BattleAbility[type.abilities.size()];
		for(int i=0; i<type.abilities.size(); i++) {
			this.abilities[i] = new BattleAbility(type.abilities.get(i));
		}
		this.tickLastAbilityUsed = Integer.MIN_VALUE/2;
		try {
			this.ai = type.ai.getDeclaredConstructor(BattleCreature.class).newInstance(this);
		} catch(Exception e) {
			Logger.err("ERROR: failed to create AI instance for the enemy "+type.name);
			e.printStackTrace();
		}
	}
	
	/** Creates a creature at a given pixel coordinate. */
	protected BattleCreature(int spawnZone, int startHP, int maxHP, Map animationMap, EnemyType type) {
		this.type = type;
		this.id = objectCount++;
		this.specID = -1;
		this.team = 0;
		this.hitBoxes = new ArrayList<HitBox>();
		this.facingRightHitBoxes = new ArrayList<HitBox>();
		if(animationMap != null) {
			this.renderer = new BattleCreatureRenderer(this, animationMap);
		}
		if (this.hitBoxes.isEmpty() || this.facingRightHitBoxes.isEmpty()) {
			Logger.err("Should not be empty : HITBOXES");
		}
		for (HitBox hb : hitBoxes) {
			hb.setCreature(this);
		}
		for (HitBox hb : facingRightHitBoxes) {
			hb.setCreature(this);
		}
		this.width = setWidth();
		this.height = setHeight();
		Rectangle rect = BattleModel.MAIN.map.getSpawn(spawnZone);
		this.px = rect.getCenterX()*BattleConstants.TILE_WIDTH-getWidth()/2;
		float widthInTiles = getWidth()/BattleConstants.TILE_WIDTH;
		if(widthInTiles<rect.getWidth())
			this.px = (rect.getMinX()+((float)U.r())*(rect.getWidth()-widthInTiles))*
					BattleConstants.TILE_WIDTH;
		this.py = (rect.getMinY()+((float)U.r())*(rect.getHeight()))*
				BattleConstants.TILE_HEIGHT - height;
		this.vx = 0;
		this.vy = 0;
		this.inAir = true;
		this.airJumpCounter = 0;
		this.maxAirJumps = 0;
		this.moveSpeed = 1000;
		this.moveSpeedMultiplier = 1;
		this.slowEffectMultiplier = 1;
		this.damageMultiplier = 1;
		
		this.alive = true;
		this.maxHP = maxHP;
		this.hp = startHP;
		this.maxAP = 55555;
		this.ap = 55555;
		
		this.shield = false;
		this.freezeDuration = 0;
		this.iceShieldMultiplier = 1;
		this.tauntDamageMultiplier = 1;
		this.tauntMoveSpeedMultiplier = 1;
		this.hookSpeedMultiplier = 1;
		this.tauntTicksLeft = 0;
		this.grappleHook = false;
		
		this.basicAttackKnockbackSpeed = BattleConstants.BASIC_ATTACK_KNOCKBACK_SPEED;
		this.basicAttackKnockbackDuration = BattleConstants.BASIC_ATTACK_KNOCKBACK_DURATION;
		this.airJumpCost = BattleConstants.AIR_JUMP_AP_COST;
		this.dashCost = BattleConstants.DASH_AP_COST;
		this.basicAttackDamage = BattleConstants.PLAYER_BASIC_ATTACK_DAMAGE;
		this.moveSpeedMultiplier = 1;
		this.damageMultiplier = 1;
		this.dashDuration = BattleConstants.PLAYER_DASH_DURATION;
		this.fallStunThreshold = BattleConstants.LANDING_DELAY_THRESHOLD;
		this.fallStunMultiplier = 1;
		this.stunReduction = 0;
		this.damageReduction = 0;

		this.moonwalking = false;
		this.facingRight = px < BattleModel.MAIN.map.getWidth()*BattleConstants.TILE_WIDTH/2;
		this.tryingToMoveLeft = false;
		this.tryingToMoveRight = false;
		this.tryingToMoveUp = false;
		this.tryingToMoveDown = false;
		this.impulse = null;
		this.spellChannel = null;
		
		this.specialDrop = null;
		this.ai = null;
		this.abilities = null;
	
	}
	
	/** Creates a creature at a given pixel coordinate. */
	protected BattleCreature(int spawnZone, int startHP, int maxHP, Map animationMap) {
		this(spawnZone, startHP, maxHP, animationMap, null);
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null || !(o instanceof BattleCreature)) return false;
		BattleCreature other = (BattleCreature)o;
		return id==other.id;
	}
	/** Is the given creature on the same team as this creature? */
	public boolean sameTeam(BattleCreature cr) {
		return team==cr.team;
	}
	
	/** Returns the x coordinate of the center of mass of the creature. */
	public float mx() {
		return this.getScreenX()+this.getWidth()/2;
	}
	/** Returns the y coordinate of the center of mass of the creature. */
	public float my() {
		return this.getScreenY()+this.getHeight()/2;
	}
	
	/** Returns the px of the creature */
	public float getPX() {
		return this.px;
	}
	
	/** Returns the py of the creature */
	public float getPY() {
		return this.py;
	}
	
	/** Sets the px value of the creature */
	private void setPX(float x) {
		this.px = x;
	}
	
	/** Sets the py value of the creature */
	private void setPY(float y) {
		this.py = y;
	}
	
	/** Returns the screen x coordinate for everything except rendering*/
	public float getScreenX() {
		float x = Float.MAX_VALUE;
		ArrayList<HitBox> hbs = null;
		
		if(this.facingRight) hbs = this.facingRightHitBoxes;
		else hbs = this.hitBoxes;
		for (HitBox hb : hbs) {
			if (!hb.collidable) continue;
			if (x==Float.MAX_VALUE) {
				x = hb.x;
			} else if (hb.x<x) {
				x = hb.x;
			}
		}
		return this.getPX()+x;
	}
	
	/** Returns the screen y coordinate for everything except rendering */
	public float getScreenY() {
		float y = Float.MAX_VALUE;
		ArrayList<HitBox> hbs = null;
		
		if (this.facingRight) hbs = this.facingRightHitBoxes;
		else hbs = this.hitBoxes;
			
		for (HitBox hb : hbs) {
			if (!hb.collidable) continue;
			if (y==Float.MAX_VALUE) {
				y = hb.y;
			} else if (hb.y<y) {
				y = hb.y;
			}
		}
		return this.getPY()+y;
	}
	
	/** Sets the screen x value as mentioned */
	public void setScreenX(float x) {
		float hx = Float.MAX_VALUE;
		
		ArrayList<HitBox> hbs = null;
		if (this.facingRight) hbs = this.facingRightHitBoxes;
		else hbs = this.hitBoxes;
		
		for (HitBox hb : hbs) {
			if (!hb.collidable) continue;
			if (hx==Float.MAX_VALUE) {
				hx=hb.x;
			} else if (hx>hb.x) {
				hx=hb.x;
			}
		}
		this.setPX(x-hx);
	}
	
	/** Sets the screen y value as mentioned */
	public void setScreenY(float y) {
		float hy = Float.MAX_VALUE;
		
		ArrayList<HitBox> hbs = null;
		if (this.facingRight) hbs = this.facingRightHitBoxes;
		else hbs = this.hitBoxes;
		
		for (HitBox hb : hbs) {
			if (!hb.collidable) continue;
			if (hy==Float.MAX_VALUE) {
				hy = hb.y;
			} else if (hy>hb.y) {
				hy = hb.y;
			}
		}
		this.setPY(y-hy);
	}
	
	/** Returns the width of the creature according to the hitboxes, not the canvas width */
	public float getWidth() {
		return this.width;
	}
	
	/** Returns the height of the creature according to hitboes, not the canvas height */
	public float getHeight() {
		return this.height;
	}
	
	/** Returns the width of the creature using hitboxes, not the canvas width */
	protected float setWidth() {
		// TODO : This is a hack only for our game based on our set of monsters
		float width = Float.MAX_VALUE;
		float currentX=0;
		for (HitBox hb : this.hitBoxes) {
			if (!hb.collidable) continue;
			if (width==Float.MAX_VALUE) {
				width = hb.width;
				currentX = hb.x+hb.width;
			} else if (hb.x>=currentX) {
				width = width+hb.width+(hb.x-width);
				currentX = hb.x+hb.width;
			}
		}
		return width;
	}
	
	/** Returns the height of the creature according to the hitboxes, not the canvas height */
	protected float setHeight() {
		// TODO : This is a hack only for our game based on our set of monsters
		float height = Float.MAX_VALUE;
		for (HitBox hb : this.hitBoxes) {
			if (!hb.collidable) continue;
			if (height==Float.MAX_VALUE) {
				height = hb.height;
			} else if (height<hb.height) {
				height = hb.height;
			}
		}
		return height;
	}
	
	/** Returns the screen x coordinate for rendering */
	public float getRenderX() {
		return this.px;
	}
	
	/** Returns the screen y coordinate for rendering */
	public float getRenderY() {
		return this.py;
	}
	
	/** Returns whether an attack at the given target point will hit this creature. */
	public boolean isInHitbox(float x, float y) {
		if (!facingRight) {
			for (HitBox hb : this.hitBoxes) {
				if (hb.damageMultiplier>0 && hb.pointInHitBox(x, y)) {
					return true;
				}
			}
		} else {
			for (HitBox hb : this.facingRightHitBoxes) {
				if (hb.damageMultiplier>0 && hb.pointInHitBox(x, y)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/** Called whenever the creature takes damage of any form. <br>
	 * Spell channels are also interrupted if damaged 
	 * @param damage How much damage the creature is about to take. If this is not positive, nothing will happen.
	 */
	public void takeDamage(int damage) {
		//hack for godmode
		if(godModeEnabled() && this instanceof BattlePlayer) return;
		
		//If shield is on don't take damage
		if (shield) return;
		
		// standard damage
		damage = (damage - damageReduction);
		if(damage<=0) return;
		if(team==0 && U.r()<BattleModel.MAIN.playerCritChance) 
			damage *= BattleModel.MAIN.playerCritMultiplier;
		if(freezeDuration>0) damage*=(1+freezeDuration*0.01f);
		damage*=iceShieldMultiplier;
		damage*=tauntDamageMultiplier;
		
		int d = team==0 ? (int)(damage*BattleModel.MAIN.playerAttackDamageMultiplier) :
			(int)(damage*BattleModel.MAIN.playerTakeDamageMultiplier*damageMultiplier);
		

		freezeDuration = 0;
		iceShieldMultiplier = 1;
		if(d<=0) return;
		
		hp -= d;
		if(spellChannel != null) spellChannel.interrupt();
		if(hp<=0) {
			die();
		}
		
		// apply effects
		BattleModel.MAIN.addFX(new DamageNumberFX(d,(int)mx(),(int)my(),1, isPlayer()));
	}
	
	
	/**
	 * Returns whether or not the creature has enough AP
	 */
	public boolean enoughAP(int ap) {
		if(godModeEnabled() && this instanceof BattlePlayer) return true;
		
		return this.ap >= ap;
	}
	
	/** consume AP on the creature
	 * @param ap amount to spend on a spell
	 * @return whether casting the spell was possible or not
	 */
	public boolean consumeAP(int ap) {
		if(godModeEnabled() && this instanceof BattlePlayer) return true;
		
		if(this.ap >= ap) {
			this.ap -= ap;
			return true;
		}
		return false;
	}
	
	/** Increase AP on the creature
	 * @param ap amount to increase
	 * @return whether casting the spell was possible or not
	 */
	public boolean increaseAP(int ap) {
		if(godModeEnabled() && this instanceof BattlePlayer) return true;
		
		if(this.ap < this.maxAP) {
			this.ap += ap;
			if (this.ap > this.maxAP) {
				this.ap = this.maxAP;
			}
			return true;
		}
		return false;
	}
	
	/** Called whenever the creature dies, and is removed from the arena. */
	public void die() {
		hp = 0;
		alive = false;
		if (!(this instanceof BattlePlayer)){
			PopupRenderer.addPopup(this.type.name + " killed");
			GameModel.MAIN.player.lootEnemy(this);
		}
	}
	
	/**Get the jump speed of the creature
	 * @return - the jump speed of the creature
	 */
	public float getJumpSpeed() {
		return this.jumpSpeed;
	}
	
	/**Get the move speed of the creature
	 * @return - the move speed of the creature
	 */
	public float getMoveSpeed() {
		return this.moveSpeed*this.moveSpeedMultiplier*this.slowEffectMultiplier
				*this.tauntMoveSpeedMultiplier*this.hookSpeedMultiplier;
	}
	
	/**
	 * Applies an impulse force to the creature. <br>
	 * This makes the creature from move in the direction of the impulse until the duration is up. <br>
	 * It also completely negates the creature's current velocity. <br>
	 * The creature cannot move left and right until the duration is up. <br>
	 * The creature cannot jump, air jump, dash, or use any abilities until the stunDuration is up. <br>
	 * Impulses are used for dashing, stuns, knockback, forced frame delays, etc. <br>
	 * 
	 * @param x x velocity of the impulse, in pixels per second.
	 * @param y y velocity of the impulse, in pixels per second.
	 * @param duration how many ticks the impulse will last.
	 */
	public void applyImpulse(float x, float y, String cause, 
			int duration, int stunDuration) {
		//hack for godmode
		if(godModeEnabled() && cause.equals(ImpulseForce.TAKE_DAMAGE) && this instanceof BattlePlayer) return;
		
		// Checking whether the shield is on?
		if (shield && cause.equals(ImpulseForce.TAKE_DAMAGE)) return;
		
		if(duration <= 0) return;
		if(cause.equals(ImpulseForce.TAKE_DAMAGE)) {
			stunDuration = Math.max(0, Math.min(duration, stunDuration));
			duration = Math.max(duration-stunReduction, 0);
			stunDuration = Math.max(stunDuration-stunReduction, 0);
			if(duration <= 0) return;
		}
		
		impulse = new ImpulseForce(id, x, y, cause, 
				GameModel.MAIN.tickNum, duration, stunDuration);
		inAir = true;
		vx = 0;
		vy = 0;
	}
	
	/** Creature is about to use a movement based action. Other action types should have been handled already. */
	public void applyMovementAction(Action action, BattleModel bm) {
		switch(action.type) {
		case START_MOONWALK:
			moonwalking = true;
			break;
		case STOP_MOONWALK:
			moonwalking = false;
			break;
		case MOVE_LEFT:
			tryingToMoveLeft = true;
			tryingToMoveRight = false;
			break;
		case MOVE_RIGHT:
			tryingToMoveLeft = false;
			tryingToMoveRight = true;
			break;
		case MOVE_UP:
			tryingToMoveUp = true;
			tryingToMoveDown = false;
			break;
		case MOVE_DOWN:
			tryingToMoveUp = false;
			tryingToMoveDown = true;
			break;
		
		case STOP_MOVING:
			tryingToMoveLeft = false;
			tryingToMoveRight = false;
			break;
		case STOP_FLYING:
			tryingToMoveUp = false;
			tryingToMoveDown = false;
			break;
		case JUMP:
			if(impulse!=null && impulse.isStunInEffect()) return;
			if(spellChannel!=null && spellChannel.isStunned()) return;
			if(!inAir&&!enoughAP(BattleConstants.JUMP_AP_COST)) return;
			if(inAir&&!enoughAP(BattleConstants.AIR_JUMP_AP_COST)) return;
			impulse = null;
			if(!inAir) {
				consumeAP(BattleConstants.JUMP_AP_COST);
				applyImpulse(0, 0, ImpulseForce.JUMP_DELAY, BattleConstants.JUMP_INITIAL_DELAY, 0);
				inAir = true;
				vy = -getJumpSpeed();
				bm.addFX(new SpriteFX(StaticFXList.smoke, (int)this.mx(), (int)(this.getScreenY() + this.getHeight()), 20, BattleConstants.JUMP_INITIAL_DELAY));
			} else if(airJumpCounter<maxAirJumps && vy>-getJumpSpeed()*BattleConstants.AIR_JUMP_SPEED_RATIO) {
				consumeAP(airJumpCost);
				airJumpCounter++;
				vy = -getJumpSpeed()*BattleConstants.AIR_JUMP_SPEED_RATIO;
			}
			break;
		default:
			break;
		}
	}
	
	public void render(GameContainer gc, Graphics g) {

		if(renderer!=null) {
			renderer.render(gc, g);
		} else {
			g.setColor(Color.magenta);
			g.fillRect(getScreenX(), getScreenY(), getWidth(), getHeight());
			g.setColor(Color.white);
			float x = facingRight ? (getScreenX()+getWidth()*2/3) : (getScreenX()+getWidth()/3);
			g.drawLine(x, getScreenY()+getHeight()/5, x, getScreenY()+getHeight()*4/5);
		}
		
		if(freezeDuration>0) {
			g.setColor(new Color(0x5500CCFF));
			g.fillRect(getScreenX()-getWidth()/5, getScreenY()-getHeight()/5, getWidth()*7/5, getHeight()*7/5);
		}
		
	}
	
	protected void updateCreatureAndPlayer() {
		if(slowEffectMultiplier>0.999 && slowEffectMultiplier<1.001) 
			slowEffectMultiplier = 1;
		
		freezeDuration--;
		if(freezeDuration<0) freezeDuration = 0;
		
		tauntTicksLeft--;
		if(tauntTicksLeft<=0) {
			tauntTicksLeft = 0;
			tauntMoveSpeedMultiplier = 1;
			tauntDamageMultiplier = 1;
		}
	}
	public void update() {
		updateCreatureAndPlayer();
		
		ap = maxAP;
		ai.run();
	}
	
	public String toString() {
		String s = "";
		s = s+"id:" + id + " (" + (int)getScreenX() + "," + (int)getScreenY()+")"+"HP:"+hp+"/"+maxHP;
		return s;
	}
	

	/** conditions to check whether god mode is enabled */
	private boolean godModeEnabled() {
		return EpicGameContainer.MAIN.godmode;
	}
	
	public boolean isPlayer() {
		return this.id == GameModel.MAIN.player.id;
	}
	
}
