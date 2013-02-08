package debug;

import client.EpicGameContainer;

public class DebugMute extends DebugCommand  {

	@Override
	public String run(String args) {
		EpicGameContainer.MAIN.bgmPlayer.stop();
		return null;
	}
}
