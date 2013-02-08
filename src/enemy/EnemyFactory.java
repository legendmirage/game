package enemy;

import item.ItemFactory;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.yaml.snakeyaml.Yaml;

import overworld.OverworldConstants;
import util.Loader;
import util.Logger;
import ability.Ability;
import battle.HitBox;
import battle.ai.AI;

/** A utility class that constructs new types of enemies and modifies the properties of existing types of enemies. 
 */
public class EnemyFactory {

	/** private static Yaml loader */
	private static Yaml yaml;

	/** list of enemy types */
	public static HashMap<String, EnemyType> enemyTypes; 
	static {
		enemyTypes = new HashMap<String, EnemyType>();
		yaml = new Yaml();
	}
	/** This cannot be instantiated. */
	private EnemyFactory() {}
	
	/** Returns the enemy type with the given name, or null if it does not exist. */
	public static EnemyType get(String enemyName) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		if(enemyName==null) return null;
		EnemyType ret = enemyTypes.get(enemyName.toLowerCase());
		if(ret==null) {
			Logger.log("Tried to get a nonexistent enemy type: "+enemyName);
		}
		return ret;
	}
	public static void create(String enemyName, int maxHP, int overworldSpeed, int level, int power) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		enemyTypes.put(enemyName.toLowerCase(), new EnemyType(enemyName, maxHP, overworldSpeed, level, power));
	}
	public static void setAI(String enemyName, Class<? extends AI> ai) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		type.ai = ai;
	}
	public static void makeGround(String enemyName) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		type.jumpVelocity = 0;
		type.flying = false;
	}
	public static void makeJumping(String enemyName, int jumpVelocity) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		type.jumpVelocity = jumpVelocity;
		type.flying = false;
	}
	public static void makeFlying(String enemyName) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		type.jumpVelocity = 0;
		type.flying = true;
	}
	public static void addAbility(String enemyName, Ability ability) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		type.abilities.add(ability);
	}
	public static void addHitBox (String enemyName, int x, int y, int width, int height, boolean damageable, int dmgMultiplier) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		type.hitBoxes.add(new HitBox(x, y, width, height, damageable, dmgMultiplier));
	}
	
	public static void addSpriteSheet(String enemyName, String resource) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		try {
			type.overworldSprite = new SpriteSheet(resource,
					OverworldConstants.TILE_WIDTH,
					OverworldConstants.TILE_HEIGHT);
		} catch(SlickException e) {
			Logger.err("Tried to load non-existant sprite sheet");
			e.printStackTrace();
		}
	}
	public static void addDrop(String enemyName, String itemName, double probability) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		if(ItemFactory.get(itemName)==null) {
			Logger.err("Tried to add an item drop \""+itemName+"\" to enemy " +
					enemyName+", but the item name is invalid");
			return;
		}
		if(type.drops.containsKey(itemName.toLowerCase())) {
			Logger.err("Tried to add the same item \""+itemName+"\" to enemy " +
					enemyName+"'s drop probability table multiple times");
			return;
		}
		type.drops.put(itemName.toLowerCase(), probability);
		double sum = 0;
		for(double d: type.drops.values())
			sum+=d;
		if(sum>1) {
			Logger.err("The item drop probabilities for enemy " +
					enemyName+" add up to more than 1");
			return;
		}
	}

	public static void addBattleSheet(String enemyName, String resource) {
		enemyName = enemyName.toLowerCase();
		enemyName = enemyName.replace(" ", "");
		EnemyType type = get(enemyName);
		if(type==null) {
			Logger.err("Tried to modify enemy type \""+enemyName+"\" which does not exist yet.");
			return;
		}
		try {
			type.animationMap = (Map)yaml.load(Loader.open(resource));
		} catch(Exception e) {
			Logger.err("Animation YAML load failed");
			e.printStackTrace();
		}
	}
	
	
}
