package model;

import graphics.PopupRenderer;
import item.ItemFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import client.EpicGameContainer;

import util.GameConstants;

/** The player's inventory. <br>
 *  Each player has one of these, which stores exactly what he has at all times. <br>
 *  This class contains a lot of utilities for managing the items in the inventory. <br>
 */

public class Inventory {
	
	/** This is the set of all items the player has ever had at any point in time. */
	private final HashSet<String> discoveredItems;
	
	/** This is a map from the name of the item type to the number that the player has of that item type. <br>
	 * To get the actual item object, use ItemFactory.get(name). <br>
	 */
	private final TreeMap<String, Integer> allItems;

	/** The names of items the player currently has equipped. <br>
	 * These give passive effects in battle. <br>
	 * These must be present in the allItems map at all times. <br>
	 * These items must have at least one passive trait. <br>
	 */
	private final String[] passiveItems;
	
	/** The names of items the player currently has active. <br>
	 * These allow players to use an ability in battle. <br>
	 * These must be present in the allItems map at all times. <br>
	 * These items must actually have active abilities. <br>
	 */
	private final String[] activeItems;
	
	/** Creates an empty inventory. */
	public Inventory() {
		this.discoveredItems = new HashSet<String>();
		this.allItems = new TreeMap<String, Integer>();
		this.passiveItems = new String[GameConstants.MAX_PASSIVE_ITEMS];
		this.activeItems = new String[GameConstants.MAX_ACTIVE_ITEMS];
	}
	
	/** Returns whether the player has discovered the given item 
	 * (whether he's ever had the item in his inventory). */
	public boolean isDiscovered(String name) {
		if(name==null) return false;
		return discoveredItems.contains(name.toLowerCase());
	}
	/** Gets the set of all items the player current has at least one of. */
	public Set<String> getAllItems() {
		return allItems.keySet();
	}
	/** Gets the active item in the given slot. */
	public String getActiveItem(int index) {
		if(index<0 || index>=activeItems.length) return null;
		return activeItems[index];
	}
	
	/** Gets the active item in the given slot. */
	public int getNumActiveItems() {
		int num = 0;
		for(int i = 0; i < GameConstants.MAX_ACTIVE_ITEMS; i++){
			if (activeItems[i] != null){
				num += 1;
			}
		}
		return num;
	}
	/** Gets the passive item in the given slot. */
	public String getPassiveItem(int index) {
		if(index<0 || index>=passiveItems.length) return null;
		return passiveItems[index];
	}
	/** Returns the total number of the given item that the player has. */
	public int getItemCount(String name) {
		if(name==null) return 0;
		name = name.toLowerCase();
		if(allItems.containsKey(name))
			return allItems.get(name);
		else
			return 0;
	}
	
	/** Discovers the item without actually getting any of it. <br>
	 * Discovering an item makes recipes that use it visible in the crafting tab. <br> */
	public void discoverItem(String name) {
		discoveredItems.add(name);
	}
	
	/** Clears the character's memory of all discovered items */
	public void clearDiscoveredItemMemory(){
		discoveredItems.clear();
	}
	
	/** Adds one item to the inventory. */
	public void addItem(String name) {
		addItem(name, 1);
	}
	/** Adds multiple items of the same type to the inventory. */
	public void addItem(String name, int amount) {
		if(name==null || name.trim().length()==0) return;
		name = name.toLowerCase();
		if(allItems.containsKey(name)) {
			allItems.put(name, allItems.get(name) + amount);
		} else {
			allItems.put(name, amount);
		}
		discoveredItems.add(name);
		PopupRenderer.addPopup(name+" added to inventory");
		EpicGameContainer.MAIN.updateMenu();
	}
	/** Makes the given item active. Item must already be in the inventory. <br>
	 * If item is in another slot, that slot will be cleared. <br>
	 * Pass in null as the name to clear a slot. <br>
	 */
	public void setActiveItem(String name, int slot) {
		if(!canSetActiveItem(name, slot)) return;
		if (name!=null){
			name = name.toLowerCase();
			unequipItem(name);
		}
		activeItems[slot] = name;
	}
	/** Equips the given item as a passive. Item must already be in the inventory. <br>
	 * If item is in another slot, that slot will be cleared. <br>
	 * Pass in null as the name to clear a slot. <br>
	 */
	public void setPassiveItem(String name, int slot) {
		if(!canSetPassiveItem(name, slot)) return;
		if (name!=null){
			name = name.toLowerCase();
			unequipItem(name);
		}
		passiveItems[slot] = name;
	}
	/** Makes the given item not go in any equipment slot, but still in the inventory. */
	public void unequipItem(String name) {
		if(name==null) return;
		name = name.toLowerCase();
		for(int i=0; i<activeItems.length; i++) {
			if(name.equals(activeItems[i]))
				activeItems[i] = null;
		}
		for(int i=0; i<passiveItems.length; i++) {
			if(name.equals(passiveItems[i]))
				passiveItems[i] = null;
		}
	}
	/** Removes one copy of the given item from the inventory. */
	public void removeItem(String name) {
		removeItem(name, 1);
	}
	/** Removes multiple copies of the given item from the inventory. */
	public void removeItem(String name, int amount) {
		if(!canRemoveItem(name, amount)) return;
		name = name.toLowerCase();
		allItems.put(name, allItems.get(name)-amount);
		if(allItems.get(name) == 0) {
			allItems.remove(name);
			unequipItem(name);
		}
		EpicGameContainer.MAIN.updateMenu();
	}
	/** Returns whether calling setActiveItem(name, slot) will do anything. */
	public boolean canSetActiveItem(String name, int slot) {
		if(name==null) return true;
		name = name.toLowerCase();
		if(ItemFactory.get(name)==null || slot < 0 || slot >= activeItems.length) return false;
		if(!allItems.containsKey(name)) return false;
		if(ItemFactory.get(name).ability == null) return false;
		return true;
	}
	/** Returns whether calling setPassiveItem(name, slot) will do anything. */
	public boolean canSetPassiveItem(String name, int slot) {
		if(name==null) return true;
		name = name.toLowerCase();
		if(ItemFactory.get(name)==null || slot < 0 || slot >= passiveItems.length) return false;
		if(!allItems.containsKey(name)) return false;
		if(ItemFactory.get(name).components.size() == 0) return false;
		return true;
	}
	/** Returns whether calling removeItem(name) will do anything. */
	public boolean canRemoveItem(String name) {
		return canRemoveItem(name, 1);
	}
	/** Returns whether calling removeItem(name, amount) will do anything. */
	public boolean canRemoveItem(String name, int amount) {
		if(name==null) return false;
		name = name.toLowerCase();
		if(ItemFactory.get(name)==null || !allItems.containsKey(name)) return false;
		if(allItems.get(name) < amount) return false;
		return true;
	}
	
	/** Checks the inventory data and returns whether it is valid. <br>
	 * Meant to be used for testing. */
	public boolean checkConsistentData() {
		
		// Check that all item names are valid
		for(String s: allItems.keySet()) {
			if(ItemFactory.get(s) == null) return false;
		}
		
		// Check that active items are in the inventory and have abilities
		for(String s: activeItems) {
			if(!allItems.containsKey(s)) return false;
			if(ItemFactory.get(s).ability == null) return false;
		}
		
		// Check that passive items are in the inventory and have passive abilities
		for(String s: passiveItems) {
			if(!allItems.containsKey(s)) return false;
			if(ItemFactory.get(s).components.size() == 0) return false;
		}
		
		// Check that no item is in two different equipment slots
		HashSet<String> buckets = new HashSet<String>();
		for(String s: activeItems) {
			if(buckets.contains(s)) return false;
			buckets.add(s);
		}
		for(String s: passiveItems) {
			if(buckets.contains(s)) return false;
			buckets.add(s);
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("----- Inventory -----\n");
		sb.append(" All Items:\n");
		for(String name: allItems.keySet()) {
			sb.append("   "+allItems.get(name)+" "+name+"\n");
		}
		sb.append(" Active:\n");
		for(String s: activeItems) {
			sb.append(s==null?"   EMPTY\n":"   "+s+"\n");
		}
		sb.append(" Passive:\n");
		for(String s: passiveItems) {
			sb.append(s==null?"   EMPTY\n":"   "+s+"\n");
		}
		return sb.toString();
	}
}
