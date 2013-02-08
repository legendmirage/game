package battle.ai;

import model.GameModel;
import client.ActionType;
import util.Logger;
import util.U;
import battle.BattleCreature;

public class KaivanAI extends AI {
	private int mode;
	private boolean beamOn;
	private boolean up;
	private int beamCounter;
	private int modeCounter;

	public KaivanAI(BattleCreature cr) {
		super(cr);
		this.modeCounter = 1000;
		this.beamCounter = 0;
		this.up = false;
		this.mode = 0;
		this.beamOn = false;
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
		modeCounter--;
		if (modeCounter==0) {
			beamCounter=0;
			up = false;
			beamOn = false;
			modeCounter = 1000;
			mode = (mode==0)?1:0;
		}
		if (beamOn) {
			beamCounter--;
			if (beamCounter==0) {
				beamOn = false;
			}
		}
		
		if (mode==0) {
			if (U.r()<0.05) {
				if (adx>100 && !up && !beamOn) {
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
				} else if (adx<100) {
					act(ActionType.STOP_MOVING);
				}
				if ((beamOn && ady>cr.getHeight()) || ady>cr.getHeight()) {
					if (dy<0) {
						act(ActionType.JUMP);
					} else {
						act(ActionType.MOVE_RIGHT);
						up = true;
					}
				} else {
					up = false;
				}
			}
			
			// Cast Giant Laser Beam
			if (U.r()<0.005) {
				if (ady<300) {
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
					beamOn = true;
					beamCounter = 100;
					return;
				}
			}
			
			// Cast chain lightning
			if (U.r()<0.005) {
				if (dist<300) {
					act(ActionType.ABILITY, "1");
					return;
				}
			}
		} else if (mode==1) {
			if (U.r()<0.05) {
				if (adx>150 && !up) {
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
				} else if (adx<100 && !up) {
					if (dx<0) {
						act(ActionType.MOVE_RIGHT);
					} else {
						act(ActionType.MOVE_LEFT);
					}
				}
				if (ady>cr.getHeight()) {
					if (dy<0) {
						act(ActionType.JUMP);
					} else {
						act(ActionType.MOVE_RIGHT);
						up = true;
					}
				} else {
					up = false;
				}
			}
			
			// Cast Projectile Reloaded
			if (U.r()<0.005) {
				if (dist<700) {
					float oldpx = cr.getScreenX();
					if (dx<0) {
						cr.facingRight = false;
					} else {
						cr.facingRight = true;
					}
					if (bm.map.collideRectWall(cr)) {
						cr.setScreenX(oldpx);
					}
					act(ActionType.ABILITY, "2");
				}
			}
			
			//Cast Chain Lightning
			if (U.r()<0.005) {
				if (dist<300) {
					float oldpx = cr.getScreenX();
					if (dx<0) {
						cr.facingRight = false;
					} else {
						cr.facingRight = true;
					}
					if (bm.map.collideRectWall(cr)) {
						cr.setScreenX(oldpx);
					}
					act(ActionType.ABILITY, "1");
				}
			}
		} else {
			Logger.err("Unattainable mode");
		}
	}
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}

}
