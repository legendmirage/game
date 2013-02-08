package ability.type;

import util.GameConstants;
import ability.effect.Projectile;
import ability.effect.ProjectileSpawn;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * An ability which shoots projectiles all around spawning more projectiles <br>
 * arg 0 : Damage of each projectile <br>
 * arg 1 : Number of initial projectiles <br>
 * arg 2 : Number of projectiles to spawn <br>
 */
public class Projectile360Reloaded implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float arg0, float arg1, float arg2) {
		int angle = (int) (360/(int)arg1);
		for (int i=0; i<360; i=i+angle) {
			int vx = (int) (300*Math.cos(Math.toRadians(i)));
			int vy = (int) (300*Math.sin(Math.toRadians(i)));
			float dt = GameConstants.GAME_SPEED/1000.0f;
			float x = (cr.mx() + vx*dt);
			float y = (cr.my() + vy*dt);
			while (cr.isInHitbox(x, y)) {
				x += vx*dt*2;
				y += vy*dt*2;
			}
			Projectile proj = new ProjectileSpawn(cr.id, x, y, vx, vy, (int)arg0, 100, (int)arg2);
			bm.addAbilityEffect(proj);
		}
	}

	@Override
	public String getAbilityName() {
		return "An ability which shoots projectiles all around spawning more projectiles";
	}

}
