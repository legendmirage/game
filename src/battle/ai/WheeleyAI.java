package battle.ai;

import model.GameModel;
import util.Logger;
import util.U;
import ability.effect.AbilityEffect;
import ability.effect.PenetratingProjectile;
import ability.effect.Projectile;
import ability.type.AbilityConstants;
import battle.BattleCreature;
import client.ActionType;

public class WheeleyAI extends AI {
	private boolean runningAway;
	private int runningAwayCounter;
	private boolean comingDown;
	public WheeleyAI(BattleCreature cr) {
		super(cr);
		this.runningAway = false;
		this.runningAwayCounter = 0;
		this.comingDown = false;
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
			if (!runningAway && adx>cr.getWidth()*5 && !comingDown) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (ady>cr.getHeight()) {
				if (dy<0) {
					act(ActionType.JUMP);
				} else {
					comingDown = true;
					act(ActionType.MOVE_LEFT);
				}
			} else {
				comingDown = false;
				act(ActionType.STOP_MOVING);
			}
		}
		if (U.r()<0.03) {
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
		
		Projectile closestProjectile = null;
		float d = Float.MAX_VALUE;
		for(AbilityEffect p : bm.abilityEffects.values()) {
			if ((p instanceof Projectile || p instanceof PenetratingProjectile) && p.alive) {
				float curD = (cr.mx()-p.px)*(cr.mx()-p.px)+(cr.my()-p.py)*(cr.my()-p.py);
				if(curD < d) {
					d = curD;
					closestProjectile = (Projectile) p;
				}
			}
		}
		
		//Avoid Projectiles
		if(U.r()<0.4) {
			if (closestProjectile!=null) {
				dx = cr.mx()-closestProjectile.px;
				dy = cr.my()-closestProjectile.py;
				adx = Math.abs(dx);
				ady = Math.abs(dy);
				if (adx<200 && ady<20) {
					if ((closestProjectile.vx<0 && dx<0) || (closestProjectile.vx>0 && dx>0)) {
						if (cr.type.canJump()) {
							act(ActionType.JUMP);
						}
					}
				}
			}
		}
	}
}
