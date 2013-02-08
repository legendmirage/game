package ability.type;

import util.Pair;
import ability.effect.Slow;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Ability due to which nearby enemies move slower <br>
 * arg0: duration <br>
 * arg1: range  <br>
 * arg2: move speed multiplier (0.25f to make stuff move 4x slower) <br>
 */
public class Bind implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		for(Pair<Integer, Float> p: bm.collideCircle(cr.mx(), cr.my(), a1)) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if (cr.sameTeam(enemy)) continue;
			bm.addAbilityEffect(new Slow(enemy.id, (int)a0, a2));
		}
	}

	@Override
	public String getAbilityName() {
		return "Make nearby enemies move slower";
	}

}
