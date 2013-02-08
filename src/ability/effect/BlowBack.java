package ability.effect;

import model.GameModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import battle.BattleModel;

/**
 * A class handling the rendering of the Blow ability
 */
public class BlowBack extends AbilityEffect {
	/** The duration of the impulse due to blow */
	private int duration;

	/**
	 * @param creatureID - The ID of the creature applying this effect
	 * @param px - The x coordinate of the creature
	 * @param py - The y coordinate of the creature
	 * @param duration - The duration of the ability
	 * @param gm
	 */
	public BlowBack(int creatureID, float px, float py, int duration) {
		super(creatureID, px, py);
		this.duration = duration;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(BattleModel bm) {
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}

}
