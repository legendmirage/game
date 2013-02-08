package ability.type;

import ability.effect.Meteor;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Ability which does a meteor strike <br>
 * arg 0 : Damage done by meteor <br>
 * arg 1 : Radius of the meteor <br>
 * arg 2 : Speed of the meteor <br>
 */
public class MeteorStrike implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float arg0, float arg1, float arg2) {
		for (BattleCreature enemy : bm.creatures.values()) {
			if (!enemy.alive || enemy.sameTeam(cr)) continue;
			Meteor m = new Meteor(cr.id, cr.mx(), 0, arg2, (int)arg1, (int)arg0, enemy.mx(), enemy.my());
			bm.addAbilityEffect(m);
			return;
		}
	}

	@Override
	public String getAbilityName() {
		return "Ability which strikes a meteor to the ground";
	}
}
