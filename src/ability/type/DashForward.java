package ability.type;

import battle.BattleConstants;
import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

/** Dash forward a short distance. Implemented as a forward impulse. <br> 
 * arg0: duration in ticks <br>
 */
public class DashForward implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		float speed = cr.getMoveSpeed() * BattleConstants.PLAYER_DASH_SPEED_MULTIPLIER;
		cr.applyImpulse((cr.facingRight?1:-1) * speed, 0, ImpulseForce.DASH, (int)a0, 0);
	}

	@Override
	public String getAbilityName() {
		return "Dash forward";
	}

}
