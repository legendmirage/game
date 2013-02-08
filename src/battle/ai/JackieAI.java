package battle.ai;

import client.ActionType;
import util.U;
import model.GameModel;
import battle.BattleCreature;

public class JackieAI extends AI {
	private boolean torching;
	private int torchingCounter;
	private boolean hooked;
	private int hookedCounter;
	public JackieAI(BattleCreature cr) {
		super(cr);
		this.torching = false;
		this.torchingCounter = 0;
		this.hooked = false;
		this.hookedCounter = 0;
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
		
		if (hooked) {
			hookedCounter--;
			if (hookedCounter==0) {
				hooked = false;
			}
		}
		if (torching) {
			torchingCounter--;
			if (torchingCounter==0) {
				torching = false;
			}
		}
		
		if (U.r()<0.05 && !torching) {
			if (adx>400) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (adx<200) {
				if (dx<0) {
					act(ActionType.MOVE_RIGHT);
				} else {
					act(ActionType.MOVE_LEFT);
				}
				if (ady<cr.getHeight() && !torching && !hooked) {
					act(ActionType.JUMP);
				}
			}
		} else if (torching) {
			if (dx<0) {
				act(ActionType.MOVE_LEFT);
			} else {
				act(ActionType.MOVE_RIGHT);
			}
			if (ady>cr.getHeight()) {
				if (dy<0) {
					act(ActionType.JUMP);
				} else {
					act(ActionType.MOVE_LEFT);
				}
			}
		}
		
		// Cast Grapple Hook
		if (U.r()<0.005 && hookedCounter==0 && !torching) {
			if (dist<700) {
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
				hooked = true;
				hookedCounter = 40;
			}
		}
		
		// Cast Torch
		if (U.r()<0.005 || (U.r()<0.01 && hooked)) {
			act(ActionType.ABILITY, "0");
			torching = true;
			torchingCounter = 50;
		}
	}
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
