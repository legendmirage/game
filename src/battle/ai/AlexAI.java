package battle.ai;

import model.GameModel;
import util.U;
import client.ActionType;
import ability.effect.AbilityEffect;
import ability.effect.Projectile;
import battle.BattleCreature;

public class AlexAI extends AI {
	int time;
	public AlexAI(BattleCreature cr) {
		super(cr);
		time = 0;
	}

	@Override
	public void run() {
		if(cr.spellChannel!=null){
			act(ActionType.STOP_MOVING);
			
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
				if(ady < 30){
				act(ActionType.ABILITY, "1");
				act(ActionType.STOP_MOVING);
				}
			}
		}
		
		//cast projectile
		if (U.r()<0.03) {
			if (adx<150) {
				act(ActionType.ABILITY, "0");
				return;
			}
		}
		
		
		if (U.r()<0.01) {
			if (adx>50 && Math.abs(closestPlayer.mx() -cr.mx()) < 30) {
				act(ActionType.ABILITY, "1");
				return;
			}
		}
		
		
		
		
		time+=1;
		
	}


}
