package ability.type;

import util.Pair;
import ability.effect.TeleportBack;
import battle.BattleCreature;
import battle.BattleModel;
import battle.ImpulseForce;

/**
 * Teleport to enemy, damage it, and teleport back. High damage. <br>
 * arg 0 : range in pixels <br>
 * arg 1: damage <br>
 */
public class Assassinate implements AbilityType {

	@Override
	public void use(BattleCreature cr, BattleModel bm, float a0, float a1, float a2) {
		
		BattleCreature nearest = null;
		double bestD = Double.MAX_VALUE;
		float range = a0;
		float x = cr.facingRight ? cr.getScreenX()+cr.getWidth() : cr.getScreenX() - range;
		for(Pair<Integer, Float> p: bm.collideRectangle(x, cr.getScreenY(), range, cr.getHeight())) {
			BattleCreature next = bm.creatures.get(p.getVal1());
			if(next.sameTeam(cr)) continue;
			double dx = cr.mx()-next.mx();
			double dy = cr.my()-next.my();
			double d = Math.sqrt(dx*dx+dy*dy);
			if(d<bestD) {
				bestD = d;
				nearest = next;
			}
		}
		if(nearest==null) {
			bm.addAbilityEffect(new TeleportBack(cr.id, cr.getScreenX(), cr.getScreenY(), 45, cr.facingRight));
			cr.setScreenX(cr.facingRight ? cr.getScreenX()+cr.getWidth()+range : cr.getScreenX()-range);
		} else {
			bm.addAbilityEffect(new TeleportBack(cr.id, cr.getScreenX(), cr.getScreenY(), 45, nearest.facingRight));
			cr.setScreenX(nearest.facingRight ? nearest.getScreenX()-cr.getWidth() : nearest.getScreenX()+nearest.getWidth());
			cr.facingRight = nearest.facingRight;
			nearest.takeDamage((int)a1);
		}
		cr.applyImpulse(0, 0, ImpulseForce.ASSASSINATE, 40, 0);
	}

	@Override
	public String getAbilityName() {
		return "Teleport forward, do damage, teleport back";
	}

}
