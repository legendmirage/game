package overworld;

import util.GameConstants;

/** Constants to be used only for the overworld state. <br>
 * This involves things like player speed in the overworld, or overworld tilemap specs. <br>
 * 
 */
public class OverworldConstants {
	/** The number of tiles away from the player an enemy needs to be to start chasing the player. */
	public static final int ENEMY_AGRO_RANGE = 4;
	/** The number of ticks that the player is frozen for after he enters a new zone. */
	public static final int MOVE_COOLDOWN_ENTERING_ZONE = 10;
	/** The number of ticks that the player is frozen for after he finishes a battle and returns to overworld. */
	public static final int MOVE_COOLDOWN_ENDING_BATTLE = 10;
	/** The default number of ticks it takes for the player to move one square in the overworld. */
	public static final int PLAYER_BASE_MOVE_SPEED = 20;
	
	/** The width of a tile in pixels. */
	public static final int TILE_WIDTH = 32;
	/** The height of a tile in pixels. */
	public static final int TILE_HEIGHT = 32;
	

	/** width of the screen in tiles */
	public static final int SCREEN_TILE_WIDTH = GameConstants.SCREEN_WIDTH / TILE_WIDTH;
	/** height of the screen in tiles */
	public static final int SCREEN_TILE_HEIGHT = GameConstants.SCREEN_HEIGHT / TILE_HEIGHT;
	
	/** The number of "animation frames" in a given move **/
	public static final double MOVEMENT_STEP = .1;
	/** Animation time delay */
	public static final int DELAY = 50;
	
	/** Cannot instantiate this class. */
	private OverworldConstants() {}
}
