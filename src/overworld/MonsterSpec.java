package overworld;

import battle.ai.AI;
import enemy.EnemyType;

/** Data object specifying one monster of a group. */
public class MonsterSpec {
	/** Hidden counter to make sure each instance of this class has a unique id. */
	private static int objectCount = 0;
	
	/** Unique identifier. */
	public int id;
	/** The type of monster this is. */
	public EnemyType type;
	/** The item that this monster will always drop. <br>
	 * Overrides the default drop probability table. <br>
	 * Can be used for boss drops or quest drops. <br>
	 * Generally this should be null, so that the default drop probabilities are used. <br>
	 */
	public String specialDrop;
	/** Which number spawn zone on the map that this monster spawns at. Defaults to 1.*/
	public int spawnZone;
	/** Which AI this monster uses. Defaults to the type's default AI. */
	public Class<? extends AI> ai;
	
	public MonsterSpec(EnemyType type) {
		this.id = objectCount++;
		
		this.type = type;
		this.specialDrop = null;
		this.spawnZone = 1;
		this.ai = type.ai;
	}
}
