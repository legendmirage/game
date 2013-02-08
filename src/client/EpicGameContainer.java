package client;

import menu.Menu;
import menu.Shop;
import model.GameModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import overworld.OverworldStateController;
import util.GameConstants;
import util.Logger;
import audio.BGMPlayer;
import battle.BattleModel;
import battle.BattleStateController;
import debug.DebugOverlay;
import entrypoint.Tutorial;

/** The entry point for the game client. <br>
 * This is an implementation of Slick's StateBasedGame. <br>
 * It is a container for everything else in this game. <br>
 * You need a hook into this object to change game states, say between overworld and battle mode. <br>
 * 
 * This is also a singleton.
 */
public class EpicGameContainer extends StateBasedGame {
	public static EpicGameContainer MAIN;
	
	// Unique IDs for states - slick engine needs these for a state based game
	public static final int OVERWORLD_STATE = 0;
	public static final int BATTLE_STATE = 1;
	public static final int LOAD_STATE = 2;
	
	public static final int MAIN_MENU_MODE = -1;
	public static final int SINGLE_PLAYER_MODE = 0;
	public static final int SERVER_MODE = 1;
	public static final int MULTIPLAYER_MODE = 2;
	public static final int CONTROLS_MODE = 3;
	public static final int IP_MODE = 4;
	public static final int CREDITS_MODE = 5;
	
	/** The client protocol. */
	public  ClientProtocol protocol;
	
	/** The id of the player that this client is controlling. */
	public int myID = -1;

	/** the master debug draw mode flag */
	public boolean debugDraw = false;
	
	/** the master debug draw mode flag */
	public boolean debugConsoleFlag = false;

	/** the master god-mode flag */
	public boolean godmode = false;
	
	/** the master dvorak flag */
	public boolean dvorak = false;
	
	/** the master menu-open flag*/
	public boolean menuIsOpen = false;
	
	/** the master shop flag */
	public boolean shopIsOpen = false;
	
	/** the menu */
	public Menu menu;
	
	/** the shop */
	public Shop shop;

	/** the debug console */
	public DebugOverlay debugConsole;
	
	/** am i the server? */
	public static boolean amIServer = false;
	
	/** Player's mode. Options: Viewing main menu, viewing controls, single player, starting server, joining multiplayer. */
	public static int mode = MAIN_MENU_MODE;
	
	/** Show tutorial flag */
	public boolean showTutorial = true;
	
	/** Show dialog flag */
	public static boolean showDialogue = true;
	
	/** Show popups flag */
	public static boolean showPopups = true;
	
	/** Music Player */
	public BGMPlayer bgmPlayer;
	
	public EpicGameContainer() {
		super(GameConstants.taskbarGameName);
		Logger.log("Initializing EpicGameContainer");
		MAIN = this;
		this.menu = new Menu();
		this.shop = new Shop();
		this.bgmPlayer = new BGMPlayer();
		EpicGameContainer.mode = MAIN_MENU_MODE;
		
		if (showTutorial){
			showDialogue = true;
		} 
		
	}
	
	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		
		// First state added becomes the initial state
		this.addState(new LoadState(LOAD_STATE));	
		this.addState(new OverworldStateController(OVERWORLD_STATE));
		this.addState(new BattleStateController(BATTLE_STATE));
		this.debugConsole = new DebugOverlay();
		
		// Print a large space in the system.out stream 
		System.out.print("\n\n\n\n\n");
	}
	
	
	/** Is the game currently paused? If so, creatures and animations are frozen. */
	public boolean isPaused() {
		return menuIsOpen || shopIsOpen || (GameModel.MAIN.curScene!=null && !GameModel.MAIN.curScene.isDone());
	}

	
	/** Open the player's menu */ 
	public void changeMenuState(){
		if(BattleModel.MAIN!=null) return;
		menuIsOpen = !menuIsOpen;
		//Update the menu, if it's now open, with the new inventory and quest stuff
		if (menuIsOpen) {
			//reset just resets where the user focus is in the menu, for convenience
			menu.reset();
			updateMenu();
		}else{
			this.menu=new Menu();
			System.gc();
		}
	}
	
	/** Update the player's menu, but only if the menu is open */
	public void updateMenu(){
		if (this.menuIsOpen) {
			this.menu.update(GameModel.MAIN.player.inventory, 
					GameModel.MAIN.quests, GameModel.MAIN.player.stats);
		}
		if (this.shopIsOpen) {
			this.shop.update(GameModel.MAIN.player.inventory, 
					GameModel.MAIN.quests, GameModel.MAIN.player.stats);
		}
	}
	
	/**
	 * These are global game container keybinds that should be accessible to any state
	 * We don't bind the the global keyReleased because that causes some sort of recursion error
	 */
	public void keyBind(int key, char c) {
		switch(key) {
		case Input.KEY_F1:
			MAIN.debugDraw= !MAIN.debugDraw;
			Logger.log("Debug: " +  MAIN.debugDraw);
			break;
		case Input.KEY_F2:
			MAIN.godmode = !MAIN.godmode;
			Logger.log("God Mode: " + MAIN.godmode);
			break;
		case Input.KEY_F3:
			MAIN.dvorak = !MAIN.dvorak;
			Logger.log("Sam Mode: " + MAIN.dvorak);
			break;
		case Input.KEY_F4:
			System.exit(0);
			break;
		case Input.KEY_F5:
			MAIN.showDialogue = !MAIN.showDialogue;
			Logger.log("Dialogue: " +  MAIN.showDialogue);
			GameModel.MAIN.curScene = null;
			break;
		case Input.KEY_F7:
			MAIN.debugConsoleFlag= !MAIN.debugConsoleFlag;
			if (!MAIN.debugConsoleFlag) MAIN.debugConsole.exitConsole();
			Logger.log("Debug: " +  MAIN.debugConsoleFlag);
			break;
		case Input.KEY_F8:
			MAIN.shopIsOpen = !MAIN.shopIsOpen;
			updateMenu();
			break;
		case Input.KEY_F9:
			showTutorial = false;
			Tutorial.startGame();
			updateMenu();
			break;
		default:
			break;
		}
	}

	
}