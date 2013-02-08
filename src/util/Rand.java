package util;

import java.util.Random;

/** Custom randomizer. Basically just uses the Random class. */
public class Rand {

	private Random random ;// deterministic - use this for testing
	// static Random random = new Random((int)(55555*(1+5*Math.random())));
	public static int useCount = 0;
	
	/** Cannot be instantiated. */
	public Rand(int seed) {random = new Random(seed); }
	
	/** Returns a random double between 0.0 and 1.0. Just a seeded version of Math.random(). */
	public  double get() {
		useCount++;
		return random.nextDouble();
	}
	public  void seed(long seed) {	
		random.setSeed(seed);
	}
}
