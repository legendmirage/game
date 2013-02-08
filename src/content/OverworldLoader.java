package content;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import overworld.OverworldTileMap;
import util.Logger;

public class OverworldLoader {

	public static final ArrayList<OverworldTileMap> zoneMaps = new ArrayList<OverworldTileMap>();
	public static Image burningTownBackground;
//	Zone1: Odell 1
//	Zone 2: Factory
//	Zone 3: --
//	Zone 4: --
//	Zone 5: First Floor Factory
//	Zone 6: Second Floor Factory
//	Zone 7: Odell 2
//	Zone 8: Caracal
//	Zone 9: Odell 3
//	Zone 10: Sauce
//	Zone 11: Odell 3
//	Zone 12: Eastern Forest
//	Zone 13: Lucas Encounter
	
	public static void loadAll(){
		
		try {
			for(int i=0; i<=40; i++) {
				TiledMap zoneMap = new TiledMap("res/maps/Zone"+i+".tmx");
				int id = Integer.parseInt(zoneMap.getMapProperty("ID", "-1"));
				String name = zoneMap.getMapProperty("name", "-1");
				String musicTitle = zoneMap.getMapProperty("music", "call");
				zoneMaps.add(new OverworldTileMap(zoneMap,name,musicTitle));
			}
			
			burningTownBackground = new Image("/res/art/background/townOnFire.png");
		} catch (SlickException e) {
			Logger.err("ERROR: Zone map failed to load");
		}
		

	}

	
	/** Cannot be instantiated */
	public void OverworldRenderer(){}

}
