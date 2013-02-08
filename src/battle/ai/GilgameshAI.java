package battle.ai;

import client.ActionType;
import util.U;
import model.GameModel;
import ability.effect.AbilityEffect;
import ability.effect.Projectile;
import battle.BattleCreature;

public class GilgameshAI extends AI {
	private boolean avoiding;
	public GilgameshAI(BattleCreature cr) {
		super(cr);
		this.avoiding = false;
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
		if (U.r()<0.05  && !avoiding) {
			if (adx>cr.getWidth()*3) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (adx<cr.getWidth()*2) {
				if (dx<0) {
					act(ActionType.MOVE_RIGHT);
				} else {
					act(ActionType.MOVE_LEFT);
				}
			}
		}
		if (U.r()<0.05 && !avoiding) {
			if (ady>cr.getHeight()*3) {
				if (dy<0) {
					act(ActionType.MOVE_UP);
				} else {
					act(ActionType.MOVE_DOWN);
				}
			} else if (ady<cr.getHeight()*2) {
				act(ActionType.MOVE_UP);
			} else {
				act(ActionType.STOP_FLYING);
			}
		}
		
		Projectile closestProjectile = null;
		float d = Float.MAX_VALUE;
		for(AbilityEffect p : bm.abilityEffects.values()) {
			if (p instanceof Projectile && p.alive) {
				BattleCreature owner = bm.creatures.get(p.creatureID);
				if (owner.sameTeam(cr)) continue;
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
				if (adx<150 && ady<cr.getHeight()) {
					if ((closestProjectile.vx<0 && dx<0) || (closestProjectile.vx>0 && dx>0)) {
						if (cr.type.canJump()) {
							act(ActionType.MOVE_UP);
							avoiding = true;
						}
					}
				} else if (ady>cr.getHeight() && ady<cr.getHeight()*2 && adx<100 && dy>0) {
					if (closestProjectile.px<cr.mx()) {
						act(ActionType.MOVE_RIGHT);
					} else {
						act(ActionType.MOVE_LEFT);
					}
				}
			} else {
				avoiding = false;
			}
		}
		
		// Cast ProjectileReloaded
		if (U.r()<0.003) {
			if (dist<800) {
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
				act(ActionType.STOP_MOVING);
				return;
			}
		}
		
		// Cast Grapple Hook
		if (U.r()<0.005) {
			if (dist<1000 && dist>400) {
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
