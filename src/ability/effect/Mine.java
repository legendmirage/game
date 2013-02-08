package ability.effect;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.GameConstants;
import util.Pair;
import battle.BattleConstants;
import battle.BattleCreature;
import battle.BattleModel;
import battle.BattleTileType;
import battle.ImpulseForce;

/** Places a mine at the given location */
public class Mine extends AbilityEffect {
	private static int vy = 700;
	private int duration;
	private int velocity;
	private int damage;
	private boolean firstUpdated;
	private int size;
	private boolean onGround;
	public Mine(int creatureID, float px, float py, int damage, 
			int size, int velocity, int duration) {
		super(creatureID, px, py);
		this.damage = damage;
		this.firstUpdated = false;
		this.size = size;
		this.velocity = velocity;
		this.duration = duration;
		this.onGround = false;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if (!firstUpdated) return;
		g.setColor(Color.blue);
		g.fillRect(px, py, size, size);
	}

	@Override
	public void update(BattleModel bm) {
		firstUpdated = true;
		if (!onGround && bm.map.getTileAtCoord(px, py) == BattleTileType.SPACE) {
			float dt = GameConstants.GAME_SPEED/1000.0f;
			py += vy*dt;
		} else if (!onGround){
			onGround = true;
			int ty = (int) (py/BattleConstants.TILE_HEIGHT);
			py = ty*BattleConstants.TILE_HEIGHT-size;
		}
		BattleCreature owner = bm.creatures.get(creatureID);
		for(Pair<Integer, Float> p : bm.collideRectangle(px, py, size, size)) {
			BattleCreature cr = bm.creatures.get(p.getVal1());
			if (owner.sameTeam(cr)) continue;
			if (p.getVal2()>0) {
				cr.takeDamage(damage);
				cr.applyImpulse(0, -velocity, ImpulseForce.TAKE_DAMAGE, duration, duration);
			}
			alive = false;
			return;
		}
	}
}
