package client;

import menu.ControlsMenu;
import menu.CreditsMenu;
import menu.MainMenu;
import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.GameConstants;
import util.Logger;
import visnovel.CutscenePortraitLoader;
import visnovel.DialogLoader;
import content.BattleMapLoader;
import content.DebugCommandLoader;
import content.EnemyTypeLoader;
import content.FontLoader;
import content.ItemTypeLoader;
import content.MenuLoader;
import content.SoundLoader;
import content.OverworldLoader;
import content.QuestLoader;
import content.RecipeLoader;

public class LoadState extends BasicGameState{
	/** Slick framework needs this. */
	private int stateID;
	public MainMenu menu;
	public ControlsMenu controls;
	public CreditsMenu credits;
	private boolean contentLoaded = false, renderedLoadingScreen = false;
	
	public LoadState(int ID) throws SlickException{
		this.stateID = ID;
		FontLoader.loadAll();
		MenuLoader.loadAll();
		EpicGameContainer.mode = EpicGameContainer.MAIN_MENU_MODE;
		this.menu = new MainMenu();
		this.controls = new ControlsMenu(true);
		this.credits = new CreditsMenu(true);
	}
	@Override
	public int getID() {
		return this.stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		if (EpicGameContainer.mode==EpicGameContainer.MAIN_MENU_MODE) this.menu.render(gc, g,false);
		else if (EpicGameContainer.mode==EpicGameContainer.CONTROLS_MODE) this.controls.render(gc, g);
		else if (EpicGameContainer.mode==EpicGameContainer.CREDITS_MODE) this.credits.render(gc, g);
		else if (EpicGameContainer.mode==EpicGameContainer.IP_MODE) this.menu.render(gc,g,true);
		else if (EpicGameContainer.mode==EpicGameContainer.MULTIPLAYER_MODE){
			g.setColor(new Color(0,0,0));
			g.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
			g.setColor(new Color(255,255,255));
			String s = "Waiting for other player.....";
			g.drawString(s, (GameConstants.SCREEN_WIDTH-g.getFont().getWidth(s))/2, 350);
		}
		else{
			g.setColor(new Color(0,0,0));
			g.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);
			g.setColor(new Color(255,255,255));
			String s = ".....Loading, please wait......";
			g.drawString(s, (GameConstants.SCREEN_WIDTH-g.getFont().getWidth(s))/2, 350);
			renderedLoadingScreen = true;

			
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		if (EpicGameContainer.mode==EpicGameContainer.SINGLE_PLAYER_MODE){
			// Load the content if this is the first time
			if (renderedLoadingScreen){
				loadAllContent();
				
				// Create the game model
				new GameModel(55555);
				
				// Create the protocol
				EpicGameContainer.MAIN.protocol = new FakeClientProtocol();
				EpicGameContainer.MAIN.myID = 0;
				
				// Done loading, transition to overworld state
				Logger.log("Finished loading");
		
				EpicGameContainer.MAIN.enterState(EpicGameContainer.OVERWORLD_STATE);
			}
		}
	}
	
	/** Load all of the content via the various loader classes. 
	 * @throws SlickException */
	private void loadAllContent() throws SlickException {
		if(contentLoaded) return;
		// ORDER MATTERS - some objects depend on others
		
		BattleMapLoader.loadAll();
		ItemTypeLoader.loadAll();
		RecipeLoader.loadAll();
		EnemyTypeLoader.loadAll();
		OverworldLoader.loadAll();
		CutscenePortraitLoader.loadAll();
		DialogLoader.loadAll();
		DebugCommandLoader.loadAll();
		QuestLoader.loadAll();
		SoundLoader.loadAll();
		contentLoaded = true;
	}

	@Override
	public void keyReleased(int key, char c) {
		EpicGameContainer.MAIN.keyBind(key, c);
	}
	
	@Override
	public void keyPressed(int key, char c){
		if (EpicGameContainer.mode==EpicGameContainer.MAIN_MENU_MODE || 
				EpicGameContainer.mode==EpicGameContainer.IP_MODE) this.menu.keyPressed(key);
		else if (EpicGameContainer.mode==EpicGameContainer.CONTROLS_MODE) this.controls.keyPressed(key);
		else if (EpicGameContainer.mode==EpicGameContainer.CREDITS_MODE) this.credits.keyPressed(key);
	}
}
