package battle.ai;

import model.GameModel;
import util.U;
import battle.BattleCreature;
import client.ActionType;

public class TeaCupAI extends AI {
	private boolean moved;
	public TeaCupAI(BattleCreature cr) {
		super(cr);
		this.moved = false;
	}

	@Override
	public void run() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float angleInRads = (float) getAngle(cr.mx(), cr.my(), closestPlayer.mx(), closestPlayer.my());
		float angle = (float) Math.toDegrees(angleInRads);
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		if(U.r()<0.05) {
			if (adx>150) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			}
		}
		if(U.r()<0.05) {
			if (ady>cr.getHeight()) {
				if (dy<0) {
					act(ActionType.MOVE_UP);
				} else {
					act(ActionType.MOVE_DOWN);
				}
			} else {
				act(ActionType.STOP_FLYING);
			}
		}
		if (dist(closestPlayer.mx(),closestPlayer.my())<cr.getWidth()*2) {
			act(ActionType.STOP_MOVING);
			//Suicide attack
			act(ActionType.ABILITY, "0");
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
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
