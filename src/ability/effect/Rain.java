package ability.effect;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.U;
import battle.BattleModel;

public class Rain extends AbilityEffect {
	/** The area around the player in which it rains */
	private float radius;
	/** The damage each hail pellet does */
	private int damage;
	/** The probability at each tick that a new hail pellet is spawned */
	private float spawnRate;
	
	
	private float vx;
	
	public Rain(int creatureID, float radius, int damage, float spawnRate) {
		super(creatureID, BattleModel.MAIN.creatures.get(creatureID).getScreenX(), 
				BattleModel.MAIN.creatures.get(creatureID).getScreenY() - 500);
		this.radius = radius;
		this.damage = damage;
		this.spawnRate = spawnRate;
		this.vx = (BattleModel.MAIN.creatures.get(creatureID).facingRight ? 1 : -1) * 200;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(new Color(0x55DDDDFF));
		g.fillOval(px-radius, py-35, radius, 70);
		g.fillOval(px, py-35, radius, 70);
		g.fillOval(px-radius*0.6f, py-45, radius*1.2f, 90);
	}

	@Override
	public void update(BattleModel bm) {
		if(U.r()<spawnRate) {
			float x = px + ((float)U.r()*2-1)*radius;
			bm.addAbilityEffect(new Projectile(creatureID, x, py, vx, 1000, damage, 10000));
		}
		
		// Check if the duration is up
		if(GameModel.MAIN.tickNum > tickCreated + 1000) {
			alive = false;
			return;
		}
	}

}
