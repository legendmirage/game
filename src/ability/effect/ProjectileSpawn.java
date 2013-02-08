package ability.effect;

import model.GameModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import util.GameConstants;
import battle.BattleCreature;
import battle.BattleModel;
import battle.BattleTileType;

public class ProjectileSpawn extends Projectile {
	private int number;
	
	public ProjectileSpawn(int creatureID, float px, float py, float vx,
			float vy, int damage, int duration, int number) {
		super(creatureID, px, py, vx, vy, damage, duration);
		this.number = number;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		super.render(gc, g);
	}
	
	@Override
	public void update(BattleModel bm) {
		float dt = GameConstants.GAME_SPEED/1000.0f;
		px += vx * dt;
		py += vy * dt;
		
		// Check each creature to see if it has collided with this projectile
		BattleCreature owner = bm.creatures.get(creatureID);
		
		// Check if the projectile has gone out of range
		if(GameModel.MAIN.tickNum > tickCreated + duration || bm.map.getTileAtCoord(px, py) == BattleTileType.WALL) {
			alive = false;
			int angle = (int) (360/number);
			for (int i=0; i<360; i=i+angle) {
				int vx = (int) (300*Math.cos(Math.toRadians(i)));
				int vy = (int) (300*Math.sin(Math.toRadians(i)));
				dt = GameConstants.GAME_SPEED/1000.0f;
				float x = (px + vx*dt);
				float y = (py + vy*dt);
				Projectile proj = new Projectile(owner.id, x, y, vx, vy, (int)damage, duration*3);
				bm.addAbilityEffect(proj);
			}
			return;
		}
	}
}
