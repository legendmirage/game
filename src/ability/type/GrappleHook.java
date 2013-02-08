package ability.type;

import model.GameModel;
import ability.effect.Hook;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * An Ability which pulls the person towards you <br>
 * arg 0 : Maximum distance <br>
 * arg 1 : Speed of the hook <br>
 * arg 2 : Damage (normally 0) <br>
 */
public class GrappleHook implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float arg0, float arg1, float arg2) {
		if (cr.grappleHook) return;
		cr.grappleHook = true;
		BattleCreature player = bm.creatures.get(GameModel.MAIN.player.id);
		double angleRadians = getAngle(cr.mx(), cr.getScreenY() + cr.getHeight(),
				player.mx(), player.getScreenY() + player.getHeight());
		float vx = (float) (Math.cos(angleRadians) * arg1);
		float vy = (float) (Math.sin(angleRadians) * arg1);
		if (vx>0) {
			cr.facingRight = true;
		} else {
			cr.facingRight = false;
		}
		Hook h = new Hook(cr.id, cr.mx(), cr.my(), vx, vy, (int)arg2, (int)arg0);
		bm.addAbilityEffect(h);
	}

	@Override
	public String getAbilityName() {
		return "A ability which pulls the person towards you";
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
}
