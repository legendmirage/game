package ability.type;

import ability.effect.StraightBeam;
import battle.BattleCreature;
import battle.BattleModel;

/** Shoots a beam (imagine a jet of water) that goes in the direction the creature <br>
 * is facing for a fixed length. Damages everything in the beam, lasts a fixed time. <br>
 * arg 0 : Length of beam in pixels <br>
 * arg 1 : Damage per second <br>
 * arg 2 : Duration of the beam <br>
 */
public class ShootLastingBeam implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		bm.addAbilityEffect(new StraightBeam(cr.id, a0, (int)a1, (int)a2));
	}

	@Override
	public String getAbilityName() {
		return "Beam that damages everything in its path";
	}
}
