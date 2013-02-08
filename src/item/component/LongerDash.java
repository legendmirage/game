package item.component;

import battle.BattlePlayer;

/** Makes the dash last the given amount of ticks. */
public class LongerDash implements ItemComponent {
	private int duration;
	
	public LongerDash(int duration) {
		this.duration = duration;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.dashDuration = Math.max(pl.dashDuration, duration);
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Increase length of dash";
	}
}
