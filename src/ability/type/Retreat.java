package ability.type;

import battle.BattleCreature;
import battle.BattleModel;
import battle.BattlePlayer;

/**
 * Ability by which the player can retreat from battle <br>
 */
public class Retreat implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		if(!(cr instanceof BattlePlayer)) return;
		
		BattleModel.MAIN.retreatFlag = true;

	}

	@Override
	public String getAbilityName() {
		return "Retreat from battle";
	}

}
