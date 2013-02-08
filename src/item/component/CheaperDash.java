package item.component;

import battle.BattlePlayer;

/** Makes the dash cost the given amount of AP. */
public class CheaperDash implements ItemComponent {
	private int apCost;
	
	public CheaperDash(int apCost) {
		this.apCost = apCost;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.dashCost = Math.min(pl.dashCost, apCost);
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Reduce AP cost of dash";
	}
}
