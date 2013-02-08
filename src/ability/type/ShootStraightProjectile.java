package ability.type;

import ability.effect.Projectile;
import battle.BattleCreature;
import battle.BattleModel;

/** Shoots a straight projectile horizontally in the direction the caster is facing. <br>
 * The projectile does single-target damage when it collides into an enemy. <br>
 * arg0: speed, pixels per second <br>
 * arg1: damage <br>
 * arg2: duration <br>
 */
public class ShootStraightProjectile implements AbilityType {
	
	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		
		bm.addAbilityEffect(new Projectile(cr.id, cr.getScreenX()+(cr.facingRight?cr.getWidth()+1:-1), 
				cr.getScreenY()+cr.getHeight()/2, cr.facingRight?a0:-a0, 0, (int)a1, (int)a2));
	}

	@Override
	public String getAbilityName() {
		return "Shoot a basic projectile";
	}
}
