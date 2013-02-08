package item.use;

import model.Player;

public class PermanentAttributeIncrease implements ItemUse {
	
	private String attribute;
	private int amount;
	public String effectName;
	
	public PermanentAttributeIncrease(String attribute, int amount) {
		this.effectName = "Increase " + attribute + " permanently";
		this.attribute = attribute;
		this.amount = amount;
	}
	
	@Override
	public void use(Player pl) {
		pl.stats.inc(attribute, amount);
	}

	@Override
	public String getEffectName() {
		return effectName;
	}

}
