package battle;

import ability.Ability;


/** An ability of a BattleCreature. <br>
 * The difference between this and AbilityType is that AbilityType is only a description
 * of an ability, but this is the ability in the hands of the creature in battle. <br>
 * That means this object also includes details like cooldown and usage of the ability. <br>
 */
public class BattleAbility {
	
	/** The type of ability this is. From this we figure out what the ability actually does. */
	public Ability type;
	/** The tick number that this ability was last used. */
	public int tickLastUsed;
	/** The number of times this ability has been used this battle. */
	public int useCount;
	
	public BattleAbility(Ability type) {
		this.type = type;
		this.tickLastUsed = Integer.MIN_VALUE/2;
		this.useCount = 0;
	}

}
