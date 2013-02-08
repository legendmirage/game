package item.component;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import ability.effect.AbilityEffect;
import ability.type.Bind;
import battle.BattleModel;
import battle.BattlePlayer;

/** Slows enemies within a given range of the player's midpoint by a given multiplier. */
public class SlowNearbyEnemies implements ItemComponent {
	private float enemySpeedMultiplier;
	private float range;
	
	public SlowNearbyEnemies(float range, float enemySpeedMultiplier) {
		this.range = range;
		this.enemySpeedMultiplier = enemySpeedMultiplier;
	}

	@Override
	public void init(final BattlePlayer pl) {
		BattleModel.MAIN.addAbilityEffect(new AbilityEffect(pl.id) {
			@Override
			public void render(GameContainer gc, Graphics g) {
				g.setColor(new Color(0x330099FF));
				g.fillOval(pl.mx()-range, pl.my()-range, 2*range, 2*range);
			}
			@Override
			public void update(BattleModel bm) {
			}
		});
	}

	@Override
	public void update(BattlePlayer pl) {
		new Bind().use(pl, BattleModel.MAIN, 1, range, enemySpeedMultiplier);
	}

	@Override
	public String getEffectName() {
		return "Slow nearby enemies";
	}
}
