package ability.type;

import ability.effect.HomingProjectile;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Creates a homing projectile which initially goes upward and then curves towards the nearest enemy. <br>
 * arg 0 : damage <br>
 * arg 1 : duration in ticks <br>
 */
public class SeekingWind implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		bm.addAbilityEffect(new HomingProjectile(cr.id, cr.getScreenX(), cr.getScreenY(), 0, -200, (int)a0, (int)a1));
	}
	@Override
	public String getAbilityName() {
		return "Summon a homing missile";
	}

}
