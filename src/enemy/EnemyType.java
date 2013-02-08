package enemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SpriteSheet;

import ability.Ability;
import battle.HitBox;
import battle.ai.AI;
import battle.ai.TutorialAI;

/** Describes the stats and behavior for a type of enemy. */
public class EnemyType{
	/** The name of the enemy type. */
	public String name;
	/** The amount of health of this enemy in battle. */
	public int maxHP;
	/** The speed on this enemy in the overworld, in ticks per square. */
	public int overworldSpeed;
	/** Can this enemy jump? If so, this is its jump velocity. If not, this is 0. */
	public int jumpVelocity;
	/** Can this enemy fly? */
	public boolean flying;
	
	/** The AI that this enemy uses. */
	public Class<? extends AI> ai;
	/** The list of abilities that this enemy can use. */
	public ArrayList<Ability> abilities;
	/** The level that the player should be at when fighting this enemy. <br>
	 * This ranges from 1 - 50. <br>
	 * Generally, monsters deeper into the game should be higher level. <br>
	 */
	public int level;
	/** The power level of the monster, common rats have low power, bosses have high power. <br>
	 * As a general guideline, level+power is a heuristic for how difficult the monster is. <br>
	 * This ranges from 1 - 20. <br>
	 */
	public int power;
	/** The enemy's individual drop probability table. This is checked before the generic 
	 * drop table is checked. <br> 
	 * Maps from item name to probability that the item is dropped. <br>
	 * Values in the map should not add up to more than 1. <br>
	 */
	public HashMap<String, Double> drops;
	/** Enemy's overworld sprite sheet */
	public SpriteSheet overworldSprite;
	/** Enemy's animation map */
	public Map animationMap;
	/** The battle hit boxes of this enemy */
	public ArrayList<HitBox> hitBoxes;

	
	public EnemyType(String name, int maxHP, int overworldSpeed, int level, int power) {
		this.name = name;
		this.maxHP = maxHP;
		this.overworldSpeed = overworldSpeed;
		this.jumpVelocity = 0;
		this.flying = false;
		this.ai = TutorialAI.class;
		this.abilities = new ArrayList<Ability>();
		this.level = level;
		this.power = power;
		this.drops = new HashMap<String, Double>();
		this.overworldSprite = null;
		this.hitBoxes = new ArrayList<HitBox>();
	}
	
	/** Can this enemy jump? */
	public boolean canJump() {
		return jumpVelocity!=0;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
