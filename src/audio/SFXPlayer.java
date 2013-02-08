package audio;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SFXPlayer {
	public static final HashMap<String, Sound> sfxlist;
	
	static {
		sfxlist = new HashMap<String, Sound>();
	}
	
	public static void addPiece(String title, String path) {
		try {
			sfxlist.put(title, new Sound(path));
		} catch (SlickException e ) {
			e.printStackTrace();
		}
	}
	
	public static boolean play(String title) {
		Sound o = sfxlist.get(title);
		if(o != null) {
			o.play();
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	
	
	

}
