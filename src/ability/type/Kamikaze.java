package ability.type;

import ability.effect.ExpandingCircle;
import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

/**
 * An ability which makes you blow up and damage enemies nearby. <br>
 * Basically a suicide bomber. <br>
 * arg 0 : radius of the suicide bomber in pixels <br>
 * arg 1 : damage done by kamikaze <br>
 * arg 2 : Impulse velocity of Kamikaze <br>
 */
public class Kamikaze implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		bm.addAbilityEffect(new ExpandingCircle(cr.id, cr.mx(), cr.my(), 10, a0));
		a0 = cr.getWidth()/2+a0;
		for (BattleCreature enemy : bm.creatures.values()) {
			if (cr.sameTeam(enemy) || enemy.alive == false)
				continue;
			float dist = (cr.mx() - enemy.mx()) * (cr.mx() - enemy.mx())
					+ (cr.my() - enemy.my()) * (cr.my() - enemy.my());
			if (dist > a0 * a0)
				continue;
			double angleRadians = getAngle(cr.mx(), cr.getScreenY() + cr.getHeight(),
					enemy.mx(), enemy.getScreenY() + enemy.getHeight());
			float vx = (float) (Math.cos(angleRadians) * a2);
			float vy = (float) (Math.sin(angleRadians) * a2);
			enemy.applyImpulse(vx, vy, ImpulseForce.TAKE_DAMAGE, 5, 5);
			enemy.takeDamage((int)a1);
		}
		cr.takeDamage(cr.maxHP);
		cr.die();
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
		return "Kamikaze attack";
	}

}
