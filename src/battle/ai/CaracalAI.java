package battle.ai;

import model.GameModel;
import util.U;
import client.ActionType;
import ability.type.AbilityConstants;
import battle.BattleCreature;

public class CaracalAI extends AI {
	private boolean up;

	public CaracalAI(BattleCreature cr) {
		super(cr);
		this.up = false;
	}

	@Override
	public void run() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		float dist = (float) this.dist(closestPlayer.mx(), closestPlayer.my());
		if(U.r()<0.05) {
			if (!up && adx>cr.getWidth()*3) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (ady>cr.getHeight()) {
				if (dy<0) {
					act(ActionType.JUMP);
				} else {
					up = true;
					act(ActionType.MOVE_LEFT);
				}
			} else {
				up = false;
				act(ActionType.STOP_MOVING);
			}
		}
		
		// Cast Laser beam
		if (U.r()<0.01) {
			if (ady<cr.getHeight() && adx<AbilityConstants.PROJECTILE_RANGE) {
				float oldpx = cr.getScreenX();
				if (dx>0) {
					cr.facingRight = true;
				} else {
					cr.facingRight = false;
				}
				if (bm.map.collideRectWall(cr)) {
					cr.setScreenX(oldpx);
				}
				act(ActionType.ABILITY, "0");
				return;
			}
		}
		
		// Cast Bind
		if (U.r()<0.01) {
			if (dist<250) {
				float oldpx = cr.getScreenX();
				if (dx>0) {
					cr.facingRight = true;
				} else {
					cr.facingRight = false;
				}
				if (bm.map.collideRectWall(cr)) {
					cr.setScreenX(oldpx);
				}
				act(ActionType.ABILITY, "0");
			}
			return;
		}

	}
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
