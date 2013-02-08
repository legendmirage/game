package util;

/** Class to store general project constants. More narrow scoped constants can be stored elsewhere. */
public class GameConstants {
	
	/** Does the game start in full screen mode? */
	public static final boolean FULL_SCREEN = true;
	/** The width of the entire space of our game in pixels */
	public static final int SCREEN_WIDTH = 1024;
	/** The height of the entire space for our game in pixels */
	public static final int SCREEN_HEIGHT = 768;
	/** What the taskbar says our game is called. */
	public static final String taskbarGameName = "EPIC";
	/** Enable the logging system? */
	public static final boolean enableLogging = true;
	
	/** Number of milliseconds that elapse between every update tick of the model. */
	public static final int GAME_SPEED = 10;
	/** The number of milliseconds within which the user must press a key twice
	 * for it to register as a double tap. */
	public static final long DOUBLE_TAP_THRESHOLD = -1;
	/** The number of abilities the player can have in battle at one time. */
	public static final int MAX_ACTIVE_ITEMS = 3;
	/** The number of items the player can equip at one time. */
	public static final int MAX_PASSIVE_ITEMS = 3;
	
	/** This flag is on if we are testing balance, so we can start with tons of items and stuff. */
	public static boolean TEST_MODE = false;
	
	/** The flag to control whether the tutorial will run or not. Depends maybe on save states? */
	public static boolean TUTORIAL_ON = true;
	
	/** Cannot instantiate this class. */
	private GameConstants() {}
}
