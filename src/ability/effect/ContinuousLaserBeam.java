package ability.effect;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.Pair;
import util.U;

import battle.BattleConstants;
import battle.BattleCreature;
import battle.BattleModel;

public class ContinuousLaserBeam extends AbilityEffect {
	private int duration;
	private int dps;
	private int height;
	private boolean firstUpdated;
	private int width;
	public ContinuousLaserBeam(int creatureID, float px, float py, int duration, int dps, int height) {
		super(creatureID, px, py-height/2);
		this.duration = duration;
		this.dps = dps;
		this.height = height;
		this.firstUpdated = false;
		this.width = 0;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if (!firstUpdated) return;
		g.setColor(new Color(0xAAFF0000));
		g.fillRect(px, py, width, height);
	}

	@Override
	public void update(BattleModel bm) {
		if (!firstUpdated) {
			firstUpdated = true;
		}
		BattleCreature owner = bm.creatures.get(creatureID);
		px = owner.getScreenX()+(owner.facingRight?owner.getWidth()+1:-1);
		py = owner.getScreenY()+owner.getHeight()/2-height/2;
		if (owner.facingRight) {
			width = bm.map.getWidth()*BattleConstants.TILE_WIDTH;
		} else {
			width = -bm.map.getWidth()*BattleConstants.TILE_WIDTH;
		}
		for(Pair<Integer, Float> p: bm.collideRectangle(px, py, width, height)) { 
			BattleCreature cr = bm.creatures.get(p.getVal1());
			if (owner.sameTeam(cr)) continue;
			if (p.getVal2()<=0) continue;
			int a = dps/100;
			int b = dps%100;
			if(U.r()<0.01*b) cr.takeDamage(a+1);
			else cr.takeDamage(a);
		}
		// Check if the projectile has gone out of range
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}

}
