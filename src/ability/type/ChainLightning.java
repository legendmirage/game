package ability.type;

import java.util.ArrayList;
import java.util.List;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import ability.effect.AbilityEffect;
import battle.BattleCreature;
import battle.BattleModel;

/**
 * A bolt of Chain Lightning which jumps from enemy to enemy. <br>
 * Each time, it jumps to the nearest enemy, starting at the player. <br>
 * Each jump, the jump distance threshold goes down and the damage goes up. <br>
 * arg 0 : base damage <br>
 * arg 1 : base jump distance threshold, in pixels <br>
 */
public class ChainLightning implements AbilityType {

	@Override
	public void use(final BattleCreature cr, final BattleModel bm, float a0, float a1, float a2) {
		int jumps = 0;
		final List<Integer> hit = new ArrayList<Integer>();
		BattleCreature current = cr;
		while(true) {
			double threshold = a1 - 50*jumps;
			int damage = (int)(a0*Math.pow(1.5f, jumps));
			BattleCreature nearest = null;
			double bestD = Double.MAX_VALUE;
			for(BattleCreature next: bm.creatures.values()) {
				if(!next.alive || next.sameTeam(cr) || hit.contains(next.id)) continue;
				double dx = current.getScreenX()-next.mx();
				double dy = current.getScreenY()-next.my();
				double d = Math.sqrt(dx*dx+dy*dy);
				if(d<bestD) {
					bestD = d;
					nearest = next;
				}
			}
			if(nearest==null || bestD>threshold) break;
			hit.add(nearest.id);
			nearest.takeDamage(damage);
			current = nearest;
			jumps++;
		}
		
		if(!hit.isEmpty()) {
			// Filler animation
			bm.addAbilityEffect(new AbilityEffect(cr.id) {
				@Override
				public void render(GameContainer gc, Graphics g) {
					float oldLineWidth = g.getLineWidth();
					g.setLineWidth(5);
					g.setColor(Color.yellow);
					g.drawLine(cr.mx(), cr.my(), bm.creatures.get(hit.get(0)).mx(), 
							bm.creatures.get(hit.get(0)).my());
					for(int i=0; i<hit.size()-1; i++) {
						BattleCreature cr1 = bm.creatures.get(hit.get(i));
						BattleCreature cr2 = bm.creatures.get(hit.get(i+1));
						g.drawLine(cr1.mx(), cr1.my(), cr2.mx(), cr2.my());
					}
					g.setLineWidth(oldLineWidth);
				}
				@Override
				public void update(BattleModel bm) {
					if(GameModel.MAIN.tickNum >= tickCreated+7) alive = false;
				}
			});
		}
	}

	@Override
	public String getAbilityName() {
		return "Lightning that jumps between enemies";
	}

}
