package graphics;

import model.GameModel;
import model.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import content.MenuLoader;

/**
 * Small static class to render the overworld gear portrait
 */
public class GearPortrait {

	public static void render(GameContainer gc, Graphics g) {
		Player pl = GameModel.MAIN.player;	
		
		int HEALTH_OFFSET_X = 40, HEALTH_OFFSET_Y = 45;
		
		g.drawImage(MenuLoader.HEALTH_BASE,-1*MenuLoader.HEALTH_BASE.getWidth()/2+HEALTH_OFFSET_X,-1*MenuLoader.HEALTH_BASE.getHeight()/2+HEALTH_OFFSET_Y);
		g.setColor(new Color(0, 255, 0, 130));
		float hpangle = 330 + 150 * (1.0f - pl.hp / (float)pl.maxHP) % 360;
		g.fillArc(-78, -75, 235, 235, hpangle, 120);
		g.drawImage(MenuLoader.PORTRAIT_GEAR,0,0);
		g.drawImage(MenuLoader.PORTRAIT_CECIL,0,0);
	};

}
