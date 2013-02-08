package client;

import menu.Menu;
import model.Player;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import overworld.ZoneModel;
import util.GameConstants;
import util.Logger;

public class EpicnessAppShell {
	private static AppGameContainer app;
	
	/** Closes the game */
	
	public static void close(){
		app.exit();
	}
	
	/** Restarts the game*/
	public static void restart(){
		try {
			Logger.log("Exited");
			Logger.log("Restarting");
			
			//TODO: If the server has been opened, close it
			EpicGameContainer.MAIN.init(app);
			EpicGameContainer.MAIN.menu = new Menu();
			EpicGameContainer.MAIN.menuIsOpen = false;
			EpicGameContainer.MAIN.showTutorial = true;
			Player.setInitialObjectCount(0);
			ZoneModel.setInitialObjectCount(0);
			
			EpicGameContainer.MAIN.enterState(EpicGameContainer.LOAD_STATE);
			Logger.log("Restart complete");
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	/** The entry point. */
	public static void main(String[] args) throws SlickException {
		app = new AppGameContainer(new EpicGameContainer());
		app.setDisplayMode(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT, GameConstants.FULL_SCREEN);
		app.setAlwaysRender(true);
		app.setShowFPS(false);
		app.start();
	}
}
