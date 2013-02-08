package battle.ai;

import model.GameModel;
import util.U;
import battle.BattleCreature;
import client.ActionType;

public class ShieldFighterAI extends AI {
	private boolean casting;
	boolean firstSpeed;
	public ShieldFighterAI(BattleCreature cr) {
		super(cr);
		this.casting = false;
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
			if (adx>cr.getWidth() || !casting) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			}
		}
		if (U.r()<0.03) {
			if (ady<cr.getHeight()) {
				if (adx<cr.getWidth()*1.5) {
					act(ActionType.STOP_MOVING);
					float oldpx = cr.getScreenX();
					if (dx>0) {
						cr.facingRight = true;
					} else {
						cr.facingRight = false;
					}
					if (bm.map.collideRectWall(cr)) {
						cr.setScreenX(oldpx);
					}
					//Melee
					act(ActionType.ABILITY, "0");
					casting = true;
				} else {
					casting = false;
				}
			} else {
				casting = false;
			}
		}
	}
}
