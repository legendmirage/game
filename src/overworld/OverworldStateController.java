package overworld;

import entrypoint.Tutorial;
import graphics.GearPortrait;
import graphics.PopupRenderer;
import graphics.transition.BattleTransition;
import menu.MonsterMenu;
import model.GameModel;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.GameConstants;
import util.KeyBindings;
import util.Logger;
import util.ModelUtil;
import util.U;
import visnovel.Cutscene;
import NetworkUtil.Action;
import audio.SFXPlayer;
import battle.BattleModel;
import client.ActionType;
import client.EpicGameContainer;

/** The slick game state which encompasses the overworld. <br>
 * The overworld includes the town, the outdoors, dungeons, etc. <br>
 * Basically everything except battle mode, and maybe main menu mode. <br>
 */
public class OverworldStateController extends BasicGameState {
	/** Slick framework needs this. */
	private int stateID;
	/** Hook into the controller. */
	public Input input;
	
	/** The zone that the player is currently in. */
	public ZoneModel zone;
	
	/** The sprites that exist on the screen */
	public Animation[] sprites;
	
	/** Monster menu flag*/
	public boolean monsterMenuOpen = false;
	
	/** Monster menu */
	public MonsterMenu monsterMenu;	
	
	public OverworldStateController(int stateID) {
		this.stateID = stateID;
		this.monsterMenu = new MonsterMenu();
	}
	
	@Override 
    public int getID() {
    	return stateID;
    }

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		input = gc.getInput();		
	}
	
	@Override 
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		updateState();
		
	}
	
	/** Makes sure that the zone is set to the one my player is in. 
	 * Transition states if necessary. */
	private void updateState() {
		if(BattleModel.MAIN!=null) {
			System.gc();
			EpicGameContainer.MAIN.enterState(EpicGameContainer.BATTLE_STATE, null, new BattleTransition());
		}
		zone = GameModel.MAIN.zones.get(GameModel.MAIN.player.zone);
		
		if (EpicGameContainer.MAIN.showTutorial) Tutorial.nextStep(); //Mostly for the cutscenes. Other "Donenesses" triggered elsewhere
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	
		
		int fullX = OverworldConstants.SCREEN_TILE_WIDTH;
		int fullY = OverworldConstants.SCREEN_TILE_HEIGHT;
		
		int halfX = fullX/2;
		int halfY = fullY/2;
		
		float rX = U.minmax(halfX, (float)zone.getWidth()-halfX, GameModel.MAIN.player.getContinuousXLoc());
		float rY = U.minmax(halfY, (float)zone.getHeight()-halfY, GameModel.MAIN.player.getContinuousYLoc());
		
		// centering small maps
		if(zone.getWidth() < fullX ) rX = rX + (fullX - zone.getWidth())/2;
		if(zone.getHeight() < fullY ) rY = rY + (fullY - zone.getHeight())/2;
		
		// main translation
		g.translate(GameConstants.SCREEN_WIDTH/2 - rX * OverworldConstants.TILE_WIDTH, 
					GameConstants.SCREEN_HEIGHT/2 - rY * OverworldConstants.TILE_HEIGHT);
		
		
		zone.render(gc, g);

		g.resetTransform();

		if (EpicGameContainer.MAIN.showTutorial) Tutorial.render(gc, g);
		if (EpicGameContainer.MAIN.menuIsOpen) 
			EpicGameContainer.MAIN.menu.render(gc, g);
		else if (monsterMenuOpen)
			this.monsterMenu.render(gc,g);

		if (EpicGameContainer.MAIN.debugConsoleFlag)
			EpicGameContainer.MAIN.debugConsole.render(gc, g);
		if (EpicGameContainer.MAIN.shopIsOpen)
			EpicGameContainer.MAIN.shop.render(gc, g);
		
			
//		g.setColor(Color.red);
//		g.drawString("Rats Killed: "+GameModel.MAIN.getKillCount("Rat"), 50, 630);
//		g.drawString("Goblins Killed: "+GameModel.MAIN.getKillCount("Goblin"), 50, 650);
//		g.drawString("Ogres Killed: "+GameModel.MAIN.getKillCount("Ogre"), 50, 670);
//		String questString = new String("Quests: ");
//		for(Quest q: GameModel.MAIN.quests.values()){
//			for(QuestComponent com : q.componentsLeft){
//			questString += "\n" + com.toString();
//			}
//			
//		}
//		g.drawString(questString, 50, 690);
		
	
		Cutscene curScene = GameModel.MAIN.curScene;
		//Cutscene curScene = GameModel.getMainModel().curScene;
		if(curScene != null && !curScene.isDone()) {
			curScene.render(gc, g);
		} else { 
			if(!monsterMenuOpen) GearPortrait.render(gc, g);
			//if (EpicGameContainer.showPopups) PopupRenderer.render(gc, g);
		}

		if (!EpicGameContainer.MAIN.menuIsOpen){
			g.translate(GameConstants.SCREEN_WIDTH/2 - rX * OverworldConstants.TILE_WIDTH, 
					GameConstants.SCREEN_HEIGHT/2 - rY * OverworldConstants.TILE_HEIGHT);
		}

		if (EpicGameContainer.showPopups) PopupRenderer.renderPermanentPopups(gc, g);
		g.resetTransform();
		
		if (EpicGameContainer.showPopups) PopupRenderer.renderImpermanentPopups(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		
		if (!EpicGameContainer.MAIN.menuIsOpen && !EpicGameContainer.MAIN.shopIsOpen){
	
			if(input.isKeyDown(kb.left) && !input.isKeyDown(kb.right) && 
					!input.isKeyDown(kb.up) && !input.isKeyDown(kb.down)) { 
				attemptAction(new Action(ActionType.MOVE_LEFT));
			} else if(!input.isKeyDown(kb.left) && input.isKeyDown(kb.right) && 
					!input.isKeyDown(kb.up) && !input.isKeyDown(kb.down)) { 
				attemptAction(new Action(ActionType.MOVE_RIGHT));
			}else if(!input.isKeyDown(kb.left) && !input.isKeyDown(kb.right) && 
					input.isKeyDown(kb.up) && !input.isKeyDown(kb.down)) { 
				attemptAction(new Action(ActionType.MOVE_UP));
			} else if(!input.isKeyDown(kb.left) && !input.isKeyDown(kb.right) && 
					!input.isKeyDown(kb.up) && input.isKeyDown(kb.down)) { 
				attemptAction(new Action(ActionType.MOVE_DOWN));
			} 
		}
		
		ModelUtil.updateModel(delta);
		updateState();
	}

	//@Override
	public void keyPressed(int key, char c) {
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		
		if(key==kb.dialog || key==kb.enter) {
			Cutscene curScene = GameModel.MAIN.curScene;
			if(curScene != null && !curScene.isDone()) {
				curScene.step();
			}
		}

		if (EpicGameContainer.MAIN.debugConsoleFlag) EpicGameContainer.MAIN.debugConsole.keyPressed(key, c);
		else if (this.monsterMenuOpen){
			if (key==kb.monsterSpawn) this.monsterMenuOpen = false;
			else this.monsterMenu.keyPressed(key);
		}
		else if (!EpicGameContainer.MAIN.menuIsOpen&&!EpicGameContainer.MAIN.shopIsOpen){
			if(key==kb.left) { 
				attemptAction(new Action(ActionType.MOVE_LEFT));
			} else if(key==kb.right) { 
				attemptAction(new Action(ActionType.MOVE_RIGHT));
			} else if(key==kb.up) { 
				attemptAction(new Action(ActionType.MOVE_UP));
			} else if(key==kb.down) { 
				attemptAction(new Action(ActionType.MOVE_DOWN));
			} else if(key==kb.space || key==kb.enter) { 
				attemptAction(new Action(ActionType.ENTER));
			} else if (key==kb.menu){
				attemptAction(new Action(ActionType.OPEN_MENU));
			} else if (key==kb.monsterSpawn){
				this.monsterMenuOpen=true;
				this.monsterMenu.update();
			} 
		}
		else if (!EpicGameContainer.MAIN.shopIsOpen){
			if (key==kb.menu){
				attemptAction(new Action(ActionType.OPEN_MENU));
			} else if(key==kb.left){
				EpicGameContainer.MAIN.menu.leftPressed = true;	
			} else if(key==kb.right){
				EpicGameContainer.MAIN.menu.rightPressed = true;	
			} else if(key==kb.down){
				Tutorial.setStepDone(10);
				EpicGameContainer.MAIN.menu.downPressed = true;			
			} else if(key==kb.up){
				EpicGameContainer.MAIN.menu.upPressed = true;	
			}
			else {
				//These are passed into the menu to be dealt with on a more tab-specific basis
				//The menu is not updated immediately upon key press (though it could be), but rather whenever the action is completed
				EpicGameContainer.MAIN.menu.keyPressed(key,c);
			}
			
		} 
		else {
			if (key==kb.menu && !EpicGameContainer.MAIN.shopIsOpen){
				attemptAction(new Action(ActionType.OPEN_MENU));
			} else if(key==kb.left){
				EpicGameContainer.MAIN.shop.leftPressed = true;	
			} else if(key==kb.right){
				EpicGameContainer.MAIN.shop.rightPressed = true;	
			} else if(key==kb.down){
				EpicGameContainer.MAIN.shop.downPressed = true;			
			} else if(key==kb.up){
				EpicGameContainer.MAIN.shop.upPressed = true;	
			}
			else {
				//These are passed into the menu to be dealt with on a more tab-specific basis
				//The menu is not updated immediately upon key press (though it could be), but rather whenever the action is completed
				EpicGameContainer.MAIN.shop.keyPressed(key,c);
			}
			
		}
		
	}
	
	@Override
	public void keyReleased(int key, char c) {
		
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		if (!EpicGameContainer.MAIN.menuIsOpen && !EpicGameContainer.MAIN.shopIsOpen){
			if(key==kb.left || key==kb.right || key==kb.up || key==kb.down) { 
				if(input.isKeyDown(kb.left))
					attemptAction(new Action(ActionType.MOVE_LEFT));
				else if(input.isKeyDown(kb.right))
					attemptAction(new Action(ActionType.MOVE_RIGHT));
				else if(input.isKeyDown(kb.up))
					attemptAction(new Action(ActionType.MOVE_UP));
				else if(input.isKeyDown(kb.down))
					attemptAction(new Action(ActionType.MOVE_DOWN));
				else
					attemptAction(new Action(ActionType.STOP_MOVING));
			} else {
				
			}
		}
		else if (!EpicGameContainer.MAIN.shopIsOpen){
			if (key==kb.menu){
			} else if(key==kb.left){
				EpicGameContainer.MAIN.menu.leftPressed = false;	
			} else if(key==kb.right){
				EpicGameContainer.MAIN.menu.rightPressed = false;	
			} else if(key==kb.down){
				EpicGameContainer.MAIN.menu.downPressed = false;	
			} else if(key==kb.up){
				EpicGameContainer.MAIN.menu.upPressed = false;	
			}
			else {
			}
		}
		else {
			if (key==kb.menu){
			} else if(key==kb.left){
				EpicGameContainer.MAIN.shop.leftPressed = false;	
			} else if(key==kb.right){
				EpicGameContainer.MAIN.shop.rightPressed = false;	
			} else if(key==kb.down){
				EpicGameContainer.MAIN.shop.downPressed = false;	
			} else if(key==kb.up){
				EpicGameContainer.MAIN.shop.upPressed = false;	
			}
			else {
			}
		}
		EpicGameContainer.MAIN.keyBind(key, c);
	}
	
	private void attemptAction(Action action) {
		EpicGameContainer.MAIN.protocol.sendAction(action);
		
	}
}
