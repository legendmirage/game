package battle.ai;

import client.ActionType;
import util.U;
import battle.BattleCreature;

public class NightShadeAI extends AI {

	public NightShadeAI(BattleCreature cr) {
		super(cr);
	}

	@Override
	public void run() {
		if (U.r()<0.01) {
			act(ActionType.ABILITY, "0");
		} else if (U.r()<0.01) {
			act(ActionType.ABILITY, "1");
		}
		
	}

}
