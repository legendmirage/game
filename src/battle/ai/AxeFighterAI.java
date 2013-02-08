package battle.ai;

import model.GameModel;
import util.U;
import ability.effect.AbilityEffect;
import ability.effect.Projectile;
import battle.BattleCreature;
import client.ActionType;

public class AxeFighterAI extends AI {
	
	private boolean casting;
	boolean firstSpeed;
	public AxeFighterAI(BattleCreature cr) {
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
			if (adx>cr.getWidth() && !casting) {
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
					if (dx<0) {
						cr.facingRight = false;
					} else {
						cr.facingRight = true;
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
}
