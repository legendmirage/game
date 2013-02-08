package battle;

/** Terrain types for the battle maps. */
public enum BattleTileType {
	SPACE("background"), 
	WALL("wall"), 
	PLATFORM("platform"),
	;

	private BattleTileType(final String tileName){
		this.tileName = tileName;
	}
	private final String tileName;
	
	
	@Override
	public String toString(){
		return tileName;
	}
}

