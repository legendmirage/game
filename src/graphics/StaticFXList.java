package graphics;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class StaticFXList {
	
	public static Animation smoke;
	public static Animation pulseBall;

	/** Load a number of global animations used repeatedly */
	public static void load() throws SlickException {
		
		pulseBall = new Animation(new Image[] { 
			new Image("res/sprite/spells/pulseball1.png"),
			new Image("res/sprite/spells/pulseball2.png"),
			new Image("res/sprite/spells/pulseball3.png")
		},100);
	
		smoke = new Animation(new Image[] {
			new Image("res/sprite/smoke/smoke1.png"),
			new Image("res/sprite/smoke/smoke2.png"),
			new Image("res/sprite/smoke/smoke3.png")
		},10);
	}
	

}
