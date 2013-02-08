package ability.type;

import ability.effect.DamageTouchingEnemies;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Ability which propels enemies around you into the air.  <br>
 * arg 0 : damage per second <br>
 */
public class Torch implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		bm.addAbilityEffect(new DamageTouchingEnemies(cr.id, (int)a0, 300));
	}

	@Override
	public String getAbilityName() {
		return "Damages touching enemies";
	}
}
