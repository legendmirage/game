package item.component;

import battle.BattlePlayer;

/** Fall stun to the player is multiplied by the given number, 
 * and the velocity threshold for which fall stun is applied is raised by the given amount. */
public class ReducedFallDamage implements ItemComponent {
	private int thresholdIncrease;
	private float multiplier;
	
	public ReducedFallDamage(int thresholdIncrease, float multiplier) {
		this.thresholdIncrease = thresholdIncrease;
		this.multiplier = multiplier;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.fallStunMultiplier *= multiplier;
		pl.fallStunThreshold += thresholdIncrease;
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Reduce fall damage";
	}
}
