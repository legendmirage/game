package ability.effect;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.GameConstants;

import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

public class Hook extends AbilityEffect {
	private float fx, fy;
	private float vx,vy;
	private int damage;
	private int range;
	private boolean firstUpdated;
	private boolean grabbed;
	public Hook(int creatureID, float px, float py, float vx, float vy, int damage, int range) {
		super(creatureID, px, py);
		this.fx = px;
		this.fy = py;
		this.vx = vx;
		this.vy = vy;
//		this.speed = speed;
		this.damage = damage;
		this.range = range;
		this.grabbed = false;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if (!firstUpdated) return;
		g.setColor(Color.blue);
		g.drawLine(fx, fy, px, py);
	}

	@Override
	public void update(BattleModel bm) {
		BattleCreature player = bm.creatures.get(GameModel.MAIN.player.id);
		BattleCreature owner = bm.creatures.get(creatureID);
		if (!firstUpdated) {
			firstUpdated = true;
		}
		owner.hookSpeedMultiplier = 0;
		float dt = GameConstants.GAME_SPEED/1000.0f;
		px += vx * dt;
		py += vy * dt;
		float dist = (float) Math.sqrt((fx-px)*(fx-px)+(fy-py)*(fy-py));
		if (!grabbed) {
			if (player.isInHitbox(px, py)) {
				if (owner.facingRight) {
					player.setScreenX(owner.getScreenX()+owner.getWidth()+10);
				} else {
					player.setScreenX(owner.getScreenX()-player.getWidth()-10);
				}
				player.setScreenY(fy-player.getHeight());
				player.applyImpulse(0, 0, ImpulseForce.ASSASSINATE, 100, 100);
				player.takeDamage(damage);
				owner.applyImpulse(0, 0, ImpulseForce.ASSASSINATE, 0, 0);
				alive = false;
				owner.hookSpeedMultiplier = 1;
				owner.grappleHook = false;
			}
		}
		owner.applyImpulse(0, 0, ImpulseForce.ASSASSINATE, 10, 10);
		// Check if out of range
		if (dist>range) {
			alive = false;
			owner.hookSpeedMultiplier = 1;
			owner.grappleHook = false;
			return;
		}
	}
	
	/**
	 * Calculates angle between two points and the x-axis where (x1,y1) is taken
	 * to be on the x-axis
	 * 
	 * @param x1
	 *            x co-ordinate of the point on the X-axis
	 * @param y1
	 *            y co-ordinate of the point on the X-axis
	 * @param x2
	 *            x co-ordinate of the second point
	 * @param y2
	 *            y co-ordinate of the second point
	 * @return the angle between the line joining the two points and the X-axis
	 *         in Radians
	 */
	/*
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
	*/
}
