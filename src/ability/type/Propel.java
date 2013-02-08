package ability.type;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.Pair;
import ability.effect.AbilityEffect;
import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

/**
 * Ability which propels enemies around you into the air.  <br>
 * arg 0 : radius in pixels <br>
 * arg 1 : upward speed in pixels per second <br>
 * arg 2 : duration in ticks <br>
 */
public class Propel implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, final float a0, float a1, float a2) {
		bm.addAbilityEffect(new AbilityEffect(cr.id, cr.getScreenX(), cr.getScreenY()) {
			@Override
			public void render(GameContainer gc, Graphics g) {
				g.setColor(new Color(0x55CCDDEE));
				g.fillOval(px-a0, py-a0, 2*a0, 2*a0);
			}
			@Override
			public void update(BattleModel bm) {
				if(GameModel.MAIN.tickNum >= tickCreated+5) alive = false;
			}
		});
		
		for (Pair<Integer, Float> p : bm.collideCircle(cr.mx(), cr.my(), a0)) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if (!cr.sameTeam(enemy)) {
				enemy.applyImpulse(0, -a1, ImpulseForce.TAKE_DAMAGE, (int)a2, (int)a2);
			}
		}
	}

	@Override
	public String getAbilityName() {
		return "Propel enemies into the air";
	}
}
