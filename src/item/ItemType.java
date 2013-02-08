package item;

import item.component.ItemComponent;
import item.use.ItemUse;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import ability.Ability;

/** A type of item. This does not represent an actual item in a player's inventory, 
 * only a description of an item. <br>
 * These should only be created and modified via ItemFactory. <br>
 */
public class ItemType implements Comparable<ItemType> {
	public String name;
	public Ability ability;
	public ArrayList<ItemUse> useEffects;
	public ArrayList<ItemComponent> components;
	public Image image;
	private int cost=50;
	
	/** Creates a item with the given name that does nothing. */
	public ItemType(String name) {
		this.name = name;
		this.ability = null;
		this.useEffects = new ArrayList<ItemUse>();
		this.components = new ArrayList<ItemComponent>();
		this.image = null;
	}
	
	/** Adds a cost to an object */
	public void addCost(int cost){
		this.cost = cost;
	}
	
	/** Returns the cost of an object */
	public int getCost(){
		return cost;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o==null || !(o instanceof ItemType)) return false;
		ItemType other = (ItemType)o;
		return name.equals(other.name);
	}

	@Override
	public int compareTo(ItemType o) {
		if(o==null) return 1;
		return name.compareTo(o.name);
	}
}
