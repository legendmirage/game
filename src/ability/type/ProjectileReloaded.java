package ability.type;

import model.GameModel;
import ability.effect.ProjectileSpawn;
import battle.BattleCreature;
import battle.BattleModel;
import battle.BattlePlayer;

/**
 * Ability by which a projectile spawns more projectiles <br>
 * arg 0 : Damage of the projectiles <br>
 * arg 1 : Number of projectiles to disintegrate into <br>
 * arg 2 : Duration of the projectiles <br>
 */
public class ProjectileReloaded implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float arg0, float arg1, float arg2) {
		BattleCreature player = bm.creatures.get(GameModel.MAIN.player.id);
		float vx,vy;
		int angler;
		int anglel;
		if (!(cr instanceof BattlePlayer)) {
			if (player.my()>cr.my()) {
				angler = 45;
				anglel = 135;
			} else {
				angler = 315;
				anglel = 225;
			}
		} else {
			angler = 315;
			anglel = 225;
		}
		if (cr.facingRight) {
			vx = (int) (300*Math.cos(Math.toRadians(angler)));
			vy = (int) (300*Math.sin(Math.toRadians(angler)));
		} else {
			vx = (int) (300*Math.cos(Math.toRadians(anglel)));
			vy = (int) (300*Math.sin(Math.toRadians(anglel)));
		}
		ProjectileSpawn ps = new ProjectileSpawn(cr.id, cr.getScreenX()+(cr.facingRight?cr.getWidth()+1:-1), 
				cr.getScreenY()+cr.getHeight()/2, vx, vy, (int)arg0, (int)arg2, (int)arg1);
		bm.addAbilityEffect(ps);
	}

	@Override
	public String getAbilityName() {
		String s = "A projectile which disintegrates into more projectiles";
		return s;
	}

}
