package ability.type;

import ability.effect.Projectile;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Ability which fires three ice projectiles forward <br>
 * arg 0 : Speed pixels per second <br>
 * arg 1 : Damage of each projectile <br>
 * arg 2 : Duration of projectile <br>
 */
public class IceSpikes implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		int duration = (int)a2;
		int dAngle = 30;
		if (cr.facingRight) {
			int vx = (int) (a0*Math.cos(Math.toRadians(0)));
			int vy = (int) (a0*Math.sin(Math.toRadians(0)));
			Projectile proj = new Projectile(cr.id, cr.mx(), cr.my(), vx, vy, (int)a1, duration);
			bm.addAbilityEffect(proj);
			vx = (int) (a0*Math.cos(Math.toRadians(360-dAngle)));
			vy = (int) (a0*Math.sin(Math.toRadians(360-dAngle)));
			proj = new Projectile(cr.id, cr.mx(), cr.my(), vx, vy, (int)a1, duration);
			bm.addAbilityEffect(proj);
			vx = (int) (a0*Math.cos(Math.toRadians(dAngle)));
			vy = (int) (a0*Math.sin(Math.toRadians(dAngle)));
			proj = new Projectile(cr.id, cr.mx(), cr.my(), vx, vy, (int)a1, duration);
			bm.addAbilityEffect(proj);
		} else {
			int vx = (int) (a0*Math.cos(Math.toRadians(180)));
			int vy = (int) (a0*Math.sin(Math.toRadians(180)));
			Projectile proj = new Projectile(cr.id, cr.mx(), cr.my(), vx, vy, (int)a1, duration);
			bm.addAbilityEffect(proj);
			vx = (int) (a0*Math.cos(Math.toRadians(180-dAngle)));
			vy = (int) (a0*Math.sin(Math.toRadians(180-dAngle)));
			proj = new Projectile(cr.id, cr.mx(), cr.my(), vx, vy, (int)a1, duration);
			bm.addAbilityEffect(proj);
			vx = (int) (a0*Math.cos(Math.toRadians(180+dAngle)));
			vy = (int) (a0*Math.sin(Math.toRadians(180+dAngle)));
			proj = new Projectile(cr.id, cr.mx(), cr.my(), vx, vy, (int)a1, duration);
			bm.addAbilityEffect(proj);
		}
		
	}

	@Override
	public String getAbilityName() {
		return "Fire three projectiles";
	}

}
