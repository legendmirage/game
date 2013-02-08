package ability.type;

import ability.effect.AbilityEffect;
import ability.effect.Rain;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Causes rain around the player damaging enemies within the rain area. <br>
 * arg 0 : Radius of the rain <br>
 * arg 1 : Damage per pellet <br>
 * arg 2 : The probability at each tick that a new hail pellet is created <br>
 */
public class HailStorm implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		AbilityEffect torrent = new Rain(cr.id, a0, (int)a1, a2);
		bm.addAbilityEffect(torrent);
	}

	@Override
	public String getAbilityName() {
		return "Hail damages the enemies around you";
	}

}
