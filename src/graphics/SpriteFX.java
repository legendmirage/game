package graphics;

import model.GameModel;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


// FIXME: Manually FIX FRAME TRANSITIONS. ADVANCE MANUALLY SO THERE ARE NO GLITCHES
// FUCKKKKKK


/** Applies a transient sprite effect onto the screen */
public class SpriteFX implements BattleFX {
	private Animation fx;
	private int x, y;
	
	private int tickStart, tickLength, startDelay;

	/** Instantiates a transient sprite-based effect at screen location*/
	public SpriteFX(Animation framedata, int x, int y, 
			int tickLength, int startDelay) {
		this.fx = framedata.copy();
		
		// assuming animations are 10ms per frame for special effects
		fx.setSpeed((float)fx.getFrameCount() / tickLength);
		fx.stop();
		fx.setCurrentFrame(fx.getFrameCount()-1);
		
		this.tickLength = tickLength;
		//Required to figure out where renderer is 
		//PROPER
		this.tickStart = GameModel.MAIN.tickNum;
		this.startDelay = startDelay;
		
		this.x = x;
		this.y = y;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		//Required to figure out where renderer is 
		//PROPER

		// Run after the delay
		if(GameModel.MAIN.tickNum >= tickStart + startDelay) {
			fx.start();
			int w = fx.getWidth();
			int h = fx.getHeight();
			fx.draw(x - w/2, y - h + 4, w, h);
		}
	}

	@Override
	public void update() {}

	public boolean complete() {
		//Required to figure out where renderer is 
		//PROPER

		return GameModel.MAIN.tickNum > tickStart + startDelay + tickLength;
	}
}
