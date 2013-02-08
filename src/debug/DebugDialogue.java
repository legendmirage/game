package debug;

import util.Logger;
import visnovel.Cutscene;
import item.ItemFactory;
import model.GameModel;
import model.Player;


public class DebugDialogue extends DebugCommand{
	@Override
	public String run(String args) {

		GameModel gm = GameModel.MAIN;
		gm.curScene = Cutscene.play(args);
		
		return null;
		
	}
}
