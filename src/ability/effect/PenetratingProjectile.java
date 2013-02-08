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

/**
 * A fire storm which penetrates everything except walls
 */
public class PenetratingProjectile extends AbilityEffect {
	public float vx, vy;
	/** The damage dealt by the storm per second*/
	private int dps;
	/** The duration of the storm */
	private int duration;
	private float radius;
	
	/**
	 * @param range - The  range of the storm
	 * @param damage - The damage dealt by the storm per second
	 * @param speed - The speed of the storm
	 */
	public PenetratingProjectile(int creatureID, int duration, int dps, 
			float px, float py, float vx, float vy, float radius) {
		super(creatureID, px, py);
		
		this.vx = vx;
		this.vy = vy;
		
		this.dps = dps;
		this.duration = duration;
		
		this.radius = radius;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(new Color(0x55FF0000));
		g.fillOval(px-radius, py-radius, 2*radius, 2*radius);
	}

	@Override
	public void update(BattleModel bm) {
		float dt = GameConstants.GAME_SPEED/1000.0f;
		px += vx * dt;
		py += vy * dt;
		
		// Check each creature to see if it has collided with this fire storm
		BattleCreature owner = bm.creatures.get(creatureID);
		for(Pair<Integer, Float> p: bm.collideCircle(px, py, radius)) {
			BattleCreature cr = bm.creatures.get(p.val1);
			if (owner.sameTeam(cr)) continue;
			if (p.getVal2()<=0) continue;
			int a = dps/100;
			int b = dps%100;
			if(U.r()<0.01*b) cr.takeDamage(a+1);
			else cr.takeDamage(a);
			return;
		}
		
		// Check if the fire storm has gone out of range
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}

}
