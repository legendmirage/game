package item.component;

import battle.BattlePlayer;

/** Reduces knockback/stun time by the given duration. */
public class StunResistance implements ItemComponent {
	private int amount;
	
	public StunResistance(int amount) {
		this.amount = amount;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.stunReduction += amount;
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Increase stun resistance";
	}
}
