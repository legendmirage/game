package debug;

import client.EpicGameContainer;

public class DebugPlay extends DebugCommand {

	@Override
	public String run(String args) {
		boolean res = EpicGameContainer.MAIN.bgmPlayer.play(args);
		
		if(res) {
			return "playing " + args;
		} else {
			return "music not found";
		}
		
	}

}
