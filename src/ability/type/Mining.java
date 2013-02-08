package ability.type;

import ability.effect.Mine;
import battle.BattleCreature;
import battle.BattleModel;

/** 
 * Ability that places mines of given size below the creature casting it. <br>
 * Affects only the opposing team members <br>
 * arg 0 : Damage dealt by the mine <br>
 * arg 1 : The size of the mine <br>
 */
public class Mining implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		int velocity = (int) (a0*100);
		int duration = (int) (a0+5);
		int x = (int) cr.mx();
		int y = (int) cr.my();
		Mine m = new Mine(cr.id, x, y, (int)a0, (int)a1, velocity, duration);
		bm.addAbilityEffect(m);
	}

	@Override
	public String getAbilityName() {
		return "Place mines";
	}
}
