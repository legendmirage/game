package item.component;

import battle.BattlePlayer;

/** Reduces pre-multiplier damage by the given amount. */
public class TakeLessDamage implements ItemComponent {
	private int amount;
	
	public TakeLessDamage(int amount) {
		this.amount = amount;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.damageReduction += amount;
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Decrease damage taken";
	}
}
