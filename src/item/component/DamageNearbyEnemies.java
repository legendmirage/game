package item.component;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.Pair;
import util.U;
import ability.effect.AbilityEffect;
import battle.BattleCreature;
import battle.BattleModel;
import battle.BattlePlayer;

public class DamageNearbyEnemies implements ItemComponent {
	private int dps;
	private float range;
	
	public DamageNearbyEnemies(float range, int dps) {
		this.range = range;
		this.dps = dps;
	}

	@Override
	public void init(final BattlePlayer pl) {
		BattleModel.MAIN.addAbilityEffect(new AbilityEffect(pl.id) {
			@Override
			public void render(GameContainer gc, Graphics g) {
				g.setColor(new Color(0x33FF0000));
				g.fillOval(pl.mx()-range, pl.my()-range, 2*range, 2*range);
			}
			@Override
			public void update(BattleModel bm) {
			}
		});
	}

	@Override
	public void update(BattlePlayer pl) {
		for(Pair<Integer, Float> p: BattleModel.MAIN.collideCircle(pl.mx(), pl.my(), range)) {
			if (p.getVal2()<=0) continue;
			BattleCreature cr = BattleModel.MAIN.creatures.get(p.getVal1());
			if (pl.sameTeam(cr)) continue;

			int a = dps/100;
			int b = dps%100;
			if(U.r()<0.01*b) cr.takeDamage(a+1);
			else cr.takeDamage(a);
		}
	}

	@Override
	public String getEffectName() {
		return "Damages nearby enemies";
	}
}
