package battle.ai;

import client.ActionType;
import util.U;
import model.GameModel;
import ability.effect.AbilityEffect;
import ability.effect.Projectile;
import battle.BattleCreature;

public class LucasAI extends AI {
	private boolean up;
	private boolean avoiding;
	public LucasAI(BattleCreature cr) {
		super(cr);
		this.up = false;
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
		if (U.r()<0.05) {
			if (!up && adx>cr.getWidth()*4) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (ady>cr.getHeight()) {
				if (dy<0) {
					act(ActionType.JUMP);
				} else if (!avoiding){
					act(ActionType.MOVE_LEFT);
					up = true;
				}
			} else {
				up = false;
				act(ActionType.STOP_MOVING);
			}
		}

		//Cast Blow
		if (U.r()<0.01) {
			if (ady<cr.getHeight() && adx>400) {
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
				return;
			}
		}
		//Cast Whirlwind
		if (U.r()<0.01) {
			if (ady<cr.getHeight() && adx<250) {
				float oldpx = cr.getScreenX();
				if (dx>0) {
					cr.facingRight = true;
				} else {
					cr.facingRight = false;
				}
				if (bm.map.collideRectWall(cr)) {
					cr.setScreenX(oldpx);
				}
				act(ActionType.ABILITY, "1");
				return;
			}
		}
		//Cast Seeking Wind
		if (U.r()<0.005) {
			act(ActionType.ABILITY, "2");
			return;
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
							act(ActionType.JUMP);
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
				avoiding=false;
			}
		}
	}
}
