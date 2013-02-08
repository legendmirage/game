package battle.ai;

import model.GameModel;
import util.U;
import client.ActionType;
import battle.BattleCreature;

public class BookmanAI extends AI {
	private boolean fire;

	boolean firstSpeed;
	public BookmanAI(BattleCreature cr) {
		super(cr);
		this.fire = false;

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
			if (adx>cr.getWidth()*1.5) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else {
				act(ActionType.STOP_MOVING);
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
		
		//Cast Ice Spikes
		if (U.r()<0.005 || (U.r()<0.01 && fire)) {
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
			return;
		}
		
		//Cast Taunt
		if (U.r()<0.01) {
			if (adx<400 && ady<cr.getHeight()) {
				act(ActionType.ABILITY, "1");
				fire = true;
			}
		}
	}
}
