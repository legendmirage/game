package ability.type;

import battle.BattleCreature;
import battle.BattleModel;

/**
 * The next source of damage to the creature is multiplied by a multiplier. <br>
 * arg 0 : multiplier (0.1f would reduce damage by 90%) <br>
 */
public class IceShield implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		cr.iceShieldMultiplier = Math.min(cr.iceShieldMultiplier, a0);
	}

	@Override
	public String getAbilityName() {
		return "Reduce damage done to you";
	}

}
