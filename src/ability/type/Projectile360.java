package ability.type;

import util.GameConstants;
import ability.effect.Projectile;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * An ability which fires projectiles all around. The number of projectiles has to be a factor of 360. <br>
 * arg 0 : Speed , pixels per second (Range is 3 times the speed ) <br>
 * arg 1 : Damage <br>
 * arg 2 : Number of projectiles <br>
 *
 */
public class Projectile360 implements AbilityType {
	
	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		int duration = (int)(Math.ceil(AbilityConstants.PROJECTILE360_RANGE/a0*1000/GameConstants.GAME_SPEED));
		int angle = (int) (360/a2);
		for (int i=0; i<360; i=i+angle) {
			int vx = (int) (a0*Math.cos(Math.toRadians(i)));
			int vy = (int) (a0*Math.sin(Math.toRadians(i)));
			float dt = GameConstants.GAME_SPEED/1000.0f;
			float x = (cr.mx() + vx*dt);
			float y = (cr.my() + vy*dt);
			while (cr.isInHitbox(x, y)) {
				x += vx*dt*2;
				y += vy*dt*2;
			}
			Projectile proj = new Projectile(cr.id, x, y, vx, vy, (int)a1, duration);
			bm.addAbilityEffect(proj);
		}
	}

	@Override
	public String getAbilityName() {
		return "Send projectiles in a circle";
	}
}
