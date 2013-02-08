package content;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import org.yaml.snakeyaml.Yaml;

import util.Loader;
import util.Logger;

import battle.BattleTileMap;
import battle.BattleTileType;

/** Loads all the available battle maps. */
public class BattleMapLoader {
	
	/** A library of all the battle maps available. <br> 
	 * This contains pre-constructed battle map objects. <br>
	 * When a battle starts, a map in this list is chosen and used. <br>
	 */
	private static final ArrayList<BattleTileMap> battleMaps = new ArrayList<BattleTileMap>();
	public static Image battleHUD, battleHUDTopLayer, battleGear;
	
	/** private static Yaml loader */
    private static Yaml yaml;
    private static Map<String, Object> battles;
    
    private static final ArrayList<BattleTileMap> forestList = new ArrayList<BattleTileMap>();
    private static final ArrayList<BattleTileMap> townList = new ArrayList<BattleTileMap>();
    private static final ArrayList<BattleTileMap> beachList = new ArrayList<BattleTileMap>();
    private static final ArrayList<BattleTileMap> operaList = new ArrayList<BattleTileMap>();
    
    static {
    	yaml = new Yaml();
    }
	
    
	public static void loadAll() throws SlickException {
		
		BattleTileType[][] battleMap;
		Rectangle[] spawns;
		int id;
		TiledMap zoneMap;

		try {
            battles = (Map)yaml.load(Loader.open("res/tilemap/battleMaps.yaml"));
        } catch(Exception e) {
            Logger.err("battleMap YAML load failed");
            e.printStackTrace();
        }

		LinkedHashMap<String, Object> data;
		data = (LinkedHashMap<String, Object>) battles.get("files");
		List<String> battleList = (List<String>)data.get("maps");
		
		
		
		for(String s: battleList){
			
			zoneMap = new TiledMap(s);
			id = Integer.parseInt(zoneMap.getMapProperty("ID", "-1"));
			int zone = Integer.parseInt(zoneMap.getMapProperty("zone", "0"));
			if(zone == 0){
				townList.add(new BattleTileMap(zoneMap));
				forestList.add(new BattleTileMap(zoneMap));
			}
			else if(zone == 1){
				beachList.add(new BattleTileMap(zoneMap));
			}
			else if(zone == 2){
				operaList.add(new BattleTileMap(zoneMap));
			}
			battleMaps.add(new BattleTileMap(zoneMap));
			
		}
		
		  
        battleHUD = new Image("res/art/HUD/hud1.png");
        battleHUDTopLayer = new Image("/res/art/HUD/QWE123.png");
        battleGear = new Image("res/art/HUD/hudGear.png");
		}
	
	/** Gets a specific map from the map pool. */
	public static BattleTileMap get(int index, int zone) {
		BattleTileMap map = null;
		if (index <= 0){
			Map<String, Object> backgroundNames = null;
			try {
				 backgroundNames = (Map)yaml.load(Loader.open("res/art/background/background.yaml"));
	        } catch(Exception e) {
	            Logger.err("backgroundMap YAML load failed");
	            e.printStackTrace();
	        }

			String data;
			data = (String) backgroundNames.get("zone "+Integer.toString(zone));
			 if(data.equals("forest")|| data.equals("town")) {
			 		index = (int)(Math.random()*forestList.size());
			 		while(index == 6){
				 		index = (int)(Math.random()*forestList.size());
			 		}
			 		map = forestList.get(index);
			 }
			 else if(data.equals("beach")) {
				 
			 		index = (int)(Math.random()*beachList.size());
			 		map = beachList.get(index);
			 }
			 else{
			 		index = (int)(Math.random()*operaList.size());
			 		map = operaList.get(index);
			 }
			 

				map.setBackground(zone);
		}
			
		else{
			map = battleMaps.get(index);
			map.setBackground(zone);
		}
	 
		
		return map;
	}
	
	/** Returns a random map from the map pool. */
	public static BattleTileMap random() {
		int index = (int)(Math.random()*battleMaps.size());
		return battleMaps.get(index);
	
	}
	
	/** Cannot be instantiated. */
	private BattleMapLoader() {}
}
