package debug;

import util.Logger;
import NetworkUtil.Action;
import client.ActionType;
import client.EpicGameContainer;
import enemy.EnemyFactory;

public class DebugSpawnCommand extends DebugCommand{

	@Override
	public String run(String args) {
		Logger.log("Spawning: " + args);
		Action action = new Action(ActionType.SPAWN_MONSTER,args);
		if (EnemyFactory.enemyTypes.containsKey(args)) { 
			EpicGameContainer.MAIN.protocol.sendAction(action);
			return null;
		}
		else {
			return "Failed to spawn " + args;
		}
		
	}

}
