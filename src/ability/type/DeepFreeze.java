package ability.type;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.Pair;
import ability.effect.AbilityEffect;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * Ability which freezes the enemy. Any attack will shatter the frozen enemy for bonus damage. <br>
 * arg 0 : the radius of the deep freeze <br>
 * arg 1 : the duration of the deep freeze <br>
 */
public class DeepFreeze implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, final float a0, float a1, float a2) {
		bm.addAbilityEffect(new AbilityEffect(cr.id, cr.getScreenX(), cr.getScreenY()) {
			@Override
			public void render(GameContainer gc, Graphics g) {
				g.setColor(new Color(0x550055FF));
				g.fillOval(px-a0, py-a0, 2*a0, 2*a0);
			}
			@Override
			public void update(BattleModel bm) {
				if(GameModel.MAIN.tickNum >= tickCreated+5) alive = false;
			}
		});
		
		for (Pair<Integer, Float> p : bm.collideCircle(cr.mx(), cr.my(), a0)) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if (enemy.sameTeam(cr) || !enemy.alive) continue;
			float multiplier = 1;
			if(enemy.type.power>5) {
				multiplier = 1-0.05f*(enemy.type.power-5);
			}
			enemy.freezeDuration += (int)(a1*multiplier);
		}
	}

	@Override
	public String getAbilityName() {
		return "Freeze the enemy";
	}
}