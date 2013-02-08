package overworld;

import graphics.RenderComponent;
import model.GameModel;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class OverworldObject {
	/** Hidden counter to make sure each instance of this class has a unique id. Start large so players get the low ids. */
	private static int objectCount = 55555;
	/** Unique identifier. Player ids always go from 0, 1, ..., to (number of players)-1. */
	public int id;
	
	/** The zone number of the zone in the world map that the object is on. */
	public int zone;
	/** The x index of the tile in the world map that the object is on. */
	public int x;
	/** The y index of the tile in the world map that the object is on. */
	public int y;
	/** The number of ticks between each tile movement of the object in the world map. */
	public int moveSpeed;
	/** The moveSpeed of the object at the time of his last movement. */
	public int lastMoveMoveSpeed;
	/** All this really stores is the direction of the last movement of the object on the world map. */
	public int facingX;
	/** All this really stores is the direction of the last movement of the object on the world map. */
	public int facingY;
	/** Tick number of the last movement of the object on the world map. */
	public int lastMoveTick;
	
	/** Which way is the object facing on the world */
	public int headingX;
	/** Which way is the object facing on the world map? */
	public int headingY;
	/** Is the object moving on the world map? */
	public boolean moving;
	/** Number of ticks until the object can move again. */
	public int moveCooldown;
	/** The renderer for this overworld object */
	public RenderComponent renderer;
	
	public OverworldObject(int zone, int x, int y) {
		this.id = objectCount++;
		this.zone = zone;
		this.x = x;
		this.y = y;
		this.moveSpeed = OverworldConstants.PLAYER_BASE_MOVE_SPEED;
		this.lastMoveMoveSpeed = moveSpeed;
		this.facingX = 0;
		this.facingY = 1;
		this.lastMoveTick = Integer.MIN_VALUE/2;
		this.headingX = 0;
		this.headingY = 0;
		this.moving = false;
		this.moveCooldown = 0;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof OverworldObject)) return false;
		return id==((OverworldObject)o).id;
	}
	/** The x coordinate of the object on the world map, if the object moved continuously. */
	public float getContinuousXLoc() {
		if(!moving) return x;
		int ticksSinceLastMove = GameModel.MAIN.tickNum-lastMoveTick;
		if(ticksSinceLastMove >= lastMoveMoveSpeed) return x;
		float percentThere = 1.0f*ticksSinceLastMove/lastMoveMoveSpeed;
		percentThere = Math.max(0, Math.min(1.0f, percentThere));
		return x + facingX * (percentThere-1);
	}
	/** The y coordinate of the object on the world map, if the object moved continuously. */
	public float getContinuousYLoc() {
		if(!moving) return y;
		int ticksSinceLastMove = GameModel.MAIN.tickNum-lastMoveTick;
		if(ticksSinceLastMove >= lastMoveMoveSpeed) return y;
		float percentThere = 1.0f*ticksSinceLastMove/lastMoveMoveSpeed;
		percentThere = Math.max(0, Math.min(1.0f, percentThere));
		return y + facingY * (percentThere-1);
	}
	
	/** Sets the object's state to move in the world map in the given direction. Does not actually move the object. */
	public void startMoving(int dx, int dy) {
		headingX = dx;
		headingY = dy;
	}
	/** Sets the object's state to stay still and not move in any direction. */
	public void stopMoving() {
		headingX = 0;
		headingY = 0;
	}
	/** Makes the object actually move one tile in the direction its heading, if it can. */
	public void move(OverworldTileMap map) {
		if(moving) return;
		if(headingX==0 && headingY==0) return;
		if(!map.getTile(x+headingX, y+headingY).isTraversible()) {
			facingX = headingX;
			facingY = headingY;
			return;
		}
		moving = true;
		facingX = headingX;
		facingY = headingY;
		x += facingX;
		y += facingY;
		lastMoveTick = GameModel.MAIN.tickNum;
		lastMoveMoveSpeed = moveSpeed;
		moveCooldown = moveSpeed;
	}
	
	public void render(GameContainer gc, Graphics g) {
		if(renderer != null) {
			renderer.render(gc, g);
			return;
		}
		
		float px = getContinuousXLoc();
		float py = getContinuousYLoc();
		
		// some hard-coded test values
		g.setColor(id==0 ? Color.red : id==1 ? Color.blue : Color.magenta);
		g.fillOval(px * OverworldConstants.TILE_WIDTH, py * OverworldConstants.TILE_HEIGHT, 
				OverworldConstants.TILE_WIDTH, OverworldConstants.TILE_HEIGHT);
		g.setColor(Color.green);
		g.drawLine((px+0.5f) * OverworldConstants.TILE_WIDTH, (py+0.5f) * OverworldConstants.TILE_HEIGHT, 
				((px+0.5f*(1+facingX)) * OverworldConstants.TILE_WIDTH), 
				((py+0.5f*(1+facingY)) * OverworldConstants.TILE_HEIGHT));
	}
	
	/**
	 * Checks if the object is facing right
	 * @return - a boolean value
	 */
	public boolean isFacingRight() {
		return facingX>0? true : false;
	}
	
	/**
	 * Checks if the object is facing down
	 * @return - a boolean value
	 */
	public boolean isFacingDown() {
		return facingY>0? true : false;
	}
	
	/**
	 * Checks if the object is facing right
	 * @return - a boolean value
	 */
	public boolean isFacingLeft() {
		return facingX<0? true : false;
	}
	
	/**
	 * Checks if the object is facing down
	 * @return - a boolean value
	 */
	public boolean isFacingUp() {
		return facingY<0? true : false;
	}
	
	public void update(ZoneModel zone) {
		if(moveCooldown==0) {
			move(zone.map);
		}
		if(moveCooldown>0) 
			moveCooldown--;
		if(moveCooldown==0) {
			moving = false;
		}
	}
}
