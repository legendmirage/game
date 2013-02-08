package ability.type;

import battle.BattleCreature;
import battle.BattleModel;

/** A class of things that an ability can do. */
public interface AbilityType {
	
	/** When an ability is used, all its components will call this method. 
	 * From here they can exert their effect on the battle. */
	public void use(BattleCreature cr, BattleModel bm, float arg0, float arg1, float arg2);
	public abstract String getAbilityName();
}
