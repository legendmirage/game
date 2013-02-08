package crafting;

import item.ItemFactory;
import item.ItemType;

import java.util.TreeMap;

import util.Logger;

/** A utility class that constructs new crafting recipes and modifies the properties of existing recipes. <br>
 * Creating recipes relies on having the reagents and products already have been loaded into the ItemFactory. <br>
 */
public class RecipeFactory {
	private static TreeMap<ItemType, Recipe> recipes; 
	static {
		recipes = new TreeMap<ItemType, Recipe>();
	}
	/** This cannot be instantiated. */
	private RecipeFactory() {}
	
	/** Returns the recipe for the item of the given name, or null if it does not exist. */
	public static Recipe get(String itemName) {
		ItemType item = ItemFactory.get(itemName);
		if(item==null) return null;
		Recipe ret = recipes.get(item);
		if(ret==null) Logger.log("Tried to get a nonexistent recipe: "+itemName);
		return ret;
	}
	private static void create(ItemType type) {
		recipes.put(type, new Recipe(type));
	}
	public static void clearAll(){
		recipes = new TreeMap<ItemType,Recipe>();
	}
	public static void addReagent(String itemName, String reagentName) {
		addReagent(itemName, reagentName, 1);
	}
	public static void addReagent(String itemName, String reagentName, int amount) {
		ItemType type = ItemFactory.get(itemName);
		if(type==null) {
			Logger.err("Tried to create a recipe for item \""+itemName+"\", but the item does not exist.");
			return;
		}
		if(!recipes.containsKey(type)) {
			create(type);
		}
		Recipe recipe = get(itemName);
		ItemType reagentType = ItemFactory.get(reagentName);
		if(reagentType==null) {
			Logger.err("Tried to add item \""+reagentType+"\", which does not exist yet, to a crafting recipe.");
			return;
		}
		if(recipe.reagents.containsKey(reagentType))
			recipe.reagents.put(reagentType, recipe.reagents.get(reagentType)+amount);
		else 
			recipe.reagents.put(reagentType, amount);
	}
	
	public static String[] getAllRecipeNames() {
		String[] ret = new String[recipes.size()];
		int i=0;
		for(ItemType type: recipes.keySet()) {
			ret[i++] = type.name;
		}
		return ret;
	}
}