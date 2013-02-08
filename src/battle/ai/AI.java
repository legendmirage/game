package battle.ai;

import client.ActionType;
import model.GameModel;
import NetworkUtil.Action;
import battle.BattleCreature;
import battle.BattleModel;

/** Artificial intelligence that controls the enemy behavior in battle. <br>
 * Each implementation of this interface describes a different behavior. <br>
 * The same behavior can then be attached to different enemies. <br>
 */
public abstract class AI {
	BattleCreature cr;
	BattleModel bm;
	public AI(BattleCreature cr) {
		this.cr = cr;
		this.bm = BattleModel.MAIN;
	}
	/** The AI controls the behavior of the given creature in the given battle for the current tick. */
	public abstract void run();
	
	protected void act(ActionType type, String arg) {
		GameModel.MAIN.applyAction(new Action(cr.id, type, arg));
	}
	protected void act(ActionType type) {
		GameModel.MAIN.applyAction(new Action(cr.id, type));
	}
}
