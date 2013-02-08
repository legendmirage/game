package overworld;

/** A type of tile in the world map. */
public enum OverworldTileType {
	WALL("collision"),
	SPACE("background"),
	PORTAL("portal")
	;
	
	private OverworldTileType(final String tileName){
		this.tileName = tileName;
	}
	private final String tileName;
	
	@Override
	public String toString(){
		return tileName;
	}
	
	/** Can a player usually walk over this type of tile? */
	public boolean isTraversible() {
		return this!=WALL;
	}
	
}
