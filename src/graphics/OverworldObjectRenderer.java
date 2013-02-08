package graphics;


import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import overworld.OverworldConstants;
import overworld.OverworldObject;

public class OverworldObjectRenderer implements RenderComponent {

	/** Bringing OverworldConstants down into local space */
	private final static int TILE_SIZE = OverworldConstants.TILE_HEIGHT; 
	private final static int WALK_SPEED = 150;

	private final OverworldObject entity;
	private SpriteSheet ss;
	private Animation walk_u, walk_d, walk_l, walk_r;
	
	public OverworldObjectRenderer(OverworldObject entity, SpriteSheet spriteSheet) {
		this.entity = entity;
		
		int[] walkSpeedArr = new int[]{WALK_SPEED, WALK_SPEED, WALK_SPEED};
		this.ss = spriteSheet;

		// all sprite sheets are down, left, right, up
		walk_d = new Animation(ss, new int[]{0,0, 1,0, 2,0}, walkSpeedArr);
		walk_l = new Animation(ss, new int[]{0,1, 1,1, 2,1}, walkSpeedArr);
		walk_r = new Animation(ss, new int[]{0,2, 1,2, 2,2}, walkSpeedArr);
		walk_u = new Animation(ss, new int[]{0,3, 1,3, 2,3}, walkSpeedArr);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		
		Animation ani = null;
		if(entity.facingX>0) ani = walk_r;
		if(entity.facingX<0) ani = walk_l;
		if(entity.facingY>0) ani = walk_d;
		if(entity.facingY<0) ani = walk_u;
	
		if(entity.moving) ani.start();
		else ani.stop();
		
		ani.draw(entity.getContinuousXLoc()*TILE_SIZE, entity.getContinuousYLoc()*TILE_SIZE);
	}
	
}
