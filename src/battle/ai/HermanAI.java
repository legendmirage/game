package battle.ai;

import model.GameModel;
import util.U;
import client.ActionType;
import ability.type.AbilityConstants;
import battle.BattleCreature;

public class HermanAI extends AI {
	private boolean runningAway;
	private int runningAwayCounter;
	private boolean casted;
	public HermanAI(BattleCreature cr) {
		super(cr);
		this.casted = false;
	}

	@Override
	public void run() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		if(U.r()<0.05) {
			if (!runningAway && adx>cr.getWidth()*3) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else {
				act(ActionType.STOP_MOVING);
			}
		}
		//Shoot homing projectile
		if (U.r()<0.01) {
			if (ady<cr.getHeight() && adx<AbilityConstants.PROJECTILE_RANGE+200 && !runningAway) {
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
				casted = true;
			}
		}
		if (U.r()<0.01 && !casted) {
			if (ady<cr.getHeight() && adx<600) {
				act(ActionType.ABILITY, "1");
			}
		}
		
		if (U.r()<0.04) {
			if (adx<cr.getWidth() && ady<cr.getHeight()) {
				if (dx<0) {
					act(ActionType.MOVE_RIGHT);
					runningAway = true;
				} else {
					act(ActionType.MOVE_LEFT);
					runningAway = true;
				}
			}
		}
		if (runningAway) {
			runningAwayCounter++;
			if (runningAwayCounter>BattleAIConstants.RUNNING_AWAY || adx>cr.getWidth()*4.5) {
				runningAway = false;
				runningAwayCounter = 0;
			}
		}
		casted = false;
	}
}
