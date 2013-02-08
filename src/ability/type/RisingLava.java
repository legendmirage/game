package ability.type;

import ability.effect.Lava;
import battle.BattleConstants;
import battle.BattleCreature;
import battle.BattleModel;

/** 
 * Ability which gives rise to lava from the bottom of the screen to the user's current y position, <br>
 * heavily damaging enemies touching the lava <br>
 * arg 0 : Speed of the rising lava <br>
 * arg 1 : damage per second <br>
 * arg 2 : duration of the lava <br>
 */
public class RisingLava implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		float bottomOfScreen = bm.map.getHeight()*BattleConstants.TILE_HEIGHT;
		float leftOfScreen = 0;
		float width = bm.map.getWidth()*BattleConstants.TILE_WIDTH;
		Lava lava = new Lava(cr.id, (int)a1, (int)a2, leftOfScreen, bottomOfScreen,
				width, cr.getScreenY()+cr.getHeight()-5, a0);
		bm.addAbilityEffect(lava);
	}

	@Override
	public String getAbilityName() {
		return "Summon lava";
	}
}
