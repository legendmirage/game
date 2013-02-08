package quest.component;

import item.ItemType;
import model.GameModel;

public class GetItems implements QuestComponent {
	
	public ItemType itemType;
	public int numToGet;
	
	public GetItems(ItemType itemType, int num){
		this.itemType = itemType;
		this.numToGet = num;
	}
	
	/** For menu display of quests. Says how many monsters left to kill.*/
	@Override
	public String toString(){
		return ("Get " + numToGet + " " + itemType.name + "s" + " (" + getNumGot( ) + "/" + numToGet + ")");
	}

	
	@Override
	public boolean complete(){
		if(getNumGot() >= numToGet){
			return true;
		}
		return false;
	}
	
	/** Calculates number of items of this type that the player currently has.*/
	private int getNumGot(){
		return GameModel.MAIN.player.inventory.getItemCount(itemType.name);
	}
}
