package item.use;

import model.Player;

/** An item use effect. <br>
 * For example, if my item was a potion which permanently boosted my strength and vitality by 
 * two points, it would have two ItemUses attached to it, one to boost strength, and one to boost
 * vitality. <br>
 */
public interface ItemUse {
	/** The player uses this item. */
	public void use(Player pl);
	
	/** Get the name of the effect, for the menu */
	public String getEffectName();
}
