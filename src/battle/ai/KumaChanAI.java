package battle.ai;

import model.GameModel;
import util.U;
import client.ActionType;
import battle.BattleCreature;

public class KumaChanAI extends AI {
	boolean firstSpeed;
	public KumaChanAI(BattleCreature cr) {
		super(cr);

		firstSpeed = false;
	}

	@Override
	public void run() {

		if (firstSpeed){
			cr.moveSpeedMultiplier = (float) (U.r()*0.4+ 0.6);
			firstSpeed = false;
		}
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		if(U.r()<0.05) {
			if (adx>400) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (ady>100) {
				if (dy<0) {
					act(ActionType.JUMP);
				} else {
					act(ActionType.MOVE_LEFT);
				}
			}
		}
		
		//Cast FireStorm
		if (U.r()<0.005) {
			if (adx>100 && adx<600) {
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
		
		//Cast HailStorm
		if (U.r()<0.005) {
			if (adx<400) {
				act(ActionType.ABILITY, "1");
			}
			return;
		}
	}

}
