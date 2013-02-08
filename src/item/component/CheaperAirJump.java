package item.component;

import battle.BattlePlayer;

/** Makes the air jump cost the given amount of AP. */
public class CheaperAirJump  implements ItemComponent {
	private int apCost;
	
	public CheaperAirJump(int apCost) {
		this.apCost = apCost;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.airJumpCost = Math.min(pl.airJumpCost, apCost);
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Reduce AP cost of jump";
	}
}