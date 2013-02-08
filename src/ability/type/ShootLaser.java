package ability.type;

import ability.effect.LaserBeam;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Ability which shoots a laser like the star wars gun <br>
 * arg 0 : Length of the laser beam <br>
 * arg 1 : Damage per second done by laser beam <br>
 * arg 2 : Duration of the Beam <br>
 */
public class ShootLaser implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float arg0, float arg1, float arg2) {
		LaserBeam lb = new LaserBeam(cr.id, cr.getScreenX()+(cr.facingRight?cr.getWidth()+1:-1), 
				cr.getScreenY()+cr.getHeight()/2, cr.facingRight?AbilityConstants.LASER_BEAM_SPEED:-AbilityConstants.LASER_BEAM_SPEED, 
				0, (int) arg2, (int) arg1, cr.facingRight?(int)arg0:-(int)arg0);
		bm.addAbilityEffect(lb);
	}

	@Override
	public String getAbilityName() {
		return "Shoot a laser beam";
	}

}
