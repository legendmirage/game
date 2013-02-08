package battle.ai;

import client.ActionType;
import util.U;
import model.GameModel;
import battle.BattleCreature;

public class RukhAI extends AI {
	private boolean shield;
	public RukhAI(BattleCreature cr) {
		super(cr);
		this.shield = false;
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
		
		if (cr.shield) {
			shield = true;
		} else {
			shield = false;
		}
		
		if (!shield) {
			if (U.r()<0.05) {
				if (adx>cr.getWidth()*2) {
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
				} else if (adx<cr.getWidth()*1.5) {
					if (dx<0) {
						act(ActionType.MOVE_RIGHT);
					} else {
						act(ActionType.MOVE_LEFT);
					}
				}
				if (ady>cr.getHeight()*2) {
					if (dy<0) {
						act(ActionType.MOVE_UP);
					} else {
						act(ActionType.MOVE_DOWN);
					}
				} else if (ady<150) {
					act(ActionType.MOVE_UP);
				}
			}
		} else if (shield) {
			if (U.r()<0.05) {
				if (adx>cr.getWidth()*2) {
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
				} else if (adx<cr.getWidth()*1.5) {
					if (dx<0) {
						act(ActionType.MOVE_RIGHT);
					} else {
						act(ActionType.MOVE_LEFT);
					}
				}
				if (ady>closestPlayer.getHeight()) {
					if (dy<0) {
						act(ActionType.MOVE_UP);
					} else {
						act(ActionType.MOVE_DOWN);
					}
				}
			}
		}
		
		
		//Cast IceSpike
		if (U.r()<0.005) {
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
		
		//Cast Shield
		if (U.r()<0.005) {
			act(ActionType.ABILITY, "1");
			shield = true;
		}
	}
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
