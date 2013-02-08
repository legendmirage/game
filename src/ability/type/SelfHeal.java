package ability.type;

import battle.BattleCreature;
import battle.BattleModel;

/** Heals the user for some fixed number of hit points, up to his maximum health. <br>
 * arg 0 : the amount to heal <br>
 */
public class SelfHeal implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		cr.hp = Math.min(cr.hp+(int)a0, cr.maxHP);
	}

	@Override
	public String getAbilityName() {
		return "Basic heal";
	}
}
