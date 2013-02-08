package item.use;

import model.Player;

/** Heals the player a fixed amount of HP. */
public class Heal implements ItemUse {

	private int amount;
	public String effectName;
	public Heal(int amount) {
		this.amount = amount;
		this.effectName = "Heal by " + amount;
	}
	
	@Override
	public void use(Player pl) {
		pl.hp += amount;
		if(pl.hp>pl.maxHP)
			pl.hp = pl.maxHP;
	}

	@Override
	public String getEffectName() {
		return effectName;
	}

}
