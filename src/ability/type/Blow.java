package ability.type;

import ability.effect.AbilityEffect;
import ability.effect.BlowBack;
import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

/**
 * Ability which blows forward sending an enemy in front flying back <br>
 * arg 0 : Impulse velocity of blow <br>
 * arg 1 : Damage done by the ability <br>
 * arg 2 : Duration of the impulse <br>
 */
public class Blow implements AbilityType {
	// TODO : Something about the radius of the ability for rendering
	
	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		float vx = a0;
		if (!cr.facingRight) {
			vx = -a0;
		}
		AbilityEffect air = new BlowBack(cr.id, cr.mx(), cr.my(), (int)a2);
		bm.addAbilityEffect(air);
		BattleCreature closestCreature = null;
		float d = Float.MAX_VALUE;
		for(BattleCreature enemy: bm.creatures.values()) {
			if (cr.id==enemy.id) continue;
			if(!enemy.alive) continue;
			if(enemy.sameTeam(cr)) continue;
			if(cr.facingRight && enemy.getScreenX()<cr.getScreenX()) continue;
			if(!cr.facingRight && enemy.getScreenX()>cr.getScreenX()) continue;
			if(enemy.getScreenY()>cr.getScreenY()+cr.getHeight() || enemy.getScreenY()+enemy.getHeight()<cr.getScreenY()) continue;
			float curD = (cr.mx()-enemy.mx())*(cr.mx()-enemy.mx())+(cr.my()-enemy.my())*(cr.my()-enemy.my());
			if(curD < d) {
				d = curD;
				closestCreature = enemy;
			}
		}
		if (closestCreature==null) return;
		closestCreature.applyImpulse(vx, 0, ImpulseForce.TAKE_DAMAGE, (int)a2, (int)a2);
		closestCreature.takeDamage((int)a1);
	}

	@Override
	public String getAbilityName() {
		return "Knocks back closest enemy dealing damage";
	}
}
