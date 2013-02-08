package ability.type;

import ability.effect.Invincibility;
import battle.BattleCreature;
import battle.BattleModel;

/** Shield yourself for a small time. The time duration is in number off ticks. <br>
 *  arg 0 : Duration of the shield <br>
*/
public class Shield implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		Invincibility in = new Invincibility(cr.id, (int)a0);
		bm.addAbilityEffect(in);
	}

	@Override
	public String getAbilityName() {
		return "Basic shield";
	}

}
