package ability.type;

import java.util.HashSet;

import util.Pair;
import battle.BattleCreature;
import battle.BattleModel;

/** A basic melee attack from an enemy. <br>
 * The player's basic attack is a different ability type. <br>
 * arg0: attack damage <br>
 * arg1: attack radius <br>
 */
public class MeleeAttack implements AbilityType {

  
	@Override
	public void use(final BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
	    
		float tx = cr.mx();
		float tdx = ( cr.facingRight ? 1 : -1 ) * (a1-5);
		final float x = tdx>0 ? tx : tx+tdx;
		final float dx = Math.abs(tdx);
		HashSet<Pair<Integer,Float>> hit = bm.collideCircle(x+dx/2, cr.my(), dx/2);
		for (Pair<Integer, Float> p : hit) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if(cr.sameTeam(enemy) || !enemy.alive) continue;
			if (p.getVal2()>0) {
				enemy.takeDamage((int)a0);
			}
			break;
		}
	}

	@Override
	public String getAbilityName() {
		return "Basic melee attack";
	}
}
