package item.component;

import battle.BattleModel;
import battle.BattlePlayer;

/** Gives every damage to team 0 a chance of doing triple damage. */
public class CritChance implements ItemComponent {
	private float chance;
	private float multiplier;
	
	public CritChance(float chance, float multiplier) {
		this.chance = chance;
		this.multiplier = multiplier;
	}

	@Override
	public void init(BattlePlayer pl) {
		BattleModel.MAIN.playerCritChance = Math.max(BattleModel.MAIN.playerCritChance, chance);
		BattleModel.MAIN.playerCritMultiplier = Math.max(BattleModel.MAIN.playerCritMultiplier, multiplier);
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Critical attack";
	}
}
