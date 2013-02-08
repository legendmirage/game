package debug;

import java.util.HashMap;

import overworld.MonsterGroup;

import model.GameModel;
import model.Player;

public class DebugRemoveMonsters extends DebugCommand {

	@Override
	public String run(String args) {
		int a = GameModel.MAIN.player.zone;
		HashMap<Integer, MonsterGroup > monsters = new HashMap<Integer, MonsterGroup>();
		monsters.putAll(GameModel.MAIN.zones.get(a).monsters);
		for(Integer id: monsters.keySet()){
			GameModel.MAIN.zones.get(a).monsters.remove(id);
		}
		return null;
	}

}
