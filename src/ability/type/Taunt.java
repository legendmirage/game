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
 * Taunts enemies directly in front, making them faster but take more damage. <br>
 * Also makes player take more damage from all sources. <br>
 * Effect is stackable, and won't expire if you keep stacking it. <br>
 * arg 0 : range in pixels <br>
 * arg 1 : enemy damage multiplier (0.2f means every taunt makes enemy take 20% more damage) <br>
 * arg 2 : caster damage multiplier (0.3f means every taunt makes you take 30% more damage) <br>
 */
public class Taunt implements AbilityType {

	@Override
	public void use(final BattleCreature cr, final BattleModel bm, float a0, float a1, float a2) {
		final float range = a0;
		final float x = cr.facingRight ? cr.getScreenX()+cr.getWidth() : cr.getScreenX() - range;
		for(Pair<Integer, Float> p: bm.collideRectangle(x, cr.getScreenY(), range, cr.getHeight())) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if(enemy.sameTeam(cr)) continue;
			enemy.tauntTicksLeft = 1000;
			enemy.tauntDamageMultiplier += a1;
			enemy.tauntMoveSpeedMultiplier += 0.2f;
		}
		
		cr.tauntDamageMultiplier += a2;
		cr.tauntTicksLeft = 1000;
		
		// Filler animation
		bm.addAbilityEffect(new AbilityEffect(cr.id) {
			@Override
			public void render(GameContainer gc, Graphics g) {
				g.setColor(new Color(0x33FF0000));
				g.fillRect(x, cr.getScreenY(), range, cr.getHeight());
			}
			@Override
			public void update(BattleModel bm) {
				if(GameModel.MAIN.tickNum >= tickCreated+7) alive = false;
			}
		});
	}

	@Override
	public String getAbilityName() {
		return "Taunt enemies";
	}

}
