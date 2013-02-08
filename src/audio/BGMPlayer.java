package audio;

import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;

public class BGMPlayer {
	public static final HashMap<String, Music> bgmlist;
	private BGMListener listener;
	private Music curbgm;
	private Music nextbgm;
	
	public static int FADE_DURATION = 2000;
	
	private class BGMListener implements MusicListener{
		
		private BGMPlayer mplayer;
		public BGMListener(BGMPlayer mplayer) {
			this.mplayer = mplayer;
		}
		@Override
		public void musicEnded(Music music) {
			if(mplayer.nextbgm != null) {
				mplayer.curbgm = mplayer.nextbgm;
				mplayer.nextbgm = null;
				mplayer.curbgm.loop();
			}
		}
		@Override
		public void musicSwapped(Music music, Music newMusic) {}
	}
	
	static {
		bgmlist = new HashMap<String, Music>();
	}
	
	
	public BGMPlayer() {
		listener = new BGMListener(this);
	}
	
	public void addPiece(String title, String path) throws SlickException {
		Music m = new Music(path);
		m.addListener(listener);
		bgmlist.put(title, m);
	}
	

	/** Fades the existing piece and plays the next one if it is not already playing*/
	public boolean play(String title) {
		
		Music o = bgmlist.get(title);
		if(o == null) return false;
		
		o.setVolume(1);
		if(curbgm == null) {
			curbgm = o;
			curbgm.loop();
		}
		else if(o != curbgm) {
			System.out.println("Playing " + title);
			
			nextbgm = o;
			curbgm.fade(FADE_DURATION, 0, true);
			System.out.println("FADING!~");
		}
		
		return true;
	}
	
	public void stop() {
		if(curbgm!= null) {
			curbgm.fade(FADE_DURATION, 0, true);
		}
	}
}
