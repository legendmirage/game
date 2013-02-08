package util;

import model.GameModel;

import org.newdawn.slick.Animation;

/**
 * Container class for various general purpose static utilities. <br>
 * Abbreviated U to keep it as short as possible. <br>
 */
public class U {
	
	public static float alphaRamp(int total_duration, int fade_duration, int start_time) {
		
		float m = -1f/((float)fade_duration);
		float x = (GameModel.MAIN.tickNum - start_time);
		float b = total_duration * (1f/(float)fade_duration);
		
		float alpha = m*x + b;
		return U.minmax(0f, 1f, alpha);
	}
	

	/** returns either the lower bound if value is too low,
	 * the upper bound if value is too high,
	 * or the value itself
	 * @param lowerBound - lowest possible value
	 * @param upperBound - highest possibl value
	 * @param value - the value
	 * @return the correct value
	 */
	public static int minmax(int lowerBound, int upperBound, float value) {
		return (int)Math.min(Math.max((double)lowerBound, value), (double)upperBound);
	}
	
	/** returns either the lower bound if value is too low,
	 * the upper bound if value is too high,
	 * or the value itself
	 * @param lowerBound - lowest possible value
	 * @param upperBound - highest possibl value
	 * @param value - the value
	 * @return the correct value
	 */
	public static float minmax(float lowerBound, float upperBound, float value) {
		return Math.min(Math.max(lowerBound, value), upperBound);
	}
	
	/**
	 * Sets every frame in an animation to the same speed
	 * @param animation - the animation to modify
	 * @param duration - the duration to set every frame to
	 */
	public static void setAnimationSpeed(Animation animation, int duration) {
		for(int i = 0; i<animation.getFrameCount(); i++ ) {
			animation.setDuration(i, duration);
		}
	}
	
	/**
	 * Utility to generate a flipped copy of an animation. Frames & durations are copied.
	 * It's pretty fucking inefficient. Try not to use it too much mmmkay?
	 * @param animation - the original animation to duplicate
	 * @param flipHorizontal - whether to flip horizontally
	 * @param flipVertical - whether to flip vertically
	 * @return deep copy of the original animation except flipped as desired
	 */
	public static Animation flippedAnimationCopy(Animation animation,
			boolean flipHorizontal, boolean flipVertical) {
		
		if(animation == null) return null;
		
		Animation ret = new Animation();
		int totalFrames = animation.getFrameCount();
		for(int i=0; i<totalFrames; i++) {
			ret.addFrame(animation.getImage(i).getFlippedCopy(flipHorizontal, flipVertical),
						animation.getDuration(i));
		}
		return ret;
	}
	

	/** Returns a random double between 0 and 1, just like Math.random() but seeded. */
	public static double r() {
		return GameModel.MAIN.rand.get();
	}
}
