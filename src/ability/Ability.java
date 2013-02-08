package ability;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import ability.type.AbilityType;


/** This is a description of an ability. <br>
 * It does not contain data about a player's use of that ability. <br>
 * This should only be created and modified through AbilityFactory. <br>
 * 
 */
public class Ability {
	
	/** Unique identifier. Name of the ability. */
	public String name;
	/** The number of action points it costs to use this ability in battle. */
	public int apCost;
	/** The number of ticks after using this ability before you can use it again. */
	public int cooldown;
	/** The list of components that trigger an effect when this ability is used in battle. */
	public AbilityType type;
	
	/** The arguments that are fed into the AbilityType.use() function. 
	 * Depending on ability type, this will dictate the damage, range, speed, etc. 
	 * of the ability, but not its big picture effect.
	 */
	public float arg0,arg1,arg2;
	
	/** The time in ticks between when a creature does the action and 
	 * when the action's ability effect is actually triggered. <br>
	 * During this time the creature cannot use any other non-movement abilities. <br>
	 */
	public int channelDelay;
	/** The time in ticks after when a creature starts using the ability before
	 * he can use movement actions. <br> These include moving left/right and jumping.
	 */
	public int stunDelay;
	/** Is the ability interruptable by damage during channeling phase? */
	public boolean interruptable;
	
	/** The image associated with this abilityType */
	public Image image;
	
	/** The animation associated with this ability*/
    public String animation ;
    
	
	public Ability(String name, int apCost, int cooldown) {
		this.name = name.toLowerCase();
		this.apCost = apCost;
		this.cooldown = cooldown;
		this.channelDelay = 0;
		this.stunDelay = 0;
		this.interruptable = false;
		this.type = null;
		this.arg0 = -1;
		this.arg1 = -1;
		this.arg2 = -1;
	}
	
	/**
	 * @param type - AbilityType
	 * @param apCost - The AP Cost
	 * @param cooldown - The cooldown of the ability
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param channelDelay - The channel delay in ticks of the ability
	 * @param stunDelay - The time in ticks the creature is stunned
	 * @param interruptable - Is the ability interruptable
	 */
	public Ability(AbilityType type, int apCost, int cooldown, float arg0, float arg1, float arg2, 
			int channelDelay, int stunDelay, boolean interruptable) {
		this.apCost = apCost;
		this.cooldown = cooldown;
		this.channelDelay = channelDelay;
		this.stunDelay = stunDelay;
		this.interruptable = interruptable;
		this.type = type;
		this.arg0 = arg0;
		this.arg1 = arg1;
		this.arg2 = arg2;
        this.animation = "cast";
		try {
			String name = "/res/art/HUD/abilities/";
			if (this.type.getAbilityName().toLowerCase().contains("quartz")){
				name+="lightningGem";
			}
			if (this.type.getAbilityName().toLowerCase().contains("aquamarine")){
				name+="iceGem";
			}
			else{
				name+="fireGem";
			}
			
			if (this.type.getAbilityName().toLowerCase().contains("lucid")){
				name+="1";
			}
			if (this.type.getAbilityName().toLowerCase().contains("flawless")){
				name+="2";
			}
			else{
				name+="0";
			}
			
			name+=".png";
			
			
			this.image = new Image(name);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param type - AbilityType
	 * @param apCost - The AP Cost
	 * @param cooldown - The cooldown of the ability
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param channelDelay - The channel delay in ticks of the ability
	 * @param stunDelay - The time in ticks the creature is stunned
	 * @param interruptable - Is the ability interruptable
	 * @param animation - The animation of the ability
	 */
	public Ability(AbilityType type, int apCost, int cooldown, float arg0, float arg1, float arg2, 
			int channelDelay, int stunDelay, boolean interruptable, String animation) {
		this.apCost = apCost;
		this.cooldown = cooldown;
		this.channelDelay = channelDelay;
		this.stunDelay = stunDelay;
		this.interruptable = interruptable;
		this.type = type;
		this.arg0 = arg0;
		this.arg1 = arg1;
		this.arg2 = arg2;
        this.animation = animation;
		
	}
	
	public void setImage(String element, String level) {
		try {
			String name = "/res/art/HUD/abilities/";
			if (element.toLowerCase().contains("quartz")){
				name+="lightningGem";
			}
			else if (element.toLowerCase().contains("aquamarine")){
				name+="iceGem";
			}
			else{
				name+="fireGem";
			}
			
			
			
			if (level.toLowerCase().contains("lucid")){
				name+="1";
			}
			else if (level.toLowerCase().contains("flawless")){
				name+="2";
			}
			else{
				name+="0";
			}
			
			name+=".png";
			

			this.image = new Image(name);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setParams(float arg0, float arg1, float arg2) {
		this.arg0 = arg0;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	public void setParams(float arg0, float arg1) {
		this.arg0 = arg0;
		this.arg1 = arg1;
	}
	
	public void setParams(float arg0) {
		this.arg0 = arg0;
	}
	
	public void setDelays(int channelDelay, int stunDelay, boolean interruptable) {
		this.channelDelay = channelDelay;
		this.stunDelay = stunDelay;
		this.interruptable = interruptable;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(o == this) return true;
		if(!(o instanceof Ability)) return false;
		Ability other = (Ability)o;
		return this.name.equalsIgnoreCase(other.name);
	}
	
	public boolean equals(String s) {
		return this.name.equals(s);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
