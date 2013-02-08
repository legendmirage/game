package ability.type;

import model.GameModel;
import ability.effect.Projectile;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Ability which shoots a projectile guided to the nearest enemy. <br>
 * Should not be used by a player. <br>
 * arg 0 : speed in pixels per second <br>
 * arg 1 : damage <br>
 * arg 2 : duration <br>
 */
public class ShootGuidedProjectile implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		BattleCreature player = bm.creatures.get(GameModel.MAIN.player.id);
		if (player == null) return;
		double angleRadians = getAngle(cr.mx(), cr.my(), player.mx(), player.my());
		float vx = (float) (Math.cos(angleRadians) * a0);
		float vy = (float) (Math.sin(angleRadians) * a0);
		Projectile proj = new Projectile(cr.id, cr.getScreenX()+(cr.facingRight?cr.getWidth()+1:-1), cr.my(), vx, vy, (int)a1, (int)a2);
		bm.addAbilityEffect(proj);
	}
	
	/**
	 * Calculates angle between two points and the x-axis where (x1,y1) is taken
	 * to be on the x-axis
	 * 
	 * @param x1
	 *            x co-ordinate of the point on the X-axis
	 * @param y1
	 *            y co-ordinate of the point on the X-axis
	 * @param x2
	 *            x co-ordinate of the second point
	 * @param y2
	 *            y co-ordinate of the second point
	 * @return the angle between the line joining the two points and the X-axis
	 *         in Radians
	 */
	private static double getAngle(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		// Minus to correct for coord re-mapping
		double dy = -(y2 - y1);

		double inRads = Math.atan2(dy, dx);

		// We need to map to coord system when 0 degree is at 3 O'clock, 270 at
		// 12 O'clock
		if (inRads < 0)
			inRads = Math.abs(inRads);
		else
			inRads = 2 * Math.PI - inRads;

		return inRads;
	}

	@Override
	public String getAbilityName() {
		return "Shoot guided missile";
	}
}
