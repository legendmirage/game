package visnovel;

/** Prototype DSL for specifying out cutscenes. Up to 4 visible character portraits can be on the screen at a time (2 left, 2 right) */
public interface CutsceneDSL {
	
	/** Enters a character on an unused side of the screen (first left, then right). Convenience for simple dialog */
	public abstract Cutscene enter(String characterName);

	/** Character portrait enters on left side of the screen */
	public abstract Cutscene enterLeft(String characterName);
	
	/** Character portrait enters on the right side of the screen */
	public abstract Cutscene enterRight(String characterName);
	
	/** Character portrait exits */
	public abstract Cutscene exits(String characterName);
	
	/** Dialogue attributed to character prints on the screen */
	public abstract Cutscene speaks(String characterName, String dialog);
	
	/** Narration event unbound to a particular character (eg: narrated sound effects, actions */
	public abstract Cutscene narrate(String narration);

}
