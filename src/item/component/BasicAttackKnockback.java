package item.component;

import battle.BattlePlayer;

/** Gives the player's basic attack a non-stunning knockback of variable distance. */
public class BasicAttackKnockback implements ItemComponent {
	private float speed;
	private int duration;
	
	public BasicAttackKnockback(int duration, float speed) {
		this.duration = duration;
		this.speed = speed;
	}

	@Override
	public void init(BattlePlayer pl) {
		pl.basicAttackKnockbackDuration = Math.max(pl.basicAttackKnockbackDuration, duration);
		pl.basicAttackKnockbackSpeed = Math.max(pl.basicAttackKnockbackSpeed, speed);
	}

	@Override
	public void update(BattlePlayer pl) {
		
	}

	@Override
	public String getEffectName() {
		return "Knockback";
	}
}
