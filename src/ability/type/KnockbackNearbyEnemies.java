package ability.type;

import util.Pair;
import ability.effect.ExpandingCircle;
import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

/**
 * The ability knocks back enemy within a radius of the user. <br>
 * arg 0 : Range of the ability in pixels <br>
 * arg 1 : Damage of the ability <br>
 * arg 2 : Impulse Velocity of the ability <br>
 */
public class KnockbackNearbyEnemies implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		bm.addAbilityEffect(new ExpandingCircle(cr.id, cr.mx(), cr.my(), 10, a0));
		for (Pair<Integer, Float> p : bm.collideCircle(cr.mx(), cr.my(), a0)) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if (cr.sameTeam(enemy) || !enemy.alive) continue;
			double angleRadians = getAngle(cr.mx(), cr.getScreenY() + cr.getHeight(),
					enemy.mx(), enemy.getScreenY() + enemy.getHeight());
			float vx = (float) (Math.cos(angleRadians) * a2);
			float vy = (float) (Math.sin(angleRadians) * a2);
			enemy.applyImpulse(vx, vy, ImpulseForce.TAKE_DAMAGE, 40, 40);
			if (p.getVal2()>0) {
				enemy.takeDamage((int)a1);
			}
		}
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
		return "Knockback enemies";
	}
}
