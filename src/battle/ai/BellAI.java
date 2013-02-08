package battle.ai;

import client.ActionType;
import util.U;
import model.GameModel;
import ability.effect.AbilityEffect;
import ability.effect.Projectile;
import battle.BattleCreature;

public class BellAI extends AI {

	public BellAI(BattleCreature cr) {
		super(cr);
	}

	@Override
	public void run() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		
		if (U.r()<0.05) {
			if (adx>400 && ady<100) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (adx>200 && ady<100) {
				act(ActionType.JUMP);
			} else {
				if (dx<0) {
					act(ActionType.MOVE_RIGHT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
				act(ActionType.JUMP);
			}
		}
		
		// Cast Projectile
		if (U.r()<0.05) {
			if (ady<100 && adx<700) {
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
		}
		
		Projectile closestProjectile = null;
		float d = Float.MAX_VALUE;
		for(AbilityEffect p : bm.abilityEffects.values()) {
			if (p instanceof Projectile && p.alive) {
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
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
