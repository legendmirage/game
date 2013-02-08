package content;

import org.newdawn.slick.SlickException;

import util.Logger;
import audio.BGMPlayer;
import audio.SFXPlayer;
import client.EpicGameContainer;

public class SoundLoader {
	
	public static void loadAll() {
		BGMPlayer p = EpicGameContainer.MAIN.bgmPlayer;
	
		try {
			p.addPiece("spire", "res/music/307806_The_Sky_Spire.ogg");
			p.addPiece("world", "res/music/465721_Traversing_the_Worl.ogg");
			p.addPiece("call", "res/music/callmemaybe.ogg");
			p.addPiece("mario", "res/music/95170_Mario_Dederichs___So.ogg");
			p.addPiece("january", "res/music/466341_January_14_2012_fan.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
			Logger.err("Music resource file loaded incorrectly");
		}
		
		SFXPlayer.addPiece("victory", "res/sound/293622_fanfare1.ogg");
	}
	
	

}
