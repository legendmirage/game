package ability.effect;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.Pair;
import util.U;
import battle.BattleCreature;
import battle.BattleModel;

public class DamageTouchingEnemies extends AbilityEffect {

	private int dps;
	private int duration;
	public DamageTouchingEnemies(int creatureID, int dps, int duration) {
		super(creatureID);
		this.dps = dps;
		this.duration = duration;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		BattleCreature cr = BattleModel.MAIN.creatures.get(creatureID);
		if(cr==null || !cr.alive) return;
		g.setColor(new Color(0x22FF0000));
		g.fillRect(cr.getScreenX(), cr.getScreenY(), cr.getWidth(), cr.getHeight());
	}

	@Override
	public void update(BattleModel bm) {
		BattleCreature cr = bm.creatures.get(creatureID);
		for (Pair<Integer, Float> p : bm.collideRectangle(cr.getScreenX(), cr.getScreenY(), cr.getWidth(), cr.getHeight())) {
			BattleCreature enemy = bm.creatures.get(p.getVal1());
			if (cr.sameTeam(enemy)) continue;
			int a = dps/100;
			int b = dps%100;
			if(U.r()<0.01*b) enemy.takeDamage(a+1);
			else enemy.takeDamage(a);
		}
		
		// Check if the duration is up
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}

}
