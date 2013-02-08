package ability.type;

import battle.BattleCreature;
import battle.BattleModel;

/** Heals the user for some fixed number of action points, up to his maximum action points. <br>
 * arg 0 : the amount of AP to restore <br>
 */
public class SelfRestoreAP implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		cr.ap = Math.min(cr.ap+(int)a0, cr.maxAP);
	}

	@Override
	public String getAbilityName() {
		return "Restore AP";
	}
}
