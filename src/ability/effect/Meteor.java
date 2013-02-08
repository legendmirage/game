package ability.effect;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import util.GameConstants;
import util.Pair;
import battle.BattleCreature;
import battle.BattleModel;
import battle.BattleTileType;

public class Meteor extends AbilityEffect {
	private boolean firstUpdated;
	private int radius;
	private int damage;
	private float vx,vy;
	private float speed;
	private float fx, fy;

	public Meteor(int creatureID, float px, float py, float speed, int radius, int damage, float fx, float fy) {
		super(creatureID, px, py);
		this.firstUpdated = false;
		this.radius = radius;
		this.damage = damage;
		this.vx = 0;
		this.vy = 0;
		this.speed = speed;
		this.fx = fx;
		this.fy = fy;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if (!firstUpdated) return;
		g.setColor(Color.orange);
		g.fillOval(px-radius, py-radius, radius*2, radius*2);
	}

	@Override
	public void update(BattleModel bm) {
		if (!firstUpdated) {
			firstUpdated = true;
		}
		BattleCreature owner = bm.creatures.get(creatureID);
		float dt = GameConstants.GAME_SPEED/1000.0f;
		double angleRadians = getAngle(owner.mx(), 0, fx, fy);
		vx = (float) (Math.cos(angleRadians) * speed);
		vy = (float) (Math.sin(angleRadians) * speed);
		px += vx * dt;
		py += vy * dt;
		for (Pair<Integer, Float> p : bm.collideCircle(px, py, radius)) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if (owner.sameTeam(enemy) || !enemy.alive) continue;
			if (p.getVal2()>0) {
				enemy.takeDamage(damage);
			}
		}
		
		if(bm.map.getTileAtCoord(px, py) == BattleTileType.WALL) {
			alive = false;
			return;
		}
	}
	
	private static double getAngle(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		// Minus to correct for coord re-mapping
		double dy = -(y2 - y1);

		double inRads = Math.atan2(dy, dx);

		// We need to map to coord system when 0 degree is at 3 O'clock, 270 at
		// 12 O'clock
		if (inRads < 0)
			inRads = Math.abs(inRads);
		else
			inRads = 2 * Math.PI - inRads;

		return inRads;
	}

}
