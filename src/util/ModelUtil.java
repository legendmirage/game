package util;


import model.GameModel;

/** A library of utilities for any part of the model. */
public class ModelUtil {
	
	/** How many milliseconds have elapsed since the game was started. */
	public static int millisElapsed = 0;
	
	
	/** Fires when the slick engine calls an update, showing that some time has elapsed. */
	public static void updateModel(int delta) {
		int prevTick = millisElapsed / GameConstants.GAME_SPEED;
		millisElapsed += delta;
		int nextTick = millisElapsed / GameConstants.GAME_SPEED;
		for(int i = prevTick; i<nextTick; i++) 
			GameModel.MAIN.update();
	}
	
	/** This cannot be instantiated. */
	private ModelUtil() {}
}
