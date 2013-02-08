package battle.ai;

import model.GameModel;
import util.U;
import battle.BattleCreature;
import client.ActionType;

/** An AI which does everything normally. Chases the nearest player and tries to kill it. */
public class TutorialAI extends AI {


	public TutorialAI(BattleCreature cr) {
		super(cr);
	}

	@Override
	public void run() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		float dx = closestPlayer.mx()-cr.mx();
		float adx = Math.abs(dx);
		if (U.r()<0.05) {
			if (adx>600) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else {
				act(ActionType.STOP_MOVING);
			}
		}
		
		if (U.r()<0.002) {
			act(ActionType.ABILITY, "0");
		}
	}

}
