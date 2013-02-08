package battle.ai;

import model.GameModel;
import util.U;
import ability.Ability;
import ability.type.FireStorm;
import ability.type.RisingLava;
import ability.type.ShootGuidedProjectile;
import battle.BattleCreature;
import client.ActionType;

public class InfernoAI extends AI {
	
	public InfernoAI(BattleCreature cr) {
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
		
		
		boolean optDist = true;
		
		if(U.r()<0.02) {
			if (adx>250) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				
				}
			}
			
			if (ady>170) {
				if (dy < 0 + 150){
					act(ActionType.MOVE_UP);
					if (dx<0) {
						cr.facingRight = false;
				}
					else
						cr.facingRight = true;
					}
				else{
					act(ActionType.MOVE_DOWN);	
					if (dx<0) {
						cr.facingRight = false;
						}
					else
						cr.facingRight = true;
			
				}
			}
		}
			
		
		
		
		if (dist(closestPlayer.mx(),closestPlayer.my())<150){
			optDist = false;
		}
		
		if(closestPlayer.my()> 270){
			
			optDist = false;
			
		}

		if (dist(closestPlayer.mx(),closestPlayer.my())<100|| cr.my()> 480){
	
			act(ActionType.MOVE_UP);
		}
		
		
		
		if (optDist) {

			for(int i=0; i<cr.type.abilities.size(); i++) {
				Ability atype = cr.type.abilities.get(i);
				if (atype.type instanceof ShootGuidedProjectile) {
					act(ActionType.ABILITY, ""+i);
				}
			}
				act(circleX(closestPlayer.mx(),closestPlayer.my(), 290));
				act(circleY(closestPlayer.mx(),closestPlayer.my(), 290));
				return;
				}
		
		
		if (dist(closestPlayer.mx(),closestPlayer.my())>150){
		
			optDist = true;
		
		}
		
		
		
		if (U.r()<0.005) {
			for(int i=0; i<cr.type.abilities.size(); i++) {
				Ability atype = cr.type.abilities.get(i);
				if (atype.type instanceof RisingLava) {
					act(ActionType.ABILITY, ""+i);
				}
			}
		return;
		}
		if (U.r()<0.03) {
			
			if (U.r()<0.5 && ady < 60) {
			for(int i=0; i<cr.type.abilities.size(); i++) {
				Ability atype = cr.type.abilities.get(i);
				if (atype.type instanceof FireStorm) {

					act(ActionType.ABILITY, ""+i);
				}
			}
			

			}
			if (dist(closestPlayer.mx(),closestPlayer.my())<450) {
				
				act(ActionType.STOP_MOVING);
				act(ActionType.STOP_FLYING);
			}
			
		}
		
		if(dx < -20){
			cr.facingRight = false;
			
		}
		else if(dx > 20)
			cr.facingRight = true;
		
		
			
	}
	
	private ActionType circleX(double x1, double y1, double radius) {
		double distance = dist(x1,y1);
		//too close; go father
		if(distance < 50){
					if (x1 - cr.mx() < 0){

						if(y1-cr.my()< -10){
							return ActionType.STOP_MOVING;			
						}
				
						return ActionType.MOVE_LEFT;
					}
					else{
						
						if(y1-cr.my()< -1){

						return ActionType.MOVE_RIGHT;
						}
						return ActionType.STOP_MOVING;
					}
				}
		
		//too far; come closer
		else if(distance > radius){
			if (x1 - cr.mx() < 0){
				if(y1-cr.my()< -15){
					return ActionType.MOVE_RIGHT;
				}
				else
					return ActionType.MOVE_LEFT;
			}
			else{
				if(y1-cr.my()< -10){
					
				return ActionType.MOVE_RIGHT;
				}
				else {
					return ActionType.STOP_MOVING;
				}
			}
			
		}
		
		//circle
		else{
			if(y1-cr.my()< -10){
				return ActionType.MOVE_RIGHT;
			}
			else{
				return ActionType.MOVE_LEFT;
			}
		}
	}
	private ActionType circleY(double x1, double y1, double radius) {
		double distance = dist(x1,y1);
		//too close; go farther
		if(distance < 50){;
					if (y1 - cr.my() < 0){
						
						if(x1-cr.mx()< -10){
							return ActionType.STOP_FLYING;
							
						}
							return ActionType.MOVE_DOWN;
					}
					else{
						if(x1-cr.mx()< -10){
						return ActionType.MOVE_UP;
					}

						return ActionType.STOP_FLYING;
					}
					
				}
		//too far; come closer
		else if(distance > radius){
			if (y1 - cr.my() < 0){
				if(x1-cr.mx()< -10){
				return ActionType.MOVE_UP;
				}
				else
					if (Math.abs(x1 - cr.my()) < distance)
						return ActionType.STOP_FLYING;
					else
						return ActionType.MOVE_DOWN;
			}
			else{
				if(x1-cr.mx()< -10){
					if (x1 - cr.mx() < distance)
						return ActionType.STOP_FLYING;
					else
						return ActionType.MOVE_UP;
				}
				else
					return ActionType.MOVE_DOWN;
			}
		}
		
		//circle
		else{
			if(x1-cr.mx()< -10){
				return ActionType.MOVE_UP;
			}
			else{
				return ActionType.MOVE_DOWN;
			}
			
		}
		
		
	}
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
