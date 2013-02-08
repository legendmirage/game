package item.component;

import battle.BattlePlayer;

/** Gives player the ability to air jump the given number of times before landing. */
public class MoreAirJumps implements ItemComponent {
	private int amount;
	
	public MoreAirJumps(int amount) {
		this.amount = amount;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.airJumpCounter = Math.max(pl.airJumpCounter, amount);
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Increase max jump count";
	}
}

