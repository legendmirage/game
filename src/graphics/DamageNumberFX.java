package graphics;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.U;

public class DamageNumberFX implements BattleFX {

	/** total duration of the effect */
	private final static int DURATION = 100;
	
	/** total duration of the fade at the end */
	private final static int FADE_DURATION = 20;
	
	/** source on the right, number flies to the left */
	private final static int LEFT = -1;
	/** source on the left, number flies to the right */
	private final static int RIGHT = 1;
	/** source unknown, number flies upwards */
	private final static int CENTER = 0;
	
	
	private float vx, vy;
	private float px, py;
	private final int damage;
	private final int direction;
	private final int tickStart;
	private final boolean playerDamage;
	
	public DamageNumberFX(int damage, int x, int y, int direction, boolean playerDamage) {
		this.damage = damage;
		this.px = x;
		this.py = y;
		this.tickStart = GameModel.MAIN.tickNum;
		this.direction = direction;
		this.playerDamage = playerDamage;
		
		vx = (float) (direction * ( 1.0 * U.r()));
		vy = (float) (1.0 * U.r() + 3.0);
	}
	

	@Override
	public void update() { 
		px += vx;
		py -= vy;
		
		vy += -0.08;
	}
	
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		
		
		int alpha = (int)(255 * U.alphaRamp(DURATION, FADE_DURATION, tickStart));
		
		if(playerDamage) g.setColor(new Color(0,0,255, alpha));
		else g.setColor(new Color(255, 0, 0, alpha));
		
		// TODO: REPLACE ME WITH A BITMAP FONT
		g.drawString(Integer.toString(damage), px, py);
	}

	@Override
	public boolean complete() {
		return (GameModel.MAIN.tickNum - tickStart) >= DURATION;
	}

}
