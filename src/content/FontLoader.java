package content;

import org.newdawn.slick.Font;
import org.newdawn.slick.TrueTypeFont;

/**
 * Temporary class to hold a bunch of static fonts.
 * Rename me to something more appropriate later
 */
@SuppressWarnings("deprecation")
public class FontLoader {
	public static Font ITEM_FONT, TITLE_FONT, MAIN_MENU_TITLE_FONT, POPUP_FONT;

	public static void loadAll(){
		ITEM_FONT = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 18),false);
		POPUP_FONT = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.BOLD, 12),false);
		TITLE_FONT = new TrueTypeFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 22),false);
		MAIN_MENU_TITLE_FONT = new TrueTypeFont(new java.awt.Font("Trebuchet",java.awt.Font.BOLD,40),false);
	
	}
}
