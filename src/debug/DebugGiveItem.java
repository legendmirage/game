package debug;

import java.util.Map.Entry;

import util.Logger;
import item.ItemFactory;
import item.ItemType;
import model.GameModel;
import model.Player;
import NetworkUtil.Action;
import client.ActionType;
import client.EpicGameContainer;

public class DebugGiveItem extends DebugCommand{

	@Override
	public String run(String args) {

		Player pl = GameModel.MAIN.player;
		if (args.equals("all")){
	        for (Entry<String, ItemType> item : ItemFactory.getAllItems().entrySet()){
	        	pl.inventory.addItem(item.getKey(),1);
	        }
		}
		else if (ItemFactory.get(args)!=null) pl.inventory.addItem(args);
		else return "Failed to add item -" + args + "-";
		
		return null;
	}

}
