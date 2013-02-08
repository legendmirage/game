package battle.ai;

import client.ActionType;
import util.U;
import model.GameModel;
import battle.BattleCreature;

public class CoryAI extends AI {
	private boolean avoiding;
	public CoryAI(BattleCreature cr) {
		super(cr);
		this.avoiding = false;
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
		if (U.r()<0.05 && !avoiding) {
			if (adx>200) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			}
		}
		if(U.r()<0.05 && !avoiding) {
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
		
		if (avoiding) {
			if (dist>250) {
				this.avoiding = false;
				act(ActionType.STOP_FLYING);
				act(ActionType.STOP_MOVING);
			}
			if (dx<0) {
				act(ActionType.MOVE_RIGHT);
			} else {
				act(ActionType.MOVE_LEFT);
			}
			if (dy<0) {
				act(ActionType.MOVE_DOWN);
			} else {
				act(ActionType.MOVE_UP);
			}
		}
		
		//Cast Assasinate
		if (U.r()<0.001) {
			if (ady<cr.getHeight() && adx<400) {
				act(ActionType.STOP_FLYING);
				act(ActionType.STOP_MOVING);
				float oldpx = cr.getScreenX();
				if (dx<0) {
					cr.facingRight = false;
				} else {
					cr.facingRight = true;
				}
				if (bm.map.collideRectWall(cr)) {
					cr.setScreenX(oldpx);
				}
				act(ActionType.ABILITY, "0");
			}
			return;
		}
		
		//Cast Chain Lightning
		if (U.r()<0.01) {
			if (dist<500) {
				float oldpx = cr.getScreenX();
				if (dx<0) {
					cr.facingRight = false;
				} else {
					cr.facingRight = true;
				}
				if (bm.map.collideRectWall(cr)) {
					cr.setScreenX(oldpx);
				}
				act(ActionType.ABILITY, "2");
			}
			return;
		}
		
		//Cast Whirlwind
		if (U.r()<0.005) {
			if (dist<175) {
				float oldpx = cr.getScreenX();
				if (dx<0) {
					cr.facingRight = false;
				} else {
					cr.facingRight = true;
				}
				if (bm.map.collideRectWall(cr)) {
					cr.setScreenX(oldpx);
				}
				act(ActionType.ABILITY, "1");
				this.avoiding = true;
				act(ActionType.STOP_FLYING);
				act(ActionType.STOP_MOVING);
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
