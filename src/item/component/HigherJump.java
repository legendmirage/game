package item.component;

import battle.BattlePlayer;

/** Increases the player's jump speed by the given amount. */
public class HigherJump implements ItemComponent {
	private float amount;
	
	public HigherJump(float amount) {
		this.amount = amount;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.jumpSpeed += amount;
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Increase jump height";
	}
}
