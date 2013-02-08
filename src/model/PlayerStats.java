package model;

import java.util.HashMap;

import util.Logger;

/** Contains all of a player's stat attributes. <br>
 * 
 */
public class PlayerStats {
	
	/** The screen name of the player. The user picks this at the beginning and can change it. */
	public String name;
	/** A mapping of attribute name to attribute value. */
	public HashMap<String, Integer> attributeMap; 
	
	public PlayerStats() {
		this.name = "Cecil";
		this.attributeMap = new HashMap<String, Integer>();
		set("vitality", 0);
		set("wisdom", 0);
		set("speed", 0);
		set("power", 0);
		set("protection", 0);
	}
	
	/** Returns the player's stat for the given attribute name. */
	public int get(String attribute) {
		if(!attributeMap.containsKey(attribute.toLowerCase())) {
			Logger.err("ERROR: trying to get an invalid player attribute: "+attribute);
			return 0;
		}
		return attributeMap.get(attribute.toLowerCase());
	}
	
	/** Sets the given attribute to the given integer value. */
	public void set(String attribute, int value) {
		attributeMap.put(attribute.toLowerCase(), value);
		if(GameModel.MAIN!=null && GameModel.MAIN.player!=null)
			GameModel.MAIN.player.updateStatDependencies();
	}
	/** Increases the given attribute by the given integer value. */
	public void inc(String attribute, int value) {
		set(attribute.toLowerCase(), get(attribute)+value);
	}
}
