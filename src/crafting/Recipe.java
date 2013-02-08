package crafting;

import item.ItemType;

import java.util.HashMap;

/** A crafting recipe. Describes some combination of items that make a new item. */
public class Recipe {
	
	public HashMap<ItemType, Integer> reagents;
	public ItemType product;
	
	public Recipe(ItemType product) {
		this.product = product;
		this.reagents = new HashMap<ItemType, Integer>();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Recipe for "+product.name+":\n");
		for(ItemType type: reagents.keySet()) {
			sb.append("   "+reagents.get(type)+" "+type.name+"\n");
		}
		return sb.toString();
	}
}
