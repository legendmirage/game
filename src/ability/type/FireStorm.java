package ability.type;

import ability.effect.AbilityEffect;
import ability.effect.PenetratingProjectile;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Summons a huge storm which does damage to everything in its path <br>
 * arg 0 : radius in pixels <br>
 * arg 1 : damage per second <br>
 */
public class FireStorm implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		AbilityEffect penPro = new PenetratingProjectile(cr.id, 5000, (int)a1, 
				cr.getScreenX()+(cr.facingRight?cr.getWidth()+1:-1), 
				cr.getScreenY()+cr.getHeight()/2, (cr.facingRight?1:-1)*100, 0, a0);
		bm.addAbilityEffect(penPro);
	}

	@Override
	public String getAbilityName() {
		return "Summon a traveling firestorm";
	}

}
