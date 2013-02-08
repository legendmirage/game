package ability.effect;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.GameConstants;
import util.Pair;
import util.U;

import battle.BattleCreature;
import battle.BattleModel;
import battle.BattleTileType;

public class LaserBeam extends AbilityEffect {
	private int duration;
	private int dps;
	private int size;
	private boolean firstUpdated;
	private float vx,vy;

	public LaserBeam(int creatureID, float px, float py, float vx, float vy, int duration, int dps, int size) {
		super(creatureID, px, py);
		this.dps = dps;
		this.duration = duration;
		this.size = size;
		this.firstUpdated = false;
		this.vx = vx;
		this.vy = vy;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if (!firstUpdated) return;
		g.setColor(Color.red);
		g.fillRect(px, py, size, 5);
	}

	@Override
	public void update(BattleModel bm) {
		if (!firstUpdated) {
			firstUpdated = true;
		}
		BattleCreature owner = bm.creatures.get(creatureID);
		float dt = GameConstants.GAME_SPEED/1000.0f;
		px += vx * dt;
		py += vy * dt;
		for(Pair<Integer, Float> p: bm.collideLine(px, py, px+size, py)) { 
			BattleCreature cr = bm.creatures.get(p.getVal1());
			if (owner.sameTeam(cr)) continue;
			if (p.getVal2()<=0) continue;
			int a = dps/100;
			int b = dps%100;
			if(U.r()<0.01*b) cr.takeDamage(a+1);
			else cr.takeDamage(a);
		}
		
		// Check if the beam collided with a wall
		if(bm.map.getTileAtCoord(px, py) == BattleTileType.WALL || bm.map.getTileAtCoord(px+size, py)==BattleTileType.WALL) {
			alive = false;
			return;
		}
				
		// Check if the projectile has gone out of range
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}

}
