package overworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import model.GameModel;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

import enemy.EnemyFactory;
import enemy.EnemyType;

import util.GameConstants;
import util.Logger;

public class OverworldTileMap {
	public TiledMap map;
	private String name;
	private String musicTitle;
	
	public OverworldTileMap(TiledMap _map, String name, String musicTitle){
		map = _map; //new TiledMap();
		this.name = name;
		this.musicTitle = musicTitle;
	}
	
	public int getWidth(){
		return map.getWidth();
	}
	public int getHeight(){
		return map.getHeight();
	}

	/** Returns the name of the Zone */
	public String getName(){
		return this.name;
	}
	
	/** Returns the music title of Zone */
	public String getMusic(){
		return this.musicTitle;
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
	public OverworldTileType getTile(int x, int y){
		//TODO: For now assumes that it only matches one type, but this might not be true...
		//Also currently defaults to wall if it doesn't know what to do
		int layerID;
		if ((x<0)||(x>map.getWidth()-1)||(y<0)||(y>map.getHeight()-1)) return OverworldTileType.WALL;
		
		for (OverworldTileType tile: OverworldTileType.values()){
			layerID = map.getLayerIndex(tile.toString());
			if ((map.getTileId(x, y, layerID))!=0) return tile;
		}
		return null;
	}
	
	/** Returns an ArrayList of the portals on the map */
	public ArrayList<Portal> getPortals(){
		int layerID = 0; // I really want this to come from map.getLayerIndex, but it wasn't working? ... Neither was map.getLayerProperty for object groups
		
		int x, y, width, height, toZone, toPortal;
		String cutscene, quest, active;
		Portal portal;
		ArrayList<Portal> portalList = new ArrayList<Portal>();
		for (int i = 0; i< map.getObjectCount(layerID); i++){
			x = (int)map.getObjectX(layerID, i)/OverworldConstants.TILE_WIDTH;
			y = (int)map.getObjectY(layerID, i)/OverworldConstants.TILE_HEIGHT;
			width = (int)map.getObjectWidth(layerID, i)/OverworldConstants.TILE_WIDTH;
			height = (int)map.getObjectWidth(layerID,i)/OverworldConstants.TILE_HEIGHT;
			toZone = Integer.parseInt(map.getObjectProperty(layerID, i, "toZone", "-1"));
			toPortal = Integer.parseInt(map.getObjectProperty(layerID, i, "toPortal", "-1"));
			cutscene = map.getObjectProperty(layerID, i, "cutscene", null);
			quest = map.getObjectProperty(layerID, i, "quest", null);
			active = map.getObjectProperty(layerID, i, "active", null);
			portal = new Portal(toZone, toPortal, x, y, width, height, true);
			portal.cutsceneActive = active;
			portal.quest = quest;
			portal.cutscene = cutscene;
			portalList.add(portal);
		}
		return portalList;
	}
	
	public HashMap<String, NPC> getNPCS(){
		int layerID = 1; // I really want this to come from map.getLayerID, but it wasn't working?
		
		int x, y;
		String name;
		NPC npc;
		String quest, scene, scene1, scene2, scene3, recipe;
		HashMap<String, NPC> NPCList = new HashMap<String, NPC>();
		for (int i = 0; i< map.getObjectCount(layerID); i++){
			x = (int)map.getObjectX(layerID, i)/OverworldConstants.TILE_WIDTH;
			y = (int)map.getObjectY(layerID, i)/OverworldConstants.TILE_HEIGHT;
			name = map.getObjectName(layerID, i); 
			//TODO: Have someone who knows this code look at it?
			// believe to be valid, may not be
			npc = new NPC(1, x, y);
			
			quest = map.getObjectProperty(layerID, i, "quest", null);
			scene = map.getObjectProperty(layerID, i, "scene", null);
			scene1 = map.getObjectProperty(layerID, i, "scene1", null);
			scene2 = map.getObjectProperty(layerID, i, "scene2", null);
			scene3 = map.getObjectProperty(layerID, i, "scene3", null);
			recipe = map.getObjectProperty(layerID, i, "recipe", null);
			npc.setQuestName(quest);
			npc.setSceneName(scene);
			npc.setSceneName1(scene1);
			npc.setSceneName2(scene2);
			npc.setSceneName3(scene3);
			npc.setRecipe(recipe);
			NPCList.put(name, npc);

		}
		return NPCList;
	}	
	
	/** Grab the monsters from the tilemap definition. 
	 * @param zoneID the zone ID of the current map, needed just to make the monster groups.
	 */
	public HashMap<Integer, MonsterGroup> loadMonsters(int zoneID){
		int layerID = 2; // I really want this to come from map.getLayerID, but it wasn't working?
		
		HashMap<Integer, MonsterGroup> monsters = new HashMap<Integer, MonsterGroup>();
		
		for (int i = 0; i< map.getObjectCount(layerID); i++){
			int x = (int)map.getObjectX(layerID, i)/OverworldConstants.TILE_WIDTH;
			int y = (int)map.getObjectY(layerID, i)/OverworldConstants.TILE_HEIGHT;

			int battleMapID = Integer.parseInt(map.getObjectProperty(layerID, i, "battleMapID", "-1"));
			int movement = Integer.parseInt(map.getObjectProperty(layerID, i, "level", "1"));
			String monsterCSV = map.getObjectProperty(layerID, i, "monsters", "");
			
			ArrayList<MonsterSpec> types = new ArrayList<MonsterSpec>();
			for (String monster: monsterCSV.split(",")){
				EnemyType tempEnemy = EnemyFactory.get(monster);
				if(tempEnemy==null) {
					Logger.err("ERROR: Tried to make an enemy of an invalid type: "+monster);
				}else{
					types.add(new MonsterSpec(tempEnemy));
				}
			}

			MonsterGroup group = new MonsterGroup(types,battleMapID,0,movement,zoneID,x,y); 
			monsters.put(group.id, group);
			
		}
		return monsters;
	}
	public void render(Graphics g, int cx, int cy){

		int dx = GameConstants.SCREEN_WIDTH / OverworldConstants.TILE_WIDTH + 2;
		int dy = GameConstants.SCREEN_HEIGHT / OverworldConstants.TILE_HEIGHT + 2;
		for(int x=cx-dx; x<cx+dx; x++) for(int y=cy-dy; y<cy+dy; y++) {
			drawLayers(g, x, y);
			
			
//			if(image!=null)
//				g.drawImage(image, x*OverworldConstants.TILE_WIDTH, y*OverworldConstants.TILE_HEIGHT);
//			else {
//				g.setColor(Color.black);
//				g.fillRect(x*OverworldConstants.TILE_WIDTH, y*OverworldConstants.TILE_HEIGHT, 
//						OverworldConstants.TILE_WIDTH, OverworldConstants.TILE_HEIGHT);
//			}
		}
		

	}

	/** A basic pathfinding method. Uses flood fill to find an optimal path
	 * between two points, and returns the first direction of this path.
	 * 
	 * @param sx starting x coordinate
	 * @param sy starting y coordinate
	 * @param tx destination x coordinate
	 * @param ty destination y coordinate
	 * @param maxSearchDepth maximum path distance that the algorithm will consider.
	 * @return triplet of ints, [dx, dy, distance] of the direction to go first. <br>
	 * for [dx,dy], returns one of [-1, 0], [1, 0], [0, -1], [0, 1],
	 * or [0, 0] if start equals destination. <br>
	 * returns null if no path exists. <br> 
	 */
	public int[] findPath(int sx, int sy, int tx, int ty, int maxSearchDepth) {
		if(sx<0 || sy<0 || sx>getWidth() || sy>=getHeight()) return null;
		if(tx<0 || ty<0 || tx>getWidth() || ty>=getHeight()) return null;
		if(sx==tx && sy==ty) return new int[] {0,0,0};
		
		Queue<Integer> qx = new LinkedList<Integer>();
		Queue<Integer> qy = new LinkedList<Integer>();
		qx.add(tx);
		qy.add(ty);
		int dist[][] = new int[getWidth()][getHeight()];
		final int g = -1-getWidth()-getHeight(); // arbitrary large negative number
		if(maxSearchDepth<0) maxSearchDepth = 0;
		dist[tx][ty] = g;  
		while(!qx.isEmpty()) {
			int x = qx.poll();
			int y = qy.poll();
			int d = dist[x][y];
			if(!getTile(x, y).isTraversible()) {
				dist[x][y] = -1;
				continue;
			}
			if(d>maxSearchDepth+g) break;
			if(x==sx && y==sy) break;
			if(x>0 && dist[x-1][y]==0) {
				qx.add(x-1);
				qy.add(y);
				dist[x-1][y] = d+1;
			}
			if(x<getWidth()-1 && dist[x+1][y]==0) {
				qx.add(x+1);
				qy.add(y);
				dist[x+1][y] = d+1;
			}
			if(y>0 && dist[x][y-1]==0) {
				qx.add(x);
				qy.add(y-1);
				dist[x][y-1] = d+1;
			}
			if(y<getHeight()-1 && dist[x][y+1]==0) {
				qx.add(x);
				qy.add(y+1);
				dist[x][y+1] = d+1;
			}
		}
		
		if(dist[sx][sy]==0)
			return null;
		ArrayList<int[]> ret = new ArrayList<int[]>();
		if(sx>0 && dist[sx-1][sy]<dist[sx][sy]) 
			ret.add(new int[] {-1, 0, dist[sx][sy]});
		if(sx<getWidth()-1 && dist[sx+1][sy]<dist[sx][sy]) 
			ret.add(new int[] {1, 0, dist[sx][sy]});
		if(sy>0 && dist[sx][sy-1]<dist[sx][sy]) 
			ret.add(new int[] {0, -1, dist[sx][sy]});
		if(sy<getHeight()-1 && dist[sx][sy+1]<dist[sx][sy]) 
			ret.add(new int[] {0, 1, dist[sx][sy]});
		return ret.get((int)(ret.size()*GameModel.MAIN.rand.get()));
	}
	
}
