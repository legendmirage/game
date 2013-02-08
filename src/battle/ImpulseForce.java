package battle;

import model.GameModel;

/** Represents an impulse force on a battle creature. <br>
 */
public class ImpulseForce {
	
	
	// STATIC IMPULSE CAUSES
	public static final String JUMP_DELAY    = "jump_delay";
	public static final String LANDING_DELAY = "land_delay";
	public static final String TAKE_DAMAGE   = "take_damage";
	public static final String DASH          = "dash";
	public static final String ASSASSINATE   = "assassinate";
	
	
	// MUTABLE FIELDS
	/** Number of ticks before current impulse force runs out. 0 if there is no impulse force affecting the player. */
	public int tickStart;

	// IMMUTABLE FIELDS
	/** The ID of the creature that this is affecting. */
	public final int creatureID;
	/** x velocity of most recent impulse force. */
	public final float vx;
	/** y velocity of most recent impulse force. */
	public final float vy;
	/** The number of ticks that the impulse lasts. */
	public final int duration;
	/** The number of ticks that this impulse prevents the creature from acting. Must be at most the impulse duration. */
	public final int stunDuration;
	/** The reason for the impulse */
	public final String cause;
	
	public ImpulseForce(int creatureID, float vx, float vy, 
			String cause, int tickStart, int duration, int stunDuration) {
		this.creatureID = creatureID;
		this.vx = vx;
		this.vy = vy;
		this.cause = cause;
		this.tickStart = tickStart;
		this.duration = duration;
		this.stunDuration = stunDuration;
	}
	
	/** Is this impulse still affecting the creature? */
	public boolean isInEffect() {
		return GameModel.MAIN.tickNum < tickStart + duration;
	}
	/** Is this impulse still stunning the creature? */
	public boolean isStunInEffect() {
		return GameModel.MAIN.tickNum < tickStart + stunDuration;
	}

	/** Returns the impulse velocity magnitude */
	public double magnitude() {
		return Math.sqrt(vx*vx + vy*vy);
		
	}
}
