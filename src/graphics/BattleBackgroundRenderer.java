package graphics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.yaml.snakeyaml.Yaml;

import util.GameConstants;
import util.Loader;
import util.Logger;
import util.U;

import battle.BattleConstants;
import battle.BattleTileMap;


/**
 * Creates a new parallax battle background renderer
 */
public class BattleBackgroundRenderer {
	ArrayList<Image> layers;
	BattleTileMap battleTileMap;
	boolean initialShift; //use this later to swap the value of the 
	int randomYShift;
	String bgType;
	
	/** private static Yaml loader */
    private static Yaml yaml;
    private static Map<String, Object> bgMap;
	
	public BattleBackgroundRenderer(BattleTileMap battleTileMap, int zoneID) {
		this.battleTileMap = battleTileMap;
		initialShift = true;
		
		layers = new ArrayList<Image>();
		yaml = new Yaml();
		try {
            bgMap = (Map)yaml.load(Loader.open("res/art/background/background.yaml"));
        } catch(Exception e) {
            Logger.err("bgmap YAML load failed");
            e.printStackTrace();
        }
	
		try {
			//Looks at what bg should be attached to this map
		
			if (bgMap.containsKey("zone "+Integer.toString(zoneID))){
				bgType = (String) bgMap.get("zone "+Integer.toString(zoneID));
				
				
			}
			else{
				bgType = "town";
			}
		
			List<String> layersList;
			LinkedHashMap<String, Object> data;
			if (bgMap.containsKey(bgType)){
				data = (LinkedHashMap) bgMap.get(bgType);	
			}
			else{
			data = (LinkedHashMap) bgMap.get("town");
			}
		
			layersList = (List<String>)data.get("layers");
		
			
			for (int i=0; i<layersList.size(); i++) {
				layers.add (new Image(layersList.get(i)));
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/** Adds a layer (from back up) */
	public void addLayer(Image layer) {
		layers.add(layer);
	}

	/** Renders given the camera x and camera y position */
	public void render(GameContainer gc, Graphics g, float cx, float cy) {
		
		 
		for(int z=0; z<layers.size(); z++) {
			Image l = layers.get(z);
			
			
			int imgh = l.getHeight();
			int imgw = l.getWidth();
			if(initialShift)
			{randomYShift= (int) (Math.random()*(150)); //just.... shift the map sometimes...
				initialShift = false;
			}	

			
			
			int mapw = battleTileMap.getWidth() * BattleConstants.TILE_WIDTH;
			int maph = battleTileMap.getHeight() * BattleConstants.TILE_HEIGHT;
			
			float xp = (cx / mapw) * (z / (float)layers.size());
			float yp = (cy / maph) * (z / (float)layers.size());
			
			l.draw(0 - xp * (imgw - GameConstants.SCREEN_WIDTH),
					-1*randomYShift - yp * (imgh - GameConstants.SCREEN_HEIGHT));
		}
	}
}
