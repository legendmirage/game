package ability.type;

import java.util.HashSet;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.Pair;

import ability.effect.AbilityEffect;
import battle.BattleConstants;
import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

/** The player's basic attack. <br>
 */
public class PlayerBasicAttack implements AbilityType {

	@Override
	public void use(final BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		float tx = cr.mx();
		float tdx = ( cr.facingRight ? 1 : -1 ) * (BattleConstants.PLAYER_BASIC_ATTACK_RADIUS-5);
		final float x = tdx>0 ? tx : tx+tdx;
		final float dx = Math.abs(tdx);
		HashSet<Pair<Integer,Float>> hit = bm.collideCircle(x+dx/2, cr.my(), dx/2);
		for (Pair<Integer, Float> p : hit) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if(cr.sameTeam(enemy) || !enemy.alive) continue;
			if (p.getVal2()>0) {
				enemy.takeDamage(cr.basicAttackDamage);
				enemy.applyImpulse(cr.basicAttackKnockbackSpeed*(cr.facingRight?1:-1), 0, ImpulseForce.TAKE_DAMAGE, cr.basicAttackKnockbackDuration, 0);
			}
			break;
		}
	}

	@Override
	public String getAbilityName() {
		return "Player's basic attack";
	}
}
