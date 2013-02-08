package item.component;

import battle.BattlePlayer;


/** A class of benefits that an item can provide when used passively. */
public interface ItemComponent {
	/** This is called once in the beginning of the battle. Apply buffs to player here. */
	public void init(BattlePlayer pl);
	/** This is called every turn, so the item component can do periodic effects like regen and damage auras. */
	public void update(BattlePlayer pl);
	/** This is called by displaying objects (such as menu), so you know what the item does */
	public String getEffectName();
}
