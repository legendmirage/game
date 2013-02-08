package visnovel;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CutscenePortraitLoader {

	public static void loadAll() throws SlickException {
		loadPortrait("Sam","res/art/portraits/SamExport.png");
		loadPortrait("Haitao","res/art/portraits/HaitaoExport.png");
		loadPortrait("Factory Boss","res/art/portraits/factoryOwner.png");
		loadPortrait("Cecil","res/art/portraits/CecilPortrait.png");
		loadPortrait("Sauce Monster","res/art/portraits/JelloSprite.png");
		loadPortrait("Kid","res/art/portraits/child.png");
		loadPortrait("Jackie","res/art/portraits/Jackie.png");
		loadPortrait("Elise","res/art/portraits/Elise.png");
		loadPortrait("Shannon","res/art/portraits/shannon.png");
		loadPortrait("Ben","res/art/portraits/ben.png");
		loadPortrait("Alma", "res/art/portraits/alma.png");
		loadPortrait("Lucas","res/art/portraits/lucas.png");
		loadPortrait("Kaivan","res/art/portraits/kaivan.png");
		loadPortrait("Alex","res/art/portraits/alex.png");
		loadPortrait("Cory","res/art/portraits/cory.png");
		loadPortrait("Maker","res/art/portraits/MakerOfLife.png");
		loadPortrait("Grandfather","res/art/portraits/oldMan.png");
		loadPortrait("Annie","res/art/portraits/Annie.png");
		loadPortrait("Cortez","res/art/portraits/Cortez.png");
		loadPortrait("Guard","res/art/portraits/Guard.png");
		loadPortrait("Horace","res/art/portraits/Horace.png");
		loadPortrait("June","res/art/portraits/June.png");
		loadPortrait("Raymond","res/art/portraits/Raymond.png");
		loadPortrait("Viola","res/art/portraits/Viola.png");
		loadPortrait("Kai","res/art/portraits/Kai.png");
		loadPortrait("Eve","res/art/portraits/Eve.png");

	}
	
	private static void loadPortrait(String charName, String imgPath) throws SlickException {
		Cutscene.PORTRAITS.put(charName, new Image(imgPath));
	}
	
	/** Cannot be instantiated */
	private CutscenePortraitLoader() {}
}
