package graphics;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class FXRenderer implements RenderComponent {
	public final ArrayList<BattleFX> fxlist;
	
	public FXRenderer() {
		fxlist = new ArrayList<BattleFX>();
	}
	
	public void render(GameContainer gc, Graphics g) {
		for(int i = 0; i< fxlist.size(); i++) {
			BattleFX fx = fxlist.get(i);
			fx.render(gc, g);
			if(fx.complete()) fxlist.remove(i--);
		}
	}

	/** Adds an FX to the renderer */
	public void add(BattleFX fx) {
		fxlist.add(fx);
	}

	/** Updates all fx requiring tick-based updates */
	public void update() {
		for(BattleFX fx : fxlist) {
			fx.update();
		}
	}

}
