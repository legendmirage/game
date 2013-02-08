package ability.effect;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import battle.BattleModel;

/** This effect just draws an expanding circle on the screen. It does not do anything. */
public class ExpandingCircle extends AbilityEffect {
	
	public int duration;
	public float maxRadius;
	
	public ExpandingCircle(int creatureID, float px, float py, int duration, float maxRadius) {
		super(creatureID, px, py);
		this.duration = duration;
		this.maxRadius = maxRadius;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		float r = maxRadius * (GameModel.MAIN.tickNum - tickCreated) / duration;
		g.setColor(Color.yellow);
		g.fillOval(px-r, py-r, 2*r, 2*r);
	}

	@Override
	public void update(BattleModel bm) {
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}

}
