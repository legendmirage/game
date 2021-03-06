package battle.ai;

import model.GameModel;
import util.U;
import battle.BattleCreature;
import client.ActionType;

public class DustlingAI extends AI {
	/** Flag to check if performed ability */
	private boolean casted;
	boolean firstSpeed;
	public DustlingAI(BattleCreature cr) {
		super(cr);
		this.casted = false;
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
			if (adx>cr.getWidth()) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			}
		}
		if (adx<cr.getWidth()) {
			if (U.r()<0.1) {
				if (!this.casted) {
					act(ActionType.STOP_MOVING);
					this.casted = true;
				}
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
		}
	}
}
