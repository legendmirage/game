package ability.effect;

import util.GameConstants;
import battle.BattleCreature;
import battle.BattleModel;

public class HomingProjectile extends Projectile {

	private int origDamage;
	private int turnsElapsed;
	public HomingProjectile(int creatureID, float px, float py, float vx,
			float vy, int damage, int duration) {
		super(creatureID, px, py, vx, vy, damage, duration);
		this.origDamage = damage;
		this.turnsElapsed = 0;
	}
	
	@Override
	public void update(BattleModel bm) {
		damage = origDamage * (duration-turnsElapsed) / duration;
		turnsElapsed++;
		
		if(turnsElapsed<100) {
			float dt = GameConstants.GAME_SPEED/1000.0f;
			px += vx * dt;
			py += vy * dt;
			return;
		}
		
		BattleCreature owner = bm.creatures.get(creatureID);
		BattleCreature nearest = null;
		double bestD = Double.MAX_VALUE;
		for(BattleCreature next: bm.creatures.values()) {
			if(!next.alive || next.sameTeam(owner)) continue;
			double dx = px-next.mx();
			double dy = py-next.my();
			double d = Math.sqrt(dx*dx+dy*dy);
			if(d<bestD) {
				bestD = d;
				nearest = next;
			}
		}
		
		if(nearest!=null) {
			double dx = nearest.mx()-px;
			double dy = nearest.my()-py;
			double projSpeed = Math.sqrt(vx*vx+vy*vy);
			double angleA = Math.atan2(dy, dx);
			double angleB = Math.atan2(vy, vx);
			double angleToRotate = angleA-angleB;
			if(angleToRotate>Math.PI) angleToRotate-=2*Math.PI;
			if(angleToRotate<-Math.PI) angleToRotate+=2*Math.PI;
			double maxRotatePerTick = 0.01f;
			if(Math.abs(angleToRotate)>maxRotatePerTick) 
				angleToRotate = Math.signum(angleToRotate)*maxRotatePerTick;
			double finalAngle = angleToRotate+angleB;
			vx = (float)(Math.cos(finalAngle)*projSpeed);
			vy = (float)(Math.sin(finalAngle)*projSpeed);
		}
		
		
		if(damage<=0) 
			alive = false;
		else
			super.update(bm);
	}

}
