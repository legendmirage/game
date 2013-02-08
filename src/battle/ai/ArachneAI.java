package battle.ai;

import client.ActionType;
import util.U;
import model.GameModel;
import battle.BattleCreature;

public class ArachneAI extends AI {
	private boolean attackMode;
	private ActionType currentXDirection;
	private ActionType currentYDirection;
	private int startTime;
	
	public ArachneAI(BattleCreature cr) {
		super(cr);
		this.attackMode = true;
		this.currentXDirection = null;
		this.currentYDirection = null;
		this.startTime = 0;
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
		
		if (cr.hp<cr.maxHP/2) {
			attackMode = false;
			currentXDirection = null;
			currentYDirection = null;
			startTime = 0;
		}
		if (GameModel.MAIN.tickNum-startTime>60) {
			currentYDirection=null;
			currentXDirection=null;
		}
		if (attackMode) {
			if (U.r()<0.05) {
				if (dist>700) {
					currentXDirection = null;
					currentYDirection = null;
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
					if (dy<0) {
						act(ActionType.MOVE_UP);
					} else {
						act(ActionType.MOVE_DOWN);
					}
				} else if (dist>250) {
					currentXDirection = null;
					currentYDirection = null;
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
					if (ady<cr.getHeight()*1.5) {
						act(ActionType.MOVE_UP);
					} else if (ady>cr.getHeight()*2) {
						act(ActionType.MOVE_DOWN);
					}
				}  else {
					if (U.r()<0.5 && currentXDirection==null) {
						currentXDirection = ActionType.MOVE_RIGHT;
						startTime = GameModel.MAIN.tickNum;
					} else if (currentXDirection==null) {
						currentXDirection = ActionType.MOVE_LEFT;
						startTime = GameModel.MAIN.tickNum;
					}
					act(currentXDirection);
					if (U.r()<0.5 && currentYDirection==null) {
						currentYDirection = ActionType.MOVE_DOWN;
						startTime = GameModel.MAIN.tickNum;
					} else if (currentYDirection==null){
						currentYDirection = ActionType.MOVE_UP;
						startTime = GameModel.MAIN.tickNum;
					}
					act(currentYDirection);
				}
			}
			// Cast Hailstorm
			if (U.r()<0.001) {
				if (adx<400) {
					act(ActionType.ABILITY, "1");
				}
			}
			
			// Cast Laser Beam
			if (U.r()<0.005) {
				if (ady<cr.getHeight()) {
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
		} else {
			if (U.r()<0.05) {
				if (adx<cr.getWidth()*1) {
					currentXDirection = null;
					if (dx<0) {
						act(ActionType.MOVE_RIGHT);
					} else {
						act(ActionType.MOVE_LEFT);
					}
				} else if (adx>cr.getWidth()*3) {
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
				} else {
					if (U.r()<0.5 && currentXDirection==null) {
						currentXDirection = ActionType.MOVE_RIGHT;
						startTime = GameModel.MAIN.tickNum;
					} else if (currentXDirection==null) {
						currentXDirection = ActionType.MOVE_LEFT;
						startTime = GameModel.MAIN.tickNum;
					}
					act(currentXDirection);
				}
				if (ady<cr.getHeight()*1) {
					currentYDirection = null;
					act(ActionType.MOVE_UP);
				} else if (ady>cr.getHeight()*2.2) {
					if (dy<0) {
						act(ActionType.MOVE_UP);
					} else {
						act(ActionType.MOVE_DOWN);
					}
				} else {
					if (U.r()<0.5 && currentYDirection==null) {
						currentYDirection = ActionType.MOVE_DOWN;
						startTime = GameModel.MAIN.tickNum;
					} else if (currentYDirection==null) {
						currentYDirection = ActionType.MOVE_UP;
						startTime = GameModel.MAIN.tickNum;
					}
					act(currentYDirection);
				}
			}
			// Projectile 360
			if (U.r()<0.005 || (U.r()<0.01 && adx<cr.getWidth()*1.3)) {
				act(ActionType.ABILITY, "2");
			}
			// Laser Beam
			if (U.r()<0.05 && ady<cr.getHeight()) {
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
	}
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
