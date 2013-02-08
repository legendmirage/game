package battle;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

/**
 * A class to specify a single hitbox with offset from top left of sprite
 */
public class HitBox {
	/** The X offset from the top left of the creature sprite */
	public float x;
	/** The Y offset from the top left of the creature sprite */
	public float y;
	/** The width of the Hit Box */
	public float width;
	/** The height of the Hit Box */
	public float height;
	/** Is this hitbox collidable??*/
	public boolean collidable;
	/** The damage multiple of the hitbox */
	public float damageMultiplier;
	/** The creature this hit box belongs to */
	public BattleCreature cr;
	
	/**
	 * @param x - The X offset from the top left of the creature sprite
	 * @param y - The Y offset from the top left of the creature sprite
	 * @param width - The width of the hit box
	 * @param height - The height of the hit box
	 * @param collidable - Whether the hitbox can collide
	 * @param dmgMultiplier - How much damage should it take.
	 */
	public HitBox(float x, float y, float width, float height, boolean collidable, float dmgMultiplier) {
		this.cr = null;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.collidable = collidable;
		this.damageMultiplier = dmgMultiplier;
	}
	/**
	 * @param cr - The creature the hitbox belongs to
	 * @param x - The X offset from the top left of the creature sprite
	 * @param y - The Y offset from the top left of the creature sprite
	 * @param width - The width of the hit box
	 * @param height - The height of the hit box
	 * @param collidable - Whether the hitbox can take damage
	 * @param dmgMultiplier - How much damage should it take.
	 */
	public HitBox(BattleCreature cr, float x, float y, float width, float height, boolean collidable, float dmgMultiplier) {
		this.cr = cr;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.collidable = collidable;
		this.damageMultiplier = dmgMultiplier;
	}
	
	/**
	 * Method to set the creature for this hitbox
	 * @param cr - The creature to whom the hitbox belongs to.
	 */
	public void setCreature (BattleCreature cr) {
		this.cr = cr;
	}
	
	/**
	 * Method to return the screen X coordinate of the hit box
	 * @return
	 */
	public float getScreenX() {
		return cr.getPX()+x;
	}
	
	/**
	 * Method to return the screen Y coordinate of the hit box
	 * @return
	 */
	public float getScreenY() {
		return cr.getPY()+y;
	}
	
	/**
	 * Method to return if the hitbox is collidable
	 * @return
	 */
	public boolean isCollidable() {
		return collidable;
	}
	
	/**
	 * Method to return the rectangular hit box on screen
	 * @return - A Rectangle instance of the hitbox
	 */
	public Rectangle getScreenRectangle () {
		return new Rectangle(getScreenX(), getScreenY(), width, height);
	}
	
	/**
	 * Method to check if a point is in the hit box
	 * @param x1 - X coordinate of the point
	 * @param y1 - Y coordinate of the point
	 * @return - A boolean of the result
	 */
	public boolean pointInHitBox(float x1, float y1) {
		boolean collide;
		Rectangle hb = this.getScreenRectangle();
		if (hb.contains(x1, y1)) {
			collide = true;
		} else {
			collide = false;
		}
		return collide;
	}
	
	/**
	 * Method to check if the line intersects with the hitbox
	 * @param x1 - X coordinate of the starting point of the line
	 * @param y1 - Y coordinate of the starting point of the line
	 * @param x2 - X coordinate of the ending point of the line
	 * @param y2 - Y coordinate of the ending point of the line
	 * @return
	 */
	public boolean lineInHitBox(float x1, float y1, float x2, float y2) {
		boolean collide;
		Line l = new Line(x1, y1, x2, y2);
		Rectangle hb = this.getScreenRectangle();
		if (hb.intersects(l)) {
			collide = true;
		} else {
			collide = false;
		}
		return collide;
	}
	
	/**
	 * Method to check if circle collides with hit box
	 * @param x1 - X coordinate of center of circle
	 * @param y1 - Y coordinate of center of circle
	 * @param radius - Radius of the circle
	 * @return
	 */
	public boolean circleInHitBox (float x1, float y1, float radius) {
		boolean collide;
		Circle c = new Circle(x1, y1, radius);
		Rectangle hb = this.getScreenRectangle();
		if (hb.intersects(c)) {
			collide = true;
		} else {
			collide = false;
		}
		return collide;
	}
	
	/**
	 * Method to check collision of rectangle with hit box
	 * @param x1 - X coordinate of top left of rectangle
	 * @param y1 - Y coordinate of top left of rectangle
	 * @param w - width of the rectangle
	 * @param h - height of the rectangle
	 * @return
	 */
	public boolean rectangleInHitBox (float x1, float y1, float w, float h) {
		boolean collide;
		Rectangle r = new Rectangle(x1, y1, w, h);
		if (w<0) {
			r.setX(x1-Math.abs(w));
			r.setWidth(Math.abs(w));
		}
		if (h<0) {
			r.setY(y1-Math.abs(h));
			r.setHeight(Math.abs(h));
		}
		Rectangle hb  = this.getScreenRectangle();
		if (hb.intersects(r) || r.intersects(hb)) {
			collide = true;
		} else {
			collide = false;
		}
		return collide;
	}
	
	/**
	 * Method to see if the hit box collides with a X range
	 * @param x1
	 * @param range
	 * @return
	 */
	public boolean xRegionInHitBox (float x1, float range) {
		boolean collide;
		Rectangle hb  = this.getScreenRectangle();
		Line l = new Line(x1, getScreenY(), range, 0, true);
		if (hb.intersects(l)) {
			collide = true;
		} else {
			collide = false;
		}
		return collide;
	}
	
	/**
	 * Method to calculate the distance from the top left of hit box to a point (x1,y1)
	 * @param x1 - X coordinate of the point
	 * @param y1 - Y coordinate of the point
	 * @return
	 */
	public float distanceFromHitBox (float x1, float y1) {
		float dist = 0;
		dist = (float) Math.sqrt((x1-this.getScreenX())*(x1-this.getScreenX())+(y1-this.getScreenY())*(y1-this.getScreenY()));
		return dist;
	}
	
	@Override
	public String toString() {
		String hb = "";
		hb = "X:"+x+"Y:"+y+"Width:"+width+"Height:"+height;
		return hb;
	}
}
