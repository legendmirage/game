package battle.ai;

import org.yaml.snakeyaml.Yaml;
import client.ActionType;
import util.U;
import model.GameModel;
import battle.BattleConstants;
import battle.BattleCreature;
import battle.BattleTileType;

public class MakerAI extends AI {
	private static int XCounter = 200;
	private static int YCounter = 200;
	private boolean random;
	private int modeCounter;
	private int randomXCounter;
	private int randomYCounter;
	private ActionType currentX;
	private ActionType currentY;
	private boolean makerTwo;
	private boolean laserBeam;
	private int laserBeamCounter;
	public MakerAI(BattleCreature cr) {
		super(cr);
		this.random = true;
		this.laserBeamCounter = 0;
		this.laserBeam = false;
		this.currentX = ActionType.MOVE_LEFT;
		this.currentY = ActionType.MOVE_UP;
		this.randomXCounter = XCounter;
		this.randomYCounter = YCounter;
		this.modeCounter = 2000;
		this.makerTwo = false;
	}

	@Override
	public void run() {
		modeCounter--;
		if (modeCounter==0) {
			random = !random;
			modeCounter = 2000;
		}
		if (cr.getScreenX()<0) {
			cr.setScreenX(10);
		} else if (cr.getScreenX()+cr.getWidth()>bm.map.getWidth()*BattleConstants.TILE_WIDTH) {
			cr.setScreenX(bm.map.getWidth()*BattleConstants.TILE_WIDTH-cr.getWidth()-10);
		}
		
		if (random) {
			randomMode();
		} else {
			notRandom();
		}
	}
	
	private void randomMode() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		float dist = (float) this.dist(closestPlayer.mx(), closestPlayer.my());
		if (makerTwo) {
			makerTwo = false;
			laserBeamCounter = 0;
			laserBeam = false;
			currentX = ActionType.MOVE_LEFT;
			currentY = ActionType.MOVE_UP;
			randomXCounter = XCounter;
			randomYCounter = YCounter;
		}
		if (U.r()<0.05) {
			randomXCounter--;
			randomYCounter--;
			
			if (cr.getScreenX()<50) {
				currentX = ActionType.MOVE_RIGHT;
				randomXCounter = 500;
			} else if (cr.getScreenX()>bm.map.getWidth()*BattleConstants.TILE_WIDTH-cr.getWidth()*1.5) {
				currentX = ActionType.MOVE_LEFT;
				randomXCounter = 500;
			} else if (randomXCounter==0) {
				if (currentX==ActionType.MOVE_LEFT) {
					currentX = ActionType.MOVE_RIGHT;
				} else {
					currentX = ActionType.MOVE_LEFT;
				}
				randomXCounter = XCounter;
			}
			
			if (cr.getScreenY()<100) {
				currentY = ActionType.MOVE_DOWN;
				randomYCounter = 500;
			} else if (bm.map.getTileAtCoord(cr.getScreenX(), cr.getScreenY()+cr.getHeight()+15) == BattleTileType.WALL) {
				currentY = ActionType.MOVE_UP;
				randomYCounter = 500;
			} else if (randomYCounter==0) {
				if (currentY==ActionType.MOVE_UP) {
					currentY = ActionType.MOVE_DOWN;
				} else {
					currentY = ActionType.MOVE_UP;
				}
			}
			act(currentX);
			act(currentY);
			//Cast Projectile 360 reloaded
			if (U.r()<0.3) {
				act(ActionType.ABILITY, "0");
				return;
			}
			
			// Cast Chain Lightning
			if (U.r()<0.3) {
				if (dist<700) {
					act(ActionType.ABILITY, "1");
					return;
				}
			}
			// Cast GrappleHook
			if (U.r()<0.3) {
				if (dist<700) {
					act(ActionType.ABILITY, "2");
					return;
				}
			}
		}
	}
	
	private void notRandom() {
		BattleCreature closestPlayer = bm.creatures.get(GameModel.MAIN.player.id);
		if(closestPlayer==null) return;
		float dx = closestPlayer.mx()-cr.mx();
		float dy = closestPlayer.my()-cr.my();
		float adx = Math.abs(dx);
		float ady = Math.abs(dy);
		float dist = (float) this.dist(closestPlayer.mx(), closestPlayer.my());
		if (!makerTwo) {
			makerTwo = true;
		}
		if (laserBeam) {
			laserBeamCounter--;
			if (laserBeamCounter==0) {
				laserBeam=false;
			}
		}
		if (U.r()<0.05) {
			if (laserBeam) {
				if (adx>100) {
					if (dx<0) {
						act(ActionType.MOVE_LEFT);
					} else {
						act(ActionType.MOVE_RIGHT);
					}
				} else {
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
				}
			} else if (adx>220) {
				if (dx<0) {
					act(ActionType.MOVE_LEFT);
				} else {
					act(ActionType.MOVE_RIGHT);
				}
			} else if (adx<200) {
				if (dx<0) {
					act(ActionType.MOVE_RIGHT);
				} else {
					act(ActionType.MOVE_LEFT);
				}
			}
			if (ady>cr.getHeight()*2.5) {
				if (dy<0) {
					act(ActionType.MOVE_UP);
				} else {
					act(ActionType.MOVE_DOWN);
				}
			} else if (laserBeam) {
				if (ady>cr.getHeight()*1.5) {
					if (dy<0) {
						act(ActionType.MOVE_UP);
					} else {
						act(ActionType.MOVE_DOWN);
					}
				} else {
					act(ActionType.STOP_FLYING);
				}
			} else if (ady<cr.getHeight()) {
				act(ActionType.MOVE_UP);
			}
		}
		
		//Cast Giant Laser
		if (U.r()<0.004) {
			float oldpx = cr.getScreenX();
			if (dx<0) {
				cr.facingRight = false;
			} else {
				cr.facingRight = true;
			}
			if (bm.map.collideRectWall(cr)) {
				cr.setScreenX(oldpx);
			}
			act(ActionType.ABILITY, "3");
			laserBeam = true;
			laserBeamCounter = 220;
			return;
		}
		
		// Cast Fire Storm
		if (U.r()<0.01) {
			if (dist<600) {
				float oldpx = cr.getScreenX();
				if (dx<0) {
					cr.facingRight = false;
				} else {
					cr.facingRight = true;
				}
				if (bm.map.collideRectWall(cr)) {
					cr.setScreenX(oldpx);
				}
				act(ActionType.ABILITY, "4");
			}
		}
		
	}
	
	private double dist(double x1, double y1) {
		double dist = 0;
		dist = Math.sqrt((cr.mx()-x1)*(cr.mx()-x1)+(cr.my()-y1)*(cr.my()-y1));
		return dist;
	}
}
