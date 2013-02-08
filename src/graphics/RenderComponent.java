package graphics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/** Interface for the renderable component*/
public abstract interface RenderComponent {
	
	/** Call to render the object */
	public abstract void render(GameContainer gc, Graphics g);
	
}