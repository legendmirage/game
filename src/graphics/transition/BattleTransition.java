package graphics.transition;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import util.ModelUtil;
import battle.BattleStateController;

public class BattleTransition extends HorizontalSplitTransition {
	

	@Override
	public void init(GameState firstState, GameState secondState) {
		super.init(firstState, secondState);
		
		try{
			((BattleStateController)firstState).updateState();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(StateBasedGame game, GameContainer container, int delta) throws SlickException {
		ModelUtil.updateModel(delta);
		super.update(game, container, delta);
	}


}
