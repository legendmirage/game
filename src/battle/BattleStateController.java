package battle;
import entrypoint.Tutorial;
import graphics.PopupRenderer;
import graphics.StaticFXList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import util.GameConstants;
import util.KeyBindings;
import util.ModelUtil;
import NetworkUtil.Action;
import client.ActionType;
import client.EpicGameContainer;

/** The slick game state which encompasses a battle. 
 */
public class BattleStateController extends BasicGameState {
	
	/** Slick framework needs this. */
	private int stateID;
	/** Hook into the controller. */
	public Input input;
	
	/** Timestamp in milliseconds of the last release of the left arrow key. */
	private long lastLeftRelease;
	/** Timestamp in milliseconds of the last release of the right arrow key. */
	private long lastRightRelease;
	
	/** The current hud. */
	public BattleHUD hud;
	
	/** The current model (for the battle that the player is currently in. */
	public BattleModel model;

	public BattleStateController(int stateID) {
		this.stateID = stateID;
	}
	
	@Override
    public int getID() {
    	return stateID;
    }

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		input = gc.getInput();
		StaticFXList.load();
	}
	
	@Override 
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
		updateState();
	}
	
	/** Makes sure that the battle is set to the one my player is in. Transition states if necessary. 
	 * @throws SlickException */
	public void updateState() throws SlickException {
		if(BattleModel.MAIN==null) {
			System.gc();
			EpicGameContainer.MAIN.enterState(
					EpicGameContainer.OVERWORLD_STATE,
					new FadeOutTransition(Color.black, 150),
					new FadeInTransition(Color.black, 150));
		} else if(BattleModel.MAIN!=model) {
			model = BattleModel.MAIN;
			hud = new BattleHUD(model);
		}
	}
	
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	    
		if(BattleModel.MAIN==null) return;
		BattleCreature cr = model.creatures.get(EpicGameContainer.MAIN.myID);
		float cx = (cr.getScreenX()+cr.getWidth()/2);
		float cy = (cr.getScreenY()+cr.getHeight()/2);
		g.translate(GameConstants.SCREEN_WIDTH/2 - cx, 
				GameConstants.SCREEN_HEIGHT/2 - cy);
		model.render(gc, g);
		if (EpicGameContainer.showPopups) PopupRenderer.renderPermanentPopups(gc, g);
		g.resetTransform();
		hud.render(gc, g);

		if (EpicGameContainer.MAIN.debugConsoleFlag)
			EpicGameContainer.MAIN.debugConsole.render(gc, g);

		if (EpicGameContainer.MAIN.showTutorial) Tutorial.render(gc, g);
		if (EpicGameContainer.showPopups) PopupRenderer.renderImpermanentPopups(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	    KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;

		if(input.isKeyDown(kb.left) && !input.isKeyDown(kb.right)) {
		    attemptAction(new Action(ActionType.MOVE_LEFT));
		} else if(input.isKeyDown(kb.right) && !input.isKeyDown(kb.left)) { 
			attemptAction(new Action(ActionType.MOVE_RIGHT));
		} 
		
		if(input.isKeyDown(kb.ability0)) { 
			attemptAction(new Action(ActionType.ABILITY, "0"));
		}
		if(input.isKeyDown(kb.ability1)) { 
			attemptAction(new Action(ActionType.ABILITY, "1"));
		} 
		if(input.isKeyDown(kb.ability2)) { 
			attemptAction(new Action(ActionType.ABILITY, "2"));
		} 
		if(input.isKeyDown(kb.attack)) { 
			attemptAction(new Action(ActionType.BASIC_ATTACK));
		} 
		
		ModelUtil.updateModel(delta);
		updateState();
	}
	
	@Override
	public void keyPressed(int key, char c) {
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;

		if (EpicGameContainer.MAIN.debugConsoleFlag) EpicGameContainer.MAIN.debugConsole.keyPressed(key, c);
		else if(key==kb.left) { 
			attemptAction(new Action(ActionType.MOVE_LEFT));
			if(System.currentTimeMillis()-lastLeftRelease <= GameConstants.DOUBLE_TAP_THRESHOLD)
				attemptAction(new Action(ActionType.DASH));
		} else if(key==kb.right) { 
			attemptAction(new Action(ActionType.MOVE_RIGHT));
			if(System.currentTimeMillis()-lastRightRelease <= GameConstants.DOUBLE_TAP_THRESHOLD)
				attemptAction(new Action(ActionType.DASH));
		} else if(key==kb.up) { 
			Tutorial.setStepDone(4);
			attemptAction(new Action(ActionType.JUMP));
		} else if(key==kb.down) { 
			Tutorial.setStepDone(3);
			attemptAction(new Action(ActionType.DASH));
		} else if(key==kb.attack) { 
			Tutorial.setStepDone(1);
			attemptAction(new Action(ActionType.BASIC_ATTACK));
		} else if(key==kb.retreat) { 
			attemptAction(new Action(ActionType.RETREAT));
		} else if(key==kb.ability0) { 
			Tutorial.setStepDone(2);
			attemptAction(new Action(ActionType.ABILITY, "0"));
		} else if(key==kb.ability1) { 
			attemptAction(new Action(ActionType.ABILITY, "1"));
		} else if(key==kb.ability2) { 
			attemptAction(new Action(ActionType.ABILITY, "2"));
		} else if(key==kb.moonwalk) { 
			Tutorial.setStepDone(5);
			attemptAction(new Action(ActionType.START_MOONWALK));
		}
		
	}
	
	@Override
	public void keyReleased(int key, char c) {
		lastLeftRelease = -1;
		lastRightRelease = -1;
		KeyBindings kb = EpicGameContainer.MAIN.dvorak ? 
				KeyBindings.DVORAK : KeyBindings.QWERTY;
		if(key==kb.left) { 
			lastLeftRelease = System.currentTimeMillis();
			attemptAction(new Action(input.isKeyDown(kb.right) ? 
					ActionType.MOVE_RIGHT : ActionType.STOP_MOVING));
		} else if(key==kb.right) { 
			lastRightRelease = System.currentTimeMillis();
			attemptAction(new Action(input.isKeyDown(kb.left) ? 
					ActionType.MOVE_LEFT : ActionType.STOP_MOVING));
		} else if(key==kb.moonwalk) { 
			attemptAction(new Action(ActionType.STOP_MOONWALK));
		} else {
			EpicGameContainer.MAIN.keyBind(key, c);
		}
	}
	
	private void attemptAction(Action action) {
		EpicGameContainer.MAIN.protocol.sendAction(action);
	}
}
