package item.component;

import battle.BattlePlayer;

/** Increases the basic attack damage by the given amount. Applies before damage multipliers. */
public class IncreasedBasicAttackDamage implements ItemComponent {
	private int amount;
	
	public IncreasedBasicAttackDamage(int amount) {
		this.amount = amount;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.basicAttackDamage += amount;
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Increase base attack damage";
	}
}
