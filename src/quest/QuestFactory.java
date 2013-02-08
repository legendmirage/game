package quest;

import java.util.HashMap;

import quest.component.QuestComponent;
import util.Logger;
import item.ItemType;

public class QuestFactory {
	private static HashMap<String, QuestInfo> quests; 
	static {
		quests = new HashMap<String, QuestInfo>();
	}
	
	/** This cannot be instantiated. */
	private QuestFactory() {}
	
	/** Returns the Quest information */
	public static QuestInfo get(String name) {
		if(name==null) return null;
		QuestInfo ret = quests.get(name.toLowerCase());
		if(ret==null) Logger.log("Tried to get a nonexistent quest: "+name);
		return ret;
	}
	
	/** Creates a new Quest */
	public static void create(String name) {
		quests.put(name.toLowerCase(), new QuestInfo(name));
	}
	
	/** Adds compoment to Quest */
	public static void addComponent(String name, QuestComponent component) {
		QuestInfo questInfo = get(name);
		questInfo.components.add(component);
		
	}
	
	/** Adds reward to Quest */
	public static void reward(String name, ItemType item, int num) {
		QuestInfo questInfo = get(name);
		questInfo.reward.put(item, num);
	}
}
