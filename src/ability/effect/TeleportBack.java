package ability.effect;

import model.GameModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import battle.BattleCreature;
import battle.BattleModel;

public class TeleportBack extends AbilityEffect {

	private int duration;
	private boolean facingRight;
	private int airJumpCounter;
	private boolean faceRight;
	public TeleportBack(int creatureID, float px, float py, int duration, boolean faceRight) {
		super(creatureID, px, py);
		this.duration = duration;
		BattleCreature cr = BattleModel.MAIN.creatures.get(creatureID);
		this.facingRight = cr.facingRight;
		this.airJumpCounter = cr.airJumpCounter;
		this.faceRight = faceRight;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
	}

	@Override
	public void update(BattleModel bm) {
		BattleCreature cr = bm.creatures.get(creatureID);
		cr.facingRight = faceRight;
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			cr.setScreenX(px);
			cr.setScreenY(py);
			cr.facingRight = facingRight;
			cr.airJumpCounter = airJumpCounter;
			
			alive = false;
			return;
		}
	}

}
