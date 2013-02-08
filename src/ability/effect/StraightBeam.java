package ability.effect;

import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import util.Pair;
import util.U;
import battle.BattleCreature;
import battle.BattleModel;

/** A straight beam originating from the creature and going outward in a given direction. <br>
 * Does damage to things that it intersects with. <br>
 */
public class StraightBeam extends AbilityEffect {
	public float length;
	public float dx;
	public int dps;
	public boolean firstUpdated;
	public int duration;
	
	public StraightBeam(int creatureID, float length, 
			int dps, int duration) {
		super(creatureID);
		
		this.length = length;
		this.dps = dps;
		this.firstUpdated = false;
		this.duration = duration;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		if(!firstUpdated) return;
		float oldLineWidth = g.getLineWidth();
		g.setColor(Color.blue);
		g.setLineWidth(10);
		g.drawLine(px, py, px+dx, py);
		g.setLineWidth(oldLineWidth);
	}
	
	@Override
	public void update(BattleModel bm) {
		firstUpdated = true;
		BattleCreature owner = bm.creatures.get(creatureID);
		px = owner.facingRight ? owner.getScreenX()+owner.getWidth() : owner.getScreenX();
		py = owner.my();
		dx = owner.facingRight ? length : -length;
		
		// Check if any creatures are hit
		for(Pair<Integer, Float> p: bm.collideLine(px, py, px+dx, py)) { 
			BattleCreature cr = bm.creatures.get(p.getVal1());
			if (owner.sameTeam(cr)) continue;
			if (p.getVal2()<=0) continue;
			int a = dps/100;
			int b = dps%100;
			if(U.r()<0.01*b) cr.takeDamage(a+1);
			else cr.takeDamage(a);
		}
		
		// Check if the duration is up
		if(GameModel.MAIN.tickNum > tickCreated + duration) {
			alive = false;
			return;
		}
	}
}