package battle.ai;

import model.GameModel;
import util.U;
import ability.Ability;
import ability.type.KnockbackNearbyEnemies;
import ability.type.ShootGuidedProjectile;
import battle.BattleCreature;
import client.ActionType;

public class OgreAI extends AI {
	
	boolean firstSpeed;
	public OgreAI(BattleCreature cr) {
		super(cr);
		firstSpeed = true;
	}

	@Override
	public void run() {
		if (firstSpeed){
			cr.moveSpeedMultiplier = (float) (U.r()*0.4+ 0.6);
			firstSpeed = false;
		}

		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		
		float dx = cr.mx()-closestPlayer.mx();
		float dy = cr.my()-closestPlayer.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		if(U.r()<0.05) {
			//Move towards the player
			if (adx>300) {
				if (dx>0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (adx<150) {
				act(ActionType.STOP_MOVING);
			}
		}
		
		// Cast Melee
		if (U.r()<0.01) {
			if (adx<150) {
				act(ActionType.ABILITY, "0");
				return;
			}
		}
		
		// Cast Shockwaves
		if (U.r()<0.01) {
			if (adx<350 && adx>150) {
				act(ActionType.ABILITY, "1");
				return;
			}
		}
		
		//Firing missiles
		if (U.r()<0.01) {
			if (adx<800 && adx>100) {
				act(ActionType.ABILITY, "2");
				return;
			}
		}
	}

}
