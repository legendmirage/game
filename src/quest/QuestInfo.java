package quest;

import java.util.ArrayList;
import java.util.HashMap;

import quest.component.QuestComponent;
import item.ItemType;

public class QuestInfo {
	
	/** Quest name	*/
	public String name;
	
	/** What the quest entails	*/
	public ArrayList<QuestComponent> components;
	
	/** String description of quest	*/
	public String description;
	
	/** Quest Reward */
	public HashMap<ItemType, Integer>reward;
	
	public QuestInfo(String name){
		this.name = name;
		this.components = new ArrayList<QuestComponent>();
		this.reward = new HashMap<ItemType, Integer>();
	}
	
	public ArrayList<QuestComponent> get(){
		return this.components;
	}
	
	public HashMap<ItemType, Integer> getReward(){
		return this.reward;
	}
	
	@Override
	public String toString(){
		this.description = "";
		for(QuestComponent q : components){
			this.description += q.toString() + "and";
		}
		
		this.description += "that's it";
		return this.description;
	}
}
