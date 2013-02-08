package battle.ai;

import client.ActionType;
import util.U;
import model.GameModel;
import battle.BattleCreature;

public class VegaAI extends AI {
	private boolean hooked;
	public VegaAI(BattleCreature cr) {
		super(cr);
		this.hooked = false;
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
		if (U.r()<0.05) {
			if (adx>cr.getWidth()*2) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			}
		}
		if (U.r()<0.05) {
			if (adx>cr.getHeight()*2) {
				if (dy<0) {
					act(ActionType.MOVE_UP);
				} else {
					act(ActionType.MOVE_DOWN);
				}
			}
		}
		
		if (U.r()<0.01) {
			if (dist<800) {
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
				hooked = true;
			}
		}
		if (U.r()<0.01 || (U.r()<0.2 && hooked)) {
			if (dist<350) {
				act(ActionType.ABILITY, "1");
			}
		}
	}
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
