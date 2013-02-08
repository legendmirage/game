package item.component;

import battle.BattlePlayer;

/** Makes all damage to enemies amplified by the given multiplier. */
public class IncreasedPower implements ItemComponent {
	private float multiplier;
	
	public IncreasedPower(float multiplier) {
		this.multiplier = multiplier;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.damageMultiplier *= multiplier;
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Increase damage multiplier";
	}
}
