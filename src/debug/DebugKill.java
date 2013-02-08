package debug;

import java.util.ArrayList;

import battle.BattleCreature;
import battle.BattleModel;
import battle.BattlePlayer;
import util.Logger;

public class DebugKill extends DebugCommand {

	@Override
	public String run(String args) {
		Logger.log("Killing.. MUHAHAHAHAHAHAHAH");
		for (BattleCreature cr : BattleModel.MAIN.creatures.values()) {
			if (cr instanceof BattlePlayer) continue;
			cr.takeDamage(cr.maxHP);
		}
		return null;
	}

}
