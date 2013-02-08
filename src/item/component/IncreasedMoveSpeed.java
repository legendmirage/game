package item.component;

import battle.BattlePlayer;

/** Applies a multiplier to the player's battle movement speed. */
public class IncreasedMoveSpeed implements ItemComponent {
	private float multiplier;
	
	public IncreasedMoveSpeed(float multiplier) {
		this.multiplier = multiplier;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.moveSpeedMultiplier *= multiplier;
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Increase move speed of player";
	}
}
