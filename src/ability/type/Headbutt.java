package ability.type;

import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

/**
 * Ability which headbutts the person standing in front if any. <br>
 * arg 0 : duration of bump impulse <br>
 * arg 1 : damage of the ability <br>
 * arg 2 : impulseVelocity of the ability <br>
 */
public class Headbutt implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		for (BattleCreature enemy: bm.creatures.values()) {
			if(cr.sameTeam(enemy) || enemy.alive==false) continue;
			float vx = a2;
			float vy = 0;
			float x = cr.facingRight ? (cr.getScreenX()+cr.getWidth())+3 : cr.getScreenX()-3;
			if(enemy.isInHitbox(x, cr.getScreenY()+5) || enemy.isInHitbox(x, cr.getScreenY()+cr.getHeight()-5)) {
				//double angleRadians = getAngle(cr.mx(), cr.my(), enemy.mx(), enemy.my());
				if (!cr.facingRight) {
					vx = -a2;
				}
				enemy.applyImpulse(vx, vy, ImpulseForce.TAKE_DAMAGE, (int)a0, (int)a0);
				enemy.takeDamage((int)a2);
				break;
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
	/*
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
*/
	@Override
	public String getAbilityName() {
		return "Headbutt the enemy in front of you";
	}
}
