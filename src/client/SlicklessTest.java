package client;

import org.newdawn.slick.SlickException;

import overworld.OverworldStateController;
import battle.BattleStateController;
import content.OverworldLoader;

public class SlicklessTest {

	public static void main(String[] args) throws SlickException{
		int delta = 1;
		
		OverworldLoader.loadAll();
		//Load State
		LoadState ls = new LoadState(2);
		ls.init(null, null);
		ls.update(null,null,delta);
		
		//Overworld State Controller
		OverworldStateController osc = new OverworldStateController(0);
		osc.update(null, null, delta);
		
		//Battle State Controller
		BattleStateController bsc = new BattleStateController(1);
		bsc.update(null, null, delta);
	}

}
