package ability.effect;

import model.GameModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import battle.BattleCreature;
import battle.BattleModel;

/** Ability component which makes the creature invincible for certain amount of time */
public class Invincibility extends AbilityEffect {
	/** The duration of the shield */
	private int duration;
	private boolean firstUpdated;
	
	/**
	 * 
	 * @param creatureID - ID of the creature casting this ability 
	 * @param duration - duration of the ability
	 */
	public Invincibility(int creatureID, int duration) {
		super(creatureID);
		this.duration = duration;
		this.firstUpdated = false;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		return;
	}

	@Override
	public void update(BattleModel bm) {
		BattleCreature owner = bm.creatures.get(creatureID);
		if (!firstUpdated) {
			owner.shield = true;
			firstUpdated = true;
		}
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			owner.shield = false;
			alive = false;
		}
	}

}
