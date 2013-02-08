package battle;

/** Constants to be used only for the battle state. <br>
 * This involves things like platforming physics, damage and action point penalties, and battle tilemap specs. <br>
 * 
 */
public class BattleConstants {
	
	/** Width in pixels of the hitbox of a player in battle mode. */
	public static final float DEFAULT_PLAYER_WIDTH = 25;
	/** Height in pixels of the hitbox of a player in battle mode. */
	public static final float DEFAULT_PLAYER_HEIGHT = 75;
	/** Number of ticks after everything dies that the battle mode stays intact. */
	public static final int TICKS_AFTER_BATTLE = 350;
	
	/** The acceleration of gravity, in pixels per second squared. */
	public static final float GRAVITY = 1000;
	/** The deceleration of air resistance, as a percent loss per second. */
	public static final float AIR_RESISTANCE = 0.2f;
	/** The AP per second regen rate, as a fraction of the max AP. */
	public static final float PLAYER_AP_REGEN = 0.2f;
	/** A player's base horizontal move speed, in pixels per second. */
	public static final float PLAYER_MOVE_SPEED = 250;
	/** Can players turn around in midair? */
	public static final boolean PLAYER_CAN_TURN_IN_MIDAIR = true;
	/** The number of ticks within which a player cannot use two abilities, not counting dash and retreat. */
	public static final int GLOBAL_ABILITY_COOLDOWN = 5;
	
	/** The player's AP cost of jumping. */
	public static final int JUMP_AP_COST = 0;
	/** The player's AP cost of air jumping. */
	public static final int AIR_JUMP_AP_COST = 20;
	/** A player's upward velocity after jumping off the ground, in pixels per second. */
	public static final float PLAYER_JUMP_SPEED = 550;
	/** Air jump speed divided by jump speed */
	public static final float AIR_JUMP_SPEED_RATIO = 0.85f;
	/** The default maximum air jumps the player has after leaving the ground. */
	public static final int PLAYER_DEFAULT_MAX_AIR_JUMPS = 1;
	/** The number of ticks between when you press jump and when you actually get off the ground. */
	public static final int JUMP_INITIAL_DELAY = 4;
	/** The y velocity, in pixels per second, above which if you land, you suffer increased landing delay. */
	public static final int LANDING_DELAY_THRESHOLD = 700;
	/** This is the additional delay you get for landing from higher velocities, in ticks per (pixels per second). */
	public static final float LANDING_DELAY_MULTIPLIER = 0.5f;
	
	/** The AP cost of dashing */
	public static final int DASH_AP_COST = 30;
	/** A player's horizontal velocity multiplier while dashing. */
	public static final float PLAYER_DASH_SPEED_MULTIPLIER = 5;
	/** The time that a dash lasts, in ticks. */
	public static final int PLAYER_DASH_DURATION = 15;
	
	/** The AP cost of a basic attack */
	public static final int BASIC_ATTACK_AP_COST = 5;
	/** The delay in ticks of a basic attack */
	public static final int BASIC_ATTACK_DELAY = 5;
	/** The cooldown of the player's basic attack, in ticks. */
	public static final int PLAYER_BASIC_ATTACK_COOLDOWN = 25;
	/** How far in front of the player his basic attack goes, in pixels. */
	public static final float PLAYER_BASIC_ATTACK_RADIUS = 80;
	/** How much damage the player's basic attack does. */
	public static final int PLAYER_BASIC_ATTACK_DAMAGE = 3;
	/** The default knockback speed of the player's basic attack. */
	public static final float BASIC_ATTACK_KNOCKBACK_SPEED = 1000;
	/** The default knockback duration of the player's basic attack, in ticks. */
	public static final int BASIC_ATTACK_KNOCKBACK_DURATION = 10;
	
	/** The AP cost of retreating. */
	public static final int RETREAT_AP_COST = 0;
	/** The time it takes to channel retreat, in ticks. */
	public static final int RETREAT_CHANNEL_DURATION = 500;
	
	/** The width of a tile in pixels. */
	public static final int TILE_WIDTH = 32;
	/** The height of a tile in pixels. */
	public static final int TILE_HEIGHT = 32;
	
	
	/** Cannot instantiate this class. */
	private BattleConstants() {}
}
