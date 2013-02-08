package ability.type;

import ability.effect.ContinuousLaserBeam;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * An Ability which shoots a giant laser across the screen <br>
 * arg 0 : Damage per second <br>
 * arg 1 : Height of the laser <br>
 * arg 2 : Duration of the laser <br>
 */
public class GiantLaserBeam implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float arg0, float arg1, float arg2) {
		ContinuousLaserBeam clb = new ContinuousLaserBeam(cr.id, cr.getScreenX()+(cr.facingRight?cr.getWidth()+1:-1), 
				cr.getScreenY()+cr.getHeight()/2, (int)arg2, (int)arg0, (int)arg1);
		bm.addAbilityEffect(clb);
	}

	@Override
	public String getAbilityName() {
		return "Shoots a giant laser across the screen";
	}

}
