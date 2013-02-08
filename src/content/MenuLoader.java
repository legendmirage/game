package content;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MenuLoader {
	public static Image TAB_UNSELECTED, TAB_SELECTED; 
	public static Image HEADER_FRONT, HEADER_BACK;
	public static Image PORTRAIT_CECIL;
	public static Image MENU_GEAR, PORTRAIT_GEAR;
	public static Image HEALTH_BASE;
	public static Image TAB_DOWN_CENTER, TAB_DOWN_LEFT, TAB_DOWN_RIGHT, TAB_UP_CENTER, TAB_UP_LEFT, TAB_UP_RIGHT, TAB_LEFT_CENTER, TAB_RIGHT_CENTER;
	public static Image DIALOGUE_BG;
	public static Image CURSOR_MAIN, CURSOR_FLASH;
	
	public static void loadAll() throws SlickException{
		 TAB_UNSELECTED = new Image("res/art/menu/tab.png");
		 TAB_SELECTED = new Image("res/art/menu/tabSelected.png");
		 HEADER_FRONT = new Image("res/art/menu/headerFront.png");
		 HEADER_BACK = new Image("res/art/menu/headerBack.png");
		 PORTRAIT_CECIL = new Image("res/art/menu/portrait0.png");
		 MENU_GEAR = new Image("res/art/menu/menuGear.png");
		 PORTRAIT_GEAR = new Image("res/art/menu/profileGear.png");
		 HEALTH_BASE = new Image("res/art/menu/healthbase.png");

		 TAB_DOWN_CENTER = new Image("res/art/menu/downCenter.png");
		 TAB_DOWN_LEFT = new Image("res/art/menu/downLeft.png");
		 TAB_DOWN_RIGHT = new Image("res/art/menu/downRight.png");
		 TAB_UP_CENTER = new Image("res/art/menu/upCenter.png");
		 TAB_UP_LEFT = new Image("res/art/menu/upLeft.png");
		 TAB_UP_RIGHT = new Image("res/art/menu/upRight.png");
		 TAB_LEFT_CENTER = new Image("res/art/menu/leftCenter.png");
		 TAB_RIGHT_CENTER = new Image("res/art/menu/rightCenter.png");
		 
		 DIALOGUE_BG = new Image("res/art/HUD/dialogueBG.png");
		 CURSOR_MAIN = new Image("res/art/menu/cursorOne.png");
		 CURSOR_FLASH = new Image("res/art/menu/cursorTwo.png");
	}

}
