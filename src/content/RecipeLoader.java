package content;

import crafting.RecipeFactory;

/** This class loads all the crafting recipes. */
public class RecipeLoader {
	private static String loadingItemName;
	
	public static void loadAll() {
		
		String ss[];
		
		/* ------------------------ Raw ingredients ------------------------*/
		
		// Steam Upgrading
		
		x("cirrus steam");
		o("Steam", 5);
		x("nacreous steam");
		o("cirrus steam", 5);
		
		// Metal Upgrading (alchemy)
		
		x("Platinum");
		o("scrap metal", 5);
		x("Iridium");
		o("Platinum", 5);
		
		/* --------------------------- Potions -----------------------------*/
		
		// Potions (they heal you)
		
		x("Health Potion");
		o("bottle");
		o("steam");
		
		x("Greater Health Potion");
		o("bottle");
		o("steam", 3);
		
		x("Super Health Potion");
		o("big bottle");
		o("cirrus steam");
		
		x("Ultra Health Potion");
		o("big bottle");
		o("cirrus steam", 3);
		
		x("Full Health Potion");
		o("huge bottle");
		o("nacreous steam");
		
		// Elixirs (they give you permanent stat boosts)
		
		for(String s: new String[] {"Vitality", "Wisdom", "Speed", "Power", "Protection"}) {
			x("Elixir of "+s);
			o("vial");
			o("steam", 2);
			x("Strong Elixir of "+s);
			o("big vial");
			o("cirrus steam", 2);
			x("Potent Elixir of "+s);
			o("huge vial");
			o("nacreous steam", 2);
			
		}
		
		
		// Draughts (they give you temporary buffs)
		
		
		
		/* --------------------------- Charms -----------------------------*/
		
		ss = new String[] {"Aerobics", "Agility", "Chilling Winds", "Energy", "Fire Wall",
				"Mobility", "Potency", "Resistance", "Steadiness", "Weaponry"};
		for(String s: ss) {
			x("Charm of "+s);
			o("scrap metal");
			o("steam", 3);
			x("Ancient Charm of "+s);
			o("platinum");
			o("cirrus steam", 2);
			x("Legendary Charm of "+s);
			o("iridium");
			o("nacreous steam");
		}
		
		
		
		/* --------------------------- Jewels -----------------------------*/
		
		
		// Fire abilities
		
		ss = new String[] {"Ruby of Fireballs", "Ruby of Rage", 
				"Ruby of Burning", "Ruby of Samurai"};
		for(String s: ss) {
			x(s);
	    	o("ruby");
	    	o("steam", 7);
	    	x("Lucid "+s);
	    	o(s);
	    	o("cirrus steam", 5);
	    	x("Flawless "+s);
	    	o("lucid "+s);
	    	o("nacreous steam", 3);
		}
    	x("Flawless Ruby of the Storm");
    	o("ruby");
    	o("nacreous steam", 5);
    	o("unwritten steam", 1);
		
		// Water abilities
		
    	ss = new String[] {"Aquamarine of Streams", "Aquamarine of Needles", 
    			"Aquamarine of Hail", "Aquamarine of Warding"};
		for(String s: ss) {
			x(s);
	    	o("aquamarine");
	    	o("steam", 7);
	    	x("Lucid "+s);
	    	o(s);
	    	o("cirrus steam", 5);
	    	x("Flawless "+s);
	    	o("lucid "+s);
	    	o("nacreous steam", 3);
		}
    	x("Flawless Aquamarine of the Deep");
    	o("aquamarine");
    	o("nacreous steam", 5);
    	o("unwritten steam", 1);
		
		
		// Wind abilities
		
    	ss = new String[] {"Quartz of Repel", "Quartz of Lightning", 
    			"Quartz of Propulsion", "Quartz of Seeking"};
		for(String s: ss) {
			x(s);
	    	o("quartz");
	    	o("steam", 7);
	    	x("Lucid "+s);
	    	o(s);
	    	o("cirrus steam", 5);
	    	x("Flawless "+s);
	    	o("lucid "+s);
	    	o("nacreous steam", 3);
		}
    	x("Flawless Quartz of the Gust");
    	o("quartz");
    	o("nacreous steam", 5);
    	o("unwritten steam", 1);
		
		
	}
	
	private static void x(String itemName) {
		loadingItemName = itemName;
	}
	private static void o(String reagentName, int reagentAmount) {
		RecipeFactory.addReagent(loadingItemName, reagentName, reagentAmount);
	}
	private static void o(String reagentName) {
		RecipeFactory.addReagent(loadingItemName, reagentName, 1);
	}
	
	/** Cannot be instantiated. */
	private RecipeLoader() {}
}
