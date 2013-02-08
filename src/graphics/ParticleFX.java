package graphics;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;

public class ParticleFX implements BattleFX {
	
	ParticleSystem ps;

	public ParticleFX() {
		ps.addEmitter(new FireEmitter());
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean complete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
