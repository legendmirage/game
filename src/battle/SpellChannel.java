package battle;

import model.GameModel;


/** Represents spell casting delay on a battle creature. <br>
 */
public class SpellChannel {

	/** The ID of the creature that is being affected */
	public final int creatureID;
	/** The ability to cast */
	public final BattleAbility ab;
	/** Tick at which the channeling was started */
	private int tickStart;
	
	/** Generates a spell channeling object w/ given duration for given ability */
	public SpellChannel(int creatureID, BattleAbility toCast, int tickStart) {
		this.creatureID = creatureID;
		this.ab = toCast;
		this.tickStart = tickStart;
	}

	/** Is the ability currently being channeled? */
	public boolean isChanneling() {
		return GameModel.MAIN.tickNum < tickStart + ab.type.channelDelay;
	}

	/** Is the creature experiencing post casting stun? */
	public boolean isStunned() {
		return GameModel.MAIN.tickNum < tickStart + ab.type.stunDelay;
	}

	/** Applies the effects of the current SpellChannel on the model */
	public void castAbility() {
		BattleCreature cr = BattleModel.MAIN.creatures.get(creatureID);
		ab.type.type.use(cr, BattleModel.MAIN, ab.type.arg0, ab.type.arg1, ab.type.arg2);
		ab.tickLastUsed = GameModel.MAIN.tickNum;
		ab.useCount++;
		cr.spellChannel = null;
	}

	
	/** interrupts the currently casting spell */
	public void interrupt() {
		if(ab.type.interruptable) {
			BattleCreature cr = BattleModel.MAIN.creatures.get(creatureID);
			cr.spellChannel = null;
		}
	}
}
