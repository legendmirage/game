package ability.effect;

import graphics.StaticFXList;
import model.GameModel;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.GameConstants;
import util.Pair;
import battle.BattleCreature;
import battle.BattleModel;
import battle.BattleTileType;
import client.EpicGameContainer;

/** A general purpose representation of a projectile, like a fireball or flying knife. <br>
 * Should be extended for projectiles that do special things, like a homing missile. <br>
 */
public class Projectile extends AbilityEffect {
	public float vx, vy;
	public float r;
	public int damage;
	
	/** Ticks that this projectile lasts before it disappears. */
	public int duration;
	
	private Animation animation;
	
	public Projectile(int creatureID, float px, float py, float vx, 
			float vy, int damage, int duration) {
		super(creatureID, px, py);
		
		this.vx = vx;
		this.vy = vy;
		
		this.r = 7; // arbitrary for now
		this.damage = damage;
		this.duration = duration;
		
		animation = StaticFXList.pulseBall.copy();
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
	
		
		// TODO: REFACTOR THIS LOGIC OUT OF PROJECTILE
		int maxDimension = Math.max(animation.getWidth(),animation.getHeight());
		int minDimension = Math.min(animation.getWidth(),animation.getHeight());
		int dimDifference = maxDimension - minDimension;
		float scaleFactor = this.r / (minDimension/2);
		
		animation.draw(px-r*scaleFactor, py-r*scaleFactor-dimDifference/2.0f, 2*animation.getWidth()*scaleFactor, 2*animation.getHeight()*scaleFactor);
		
		if(EpicGameContainer.MAIN.debugDraw) {
			g.setColor(Color.blue);
			g.drawOval(px-r, py-r, 2*r, 2*r);
		}
	}
	
	@Override
	public void update(BattleModel bm) {
		float dt = GameConstants.GAME_SPEED/1000.0f;
		px += vx * dt;
		py += vy * dt;
		
		// Check each creature to see if it has collided with this projectile
		BattleCreature owner = bm.creatures.get(creatureID);
		for(Pair<Integer, Float> p: bm.collidePoint(px, py)) {
			BattleCreature cr = bm.creatures.get(p.getVal1());
			if (owner.sameTeam(cr)) continue;
			if (p.getVal2()>0) {
				cr.takeDamage(damage);
			}
			alive = false;
			return;
		}
		
		// Check if the projectile collided with a wall
		if(bm.map.getTileAtCoord(px, py) == BattleTileType.WALL) {
			alive = false;
			return;
		}
		
		// Check if the projectile has gone out of range
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}
}
