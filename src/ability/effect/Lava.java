package ability.effect;


import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.GameConstants;
import util.Pair;
import util.U;
import battle.BattleCreature;
import battle.BattleModel;

/** Gets lava to rise from the ground and affect all opposing team members */
public class Lava extends AbilityEffect {
	/** Damage per second */
	public int dps;
	public boolean firstUpdated;
	/** Total duration of Lava */
	public int duration;
	/** Y coordinate of the bottom of the screen */
	public float bottomOfScreen;
	/** X coordinate of the left of the screen */
	public float leftOfScreen;
	/** The Y coordinate at which the lava should stop rising */
	public float topOfBottomLayer;
	/** Width of the map */
	public float width;
	/** Height of the lava layer. Increases continuously till floor is reached */
	public float height;
	/** The speed of the lava coming up */
	public float speed;
	
	/**
	 * @param creatureID - ID of the creature casting this ability
	 * @param dps - Damage per second
	 * @param duration - Duration in ticks of the lava once it is at the surface
	 * @param leftOfScreen - X coordinate of the left of the screen 
	 * @param bottomOfScreen - Y coordinate of the bottom of the screen
	 * @param width - Width of the map
	 * @param topOfBottomLayer - The Y coordinate at which the lava should stop rising
	 * @param speed - Rising speed of the lava
	 */
	public Lava(int creatureID, int dps, int duration, float leftOfScreen, float bottomOfScreen, 
			float width, float topOfBottomLayer, float speed) {
		super(creatureID, leftOfScreen, bottomOfScreen);
		this.dps = dps;
		this.duration = duration;
		this.firstUpdated = false;
		this.leftOfScreen = leftOfScreen;
		this.bottomOfScreen = bottomOfScreen;
		this.width = width;
		this.speed = speed;
		this.topOfBottomLayer = topOfBottomLayer;
		this.height = 0;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if(!firstUpdated) return;
		g.setColor(Color.red);
		g.fillRect(leftOfScreen, py, width, height+300);
	}

	@Override
	public void update(BattleModel bm) {
		firstUpdated = true;
		if (py>topOfBottomLayer) {
			float dt = GameConstants.GAME_SPEED/1000.0f;
			tickCreated = GameModel.MAIN.tickNum;
			py -= speed * dt;
			height = (int) (bottomOfScreen-py);
		}
		BattleCreature owner = bm.creatures.get(creatureID);
		for(Pair<Integer, Float> p : bm.collideRectangle(leftOfScreen, py, width, height)) {
			BattleCreature cr = bm.creatures.get(p.getVal1());
			if (owner.sameTeam(cr)) continue;
			if (p.getVal2()<=0) continue;
			if(U.r()<0.01) cr.takeDamage(dps);
		}
		
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}
}
