package item.component;

import battle.BattlePlayer;

/** Increases the player's max AP by the given amount. Increases AP regen proportionally. */
public class IncreasedAP implements ItemComponent {
	private int amount;
	
	public IncreasedAP(int amount) {
		this.amount = amount;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.maxAP += amount;
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Increase max AP";
	}
}
