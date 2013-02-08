package battle.ai;

import util.U;
import client.ActionType;
import model.GameModel;
import battle.BattleCreature;

public class RuwenAI extends AI {

	private boolean charge;
	private float lowProb;
	private float highProb;
	private boolean combat;
	private boolean avoid;
	private boolean defense;
	private boolean firstLevel;
	private boolean secondLevel;
	
	public RuwenAI(BattleCreature cr) {
		super(cr);
		charge = false;
		lowProb = (float) 0.0009;
		highProb = (float) 0.003;
		combat = false;
		avoid = true;
		defense = false;
		firstLevel = true;
		secondLevel = true;
		
	}

	@Override
	public void run() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;		
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		
		
		if (cr.hp < 0.1*cr.maxHP){
			avoid = false;
			combat = false;
			defense = true;
		} else if (cr.hp < 0.25*cr.maxHP) {
			if (U.r() < lowProb || secondLevel) {
				combat = true;
				avoid = false;
				defense = false;
				secondLevel = false;
			} else if (U.r()< highProb){
					avoid = false;
					combat = false;
					defense = true;
			}	
		} else if (cr.hp < 0.6*cr.maxHP){
			if (U.r() < highProb || firstLevel){
				combat = true;
				avoid = false;
				defense = false;
				firstLevel = false;
			} else if (U.r()< lowProb){
					avoid = false;
					combat = false;
					defense = true;
			}			
		}
		
		else if (cr.hp < 0.75*cr.maxHP){
			if (U.r() < lowProb){
				combat = true;
				avoid = false;
				defense = false;
			}
			else if (U.r()< highProb){
					avoid = true;
					combat = false;
					defense = false;
			}
			
		}
		
		if(defense){
			if (U.r()< lowProb*5){
				act(ActionType.ABILITY, "2");
				return; 
			}
			if(U.r()< highProb*2){

				act(ActionType.ABILITY, "0");
				
			}
			
			if(U.r()<0.02) {
				if (adx>250) {
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					
					}
				}
				if (ady>250) {
					if (dy<0) {
						act(ActionType.MOVE_UP);
					} else {
						act(ActionType.MOVE_DOWN);
					
					}
				}
				
			}

		if (adx<250){
			if (dx< 100 && dx > 0)
				{
					act(ActionType.MOVE_LEFT);
					
				}
			else if (dx > -100 && dx < 0)
			{		
					act(ActionType.MOVE_RIGHT);
					
			}
			else
				if (U.r()< 0.006){
					if (dx < 0){
						cr.facingRight = false;
					}
					else {
						cr.facingRight = true;
					}
				}
				act(ActionType.STOP_MOVING);
		}
		
		if (ady<250){
		
			if (ady<100)
				act(ActionType.MOVE_UP);
			else	
			{
				act(ActionType.STOP_FLYING);	
				}
		}
		}
		
		else if(avoid){
			
			
			if(U.r()<0.02) {
				if (dist(closestPlayer.mx(),closestPlayer.my())>250) {
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					
					}
				}
				if (ady>250) {
					if (dy<0) {
						act(ActionType.MOVE_UP);
					} else {
						act(ActionType.MOVE_DOWN);
					
					}
				}
				
			}

		if (dist(closestPlayer.mx(),closestPlayer.my())<250){
			if (dx< 150 && dx > 0)
				{
					act(ActionType.MOVE_LEFT);
					
				}
			else if (dx > -100 && dx < 0)
			{		
					act(ActionType.MOVE_RIGHT);
					
			}
			else{
				if (U.r()< 0.003){
					if (dx < 0){
						cr.facingRight = false;
					}
					else {
						cr.facingRight = true;
					}
					}
			}
			
			if(U.r()< highProb*2){

				act(ActionType.ABILITY, "0");
				
			}
		}
		
		if (ady<250){
		
			if (ady<100)
				act(ActionType.MOVE_UP);
			
		}
		}
		
		else if(combat){
			
			if (adx>150) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				
				}
			}
			
			if (U.r() < lowProb){
				charge = true;
				lowProb += 0.0001;
			}
			
			if(charge){
				//charge
				cr.moveSpeedMultiplier = (float) 5;
				if (dy < -10){
					act(ActionType.MOVE_UP);
						
				}
				else if (dy > 10){
					act(ActionType.MOVE_DOWN);
				}
				if (U.r() < highProb){
					charge = false;
					highProb -= 0.0001;
					
				}
				act(ActionType.ABILITY, "1");
			}
			
			
			
			else{
			
			if(U.r()<0.05) {

			cr.moveSpeedMultiplier = (float) 2;
				
				if (ady>150) {
					if (dy<0) {
						act(ActionType.MOVE_UP);
					} else {
						act(ActionType.MOVE_DOWN);
					
					}
				}
				
			}
			
			
			}
			act(ActionType.ABILITY, "0");
			return;
			
		}
		
		
	}
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}

}
