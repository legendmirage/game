package ability.effect;

import model.GameModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import battle.BattleCreature;
import battle.BattleModel;

/**
 * Slows down close by enemies by the given multiplier
 */
public class Slow extends AbilityEffect {
	/** The duration for which the ability applies */
	private int duration;
	/** Move speed multiplier, every enemy in range is slowed by this amount */
	private float multiplier;
	
	private boolean applied;

	public Slow(int creatureID, int duration, float multiplier) {
		super(creatureID);
		this.duration = duration;
		this.multiplier = multiplier;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
	}

	@Override
	public void update(BattleModel bm) {
		if(!applied) {
			applied = true;
			BattleCreature cr = bm.creatures.get(creatureID);
			cr.slowEffectMultiplier *= multiplier;
		}
		
		// Check if the duration is up
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			if(applied) {
				BattleCreature cr = bm.creatures.get(creatureID);
				cr.slowEffectMultiplier /= multiplier;
			}
			alive = false;
			return;
		}
	}

}
