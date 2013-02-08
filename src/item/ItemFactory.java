package item;

import item.component.ItemComponent;
import item.use.ItemUse;

import java.util.HashMap;

import org.newdawn.slick.Image;

import util.Logger;
import ability.Ability;

/** A utility class that constructs new types of items and modifies the properties of existing types of items. 
 * 
 */
public class ItemFactory {
	private static HashMap<String, ItemType> itemTypes; 
	static {
		itemTypes = new HashMap<String, ItemType>();
	}
	/** This cannot be instantiated. */
	private ItemFactory() {}
	
	/** Returns the item type with the given name, or null if it does not exist. */
	public static ItemType get(String itemName) {
		if(itemName==null) return null;
		ItemType ret = itemTypes.get(itemName.toLowerCase());
		if(ret==null) Logger.log("Tried to get a nonexistent item: "+itemName);
		return ret;
	}
	public static void create(String itemName) {
		itemTypes.put(itemName.toLowerCase(), new ItemType(itemName));
	}
	public static void addCost(String itemName, int cost){
		ItemType type = get(itemName);
		type.addCost(cost);
	}
	public static void addComponent(String itemName, ItemComponent component) {
		ItemType type = get(itemName);
		if(type==null) {
			Logger.err("Tried to modify item \""+itemName+"\", which does not exist yet.");
			return;
		}
		if(type.ability!=null) {
			Logger.err("Item \""+itemName+"\" cannot have an ability and also have item components.");
			return;
		}
		type.components.add(component);
	}
	public static void addUseEffect(String itemName, ItemUse useEffect) {
		ItemType type = get(itemName);
		if(type==null) {
			Logger.err("Tried to modify item \""+itemName+"\", which does not exist yet.");
			return;
		}
		type.useEffects.add(useEffect);
	}
	public static void setAbility(String itemName, Ability ability) {
		ItemType type = get(itemName);
		if(type==null) {
			Logger.err("Tried to modify item \""+itemName+"\", which does not exist yet.");
			return;
		}
		if(!type.components.isEmpty()) {
			Logger.err("Item \""+itemName+"\" cannot have an ability and also have item components.");
			return;
		}
		type.ability = ability;
	}
	public static void setImage(String itemName, Image image) {
		ItemType type = get(itemName);
		if(type==null) {
			Logger.err("Tried to modify item \""+itemName+"\", which does not exist yet.");
			return;
		}
		type.image = image;
	}
	public static HashMap<String, ItemType> getAllItems(){
		return itemTypes;
	}
	
}
