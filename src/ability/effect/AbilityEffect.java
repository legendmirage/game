package ability.effect;

import model.GameModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


import battle.BattleModel;

/** An effect created by using an ability. <br>
 * Lasts in the battle model until alive is set to false. <br>
 */
public abstract class AbilityEffect {
	/** Hidden counter to make sure each instance of this class has a unique id. */
	private static int objectCount = 0;
	
	/** Unique identifier. */
	public final int id;
	/** ID of the BattleCreature that original used the ability that made this effect. */
	public final int creatureID;
	/** Is this effect still in effect? */
	public boolean alive;
	/** The tick on which this effect was created. */
	public int tickCreated;
	
	/** Position of the ability effect. <br>
	 * May not apply for some types of effects, in which case this is any random value. <br>
	 */
	public float px, py;
	
	/** Creates an effect originating from the given creature. */
	public AbilityEffect(int creatureID) {
		this(creatureID, -55555, -55555);
	}
	/** Creates an effect originating from the given creature, based at the given location. */
	public AbilityEffect(int creatureID, float px, float py) {
		this.id = objectCount++;
		this.creatureID = creatureID;
		
		this.alive = true;
		this.tickCreated = GameModel.MAIN.tickNum;
		this.px = px;
		this.py = py;
	}
	
	public abstract void render(GameContainer gc, Graphics g);
	public abstract void update(BattleModel bm);
	
}
