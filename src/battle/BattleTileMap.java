package battle;
import graphics.BattleBackgroundRenderer;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import overworld.OverworldConstants;
import util.GameConstants;
import util.Logger;

/** Contains terrain data for a battle map, as well as utilities for using it. <br> 
 */
public class BattleTileMap {
	
	private BattleBackgroundRenderer background;
	private HashMap<Integer, Rectangle> spawns;
	private TiledMap map;
	public BattleTileMap(TiledMap _map){
		map = _map; 
		background = null;
		populateSpawns();
	}
	
	
	
	public void setBackground(int zone){
		background = new BattleBackgroundRenderer(this, zone);
		}
	
	/** Returns the number of tiles of the width of the map. */
	public int getWidth() {
		return map.getWidth();
	}
	/** Returns the number of tiles of the height of the map. */
	public int getHeight() {
		return map.getHeight();
	}
	
	
	/** Returns the image of the tile of the tile map with the given indices. */
	public Image getImage(int x, int y) {
		return null;
	}
	
	
	/** Draws layers from bottom up */
	public void drawLayers(Graphics g, int x, int y){
		//draws Images from bottom up
		if ((x<0)||(x>map.getWidth()-1)||(y<0)||(y>map.getHeight()-1)){}
		else{
			for (int i = 0; i<= map.getLayerCount()-1; i++){
				if (map.getTileImage(x, y, i)!=null){ 
					g.drawImage(map.getTileImage(x, y, i), x*OverworldConstants.TILE_WIDTH, y*OverworldConstants.TILE_HEIGHT); 
				}
			}
		}
	}
	

	/** Returns the tile of the tile map with the given indices. */
	public BattleTileType getTile(int x, int y){
		//TODO: For now assumes that it only matches one type, but this might not be true...
		//Defaults to SPACE
		int layerID;
		if ((x<0)||(x>=map.getWidth())||(y>=map.getHeight())) return BattleTileType.WALL;
		if(y<0) return BattleTileType.SPACE;
		
		for (BattleTileType tile: BattleTileType.values()){
			layerID = map.getLayerIndex(tile.toString());
			if (layerID==-1) continue;
			if ((map.getTileId(x, y, layerID))!=0) return tile;
		}
		return BattleTileType.SPACE;
	}
	
	
	/** Populates the spawn arraylist */
	public void populateSpawns(){
		int layerID = 0; // I really want this to come from map.getLayerID, but it wasn't working?
		Rectangle tempRect;
		spawns = new HashMap<Integer, Rectangle>();
		for (int i = 0; i<map.getObjectCount(layerID); i++){
			//** why rectangle with width and height? does it matter?
			tempRect = new Rectangle(map.getObjectX(layerID, i)/BattleConstants.TILE_WIDTH,map.getObjectY(layerID, i)/BattleConstants.TILE_HEIGHT,
					map.getObjectWidth(layerID, i)/BattleConstants.TILE_WIDTH,map.getObjectHeight(layerID, i)/BattleConstants.TILE_HEIGHT);
			spawns.put(Integer.parseInt(map.getObjectProperty(layerID, i, "spawnID", "-1")),tempRect);
		}
	}
	/** Returns the spawn zone location(?) of the given spawn zone ID, or null if it does not exist. */
	public Rectangle getSpawn(int id) {
		if(!spawns.containsKey(id)) {
			Logger.log("Failed at finding spawn point " + id);
			return null;
		}
		return spawns.get(id);
	}


	/** Returns the tile at the given pixel coordinates. */
	public BattleTileType getTileAtCoord(float x, float y) {
		return getTile((int)Math.floor(x/BattleConstants.TILE_WIDTH), (int)Math.floor(y/BattleConstants.TILE_HEIGHT));
	}

	/** Renders the map at a given camera cx and cy position */
	public void render(GameContainer gc, Graphics g, float cx, float cy) {
		// render background layer
		g.resetTransform();
		background.render(gc, g, cx, cy);
		g.translate(GameConstants.SCREEN_WIDTH/2 - cx, 
				GameConstants.SCREEN_HEIGHT/2 - cy);
		// render platform layer
		int dx = GameConstants.SCREEN_WIDTH / BattleConstants.TILE_WIDTH + 2;
		int ex = (int)cx/BattleConstants.TILE_WIDTH;
		int dy = GameConstants.SCREEN_HEIGHT / BattleConstants.TILE_HEIGHT + 2;
		int ey = (int)cy/BattleConstants.TILE_HEIGHT;
		for(int x=ex-dx; x<ex+dx; x++) for(int y=ey-dy; y<ey+dy; y++) {
			this.drawLayers(g,x,y);
		}
	}
	

	/** Tests collision against the wall terrain of this tile map.
	 * 
	 * @param x x coordinate of upper left pixel
	 * @param y y coordinate of upper left pixel
	 * @param w width
	 * @param h height
	 * @return true if the given rectangle intersects with any wall area of the tile map.
	 */
	public boolean collideRectWall(float x, float y, float w, float h) {
		if(x<0 || x>=getWidth()*BattleConstants.TILE_WIDTH) return true;
		int a1 = (int)Math.floor(x/BattleConstants.TILE_WIDTH);
		int a2 = (int)Math.ceil((x+w)/BattleConstants.TILE_WIDTH);
		int b1 = (int)Math.floor(y/BattleConstants.TILE_HEIGHT);
		int b2 = (int)Math.ceil((y+h)/BattleConstants.TILE_HEIGHT);
		for(int a=a1; a<a2; a++) 
			for(int b=b1; b<b2; b++)
				if(getTile(a,b)==BattleTileType.WALL) {
					return true;
				}
		return false;
	}
	public boolean collideRectWall(BattleCreature cr) {
		return collideRectWall(cr.getScreenX(), cr.getScreenY(), cr.getWidth(), cr.getHeight());
	}
	/** Returns the y value of where an object with the given shape should land on the terrain. */
	public float getLandingHeightForRect(float x, float y, float w, float h) {
		int a1 = (int)Math.floor(x/BattleConstants.TILE_WIDTH);
		int a2 = (int)Math.ceil((x+w)/BattleConstants.TILE_WIDTH);
		int b1 = (int)(Math.ceil((y+h)/BattleConstants.TILE_HEIGHT));
		int b2 = getHeight()+4;
		for(int b=b1; b<b2; b++) {
			for(int a=a1; a<a2; a++) {
				if(getTile(a,b)!=BattleTileType.SPACE) {
					return b*BattleConstants.TILE_HEIGHT;
				}
			}
		}
		return Float.MAX_VALUE;
	}
	/** Collides the rectangle against the tops of all platforms as well as walls. */
	public boolean collideRectPlatformAndWall(float x, float y, float w, float h) {
		if(collideRectWall(x,y,w,h)) return true;
		int a1 = (int)Math.floor(x/BattleConstants.TILE_WIDTH);
		int a2 = (int)Math.ceil((x+w)/BattleConstants.TILE_WIDTH);
		int b1 = (int)Math.ceil(y/BattleConstants.TILE_HEIGHT);
		int b2 = (int)Math.ceil((y+h)/BattleConstants.TILE_HEIGHT);
		for(int a=a1; a<a2; a++) 
			for(int b=b1; b<b2; b++)
				if(getTile(a,b)==BattleTileType.PLATFORM) {
					return true;
				}
		return false;
	}
}
