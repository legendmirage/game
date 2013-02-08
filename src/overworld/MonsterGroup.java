package overworld;

import java.util.ArrayList;
import java.util.HashMap;

import util.Logger;

import model.GameModel;
import model.Player;
import enemy.EnemyType;
import graphics.OverworldObjectRenderer;

/** A group of monsters in the overworld. <br>
 * Corresponds one-to-one with a specification of a battle. <br>
 * 
 */
public class MonsterGroup extends OverworldObject {
	
	/** The monsters alive inside this group. Mapped from the monster spec's ID to the spec. */
	public final HashMap<Integer, MonsterSpec> monsters;
	
	/** The ID of the battle map that this monster group will always fight on. */
	public int battleMapID;
	/** The ID of the spawn zone of the battle map that the player spawns at. */
	public int playerSpawnZone;
	/** What type of overworld AI this group uses to walk around. <br>
	 * 1 = wander around randomly, sticking near original location, agro player if he gets close <br>
	 * anything else = stay completely still <br>
	 */
	public int overworldAIType;
	
	/** This is to ensure that the AI does not get run unnecessarily often. */
	private int moveAICooldown;
	/** Location that the enemy original spawned at. Used for overworld movement AI. */
	private int originalX, originalY;
	
	public MonsterGroup(ArrayList<MonsterSpec> monsters, int battleMapID, int playerSpawnZone, 
			int AIType, int zone, int x, int y) {
		super(zone, x, y);
		if(monsters.isEmpty()) {
			Logger.err("ERROR: constructed a MonsterGroup with no monsters in zone "+zone);
		}
		originalX = x;
		originalY = y;
		this.battleMapID = battleMapID;
		this.playerSpawnZone = playerSpawnZone;
		this.overworldAIType = AIType;
		this.moveAICooldown = 0;
		
		this.monsters = new HashMap<Integer, MonsterSpec>();
		for (MonsterSpec spec: monsters) {
			this.monsters.put(spec.id, spec);
		}
		
		updateMoveSpeed();
		this.renderer = new OverworldObjectRenderer(this, getBestType().overworldSprite);
	}
	
	public void updateMoveSpeed() {
		double sum = 0;
		for(MonsterSpec spec: monsters.values()) {
			sum += spec.type.overworldSpeed;
		}
		moveSpeed = (int)Math.ceil(sum/monsters.size());
	}
	
	public EnemyType getBestType() {
		EnemyType bestType = null;
		int bestValue = -1;
		for (MonsterSpec spec: monsters.values()) {
			int value = spec.type.level+spec.type.power;
			if(value>bestValue || (value==bestValue && spec.type.power > bestType.power)) {
				bestValue = value;
				bestType = spec.type;
			}
		}
		return bestType;
	}
	
	@Override
	public void update(ZoneModel zone) {
		if(moveCooldown==0) {
			if(moveAICooldown>0)
				moveAICooldown--;
			if(moveAICooldown==0) {
				runMoveAI(zone);
				moveAICooldown = moveSpeed;
			}
		} else {
			moveAICooldown = 0;
		}
		super.update(zone);
	}
	
	/** Sets the heading of the enemy according to its AI behavior. */
	private void runMoveAI(ZoneModel zone) {
		Player pl = GameModel.MAIN.player;
		if(pl.justSpawned) {
			stopMoving();
			return;
		}
		
		switch(overworldAIType) {
		case 1:
			if(Math.abs(pl.x-x)+Math.abs(pl.y-y) <= OverworldConstants.ENEMY_AGRO_RANGE) {
				int[] result = zone.map.findPath(x, y, pl.x, pl.y, OverworldConstants.ENEMY_AGRO_RANGE);
				if(result!=null) {
					startMoving(result[0], result[1]);
				} else {
					wanderRandomly();
				}
			} else {
				wanderRandomly();
			}
			break;
		default:
			break;
		}
	}
	private void wanderRandomly() {
		int dx = x>originalX ? -1 : 1;
		int dy = y>originalY ? -1 : 1;
		double r = GameModel.MAIN.rand.get();
		if(r<0.2)
			startMoving(-dx, 0);
		else if(r<0.5) 
			startMoving(dx, 0);
		else if(r<0.7) 
			startMoving(0, -dy);
		else 
			startMoving(0, dy);
	}

}
