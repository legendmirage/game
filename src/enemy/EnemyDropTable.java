package enemy;

import util.U;

/** Contains the logic to determine what item is dropped when a monster dies in battle. <br>
 * Could potentially return null if nothing should be dropped. <br>
 */
public class EnemyDropTable {
	private static double x;
	private static double y;
	private static double z;
	public static String computeDrop(EnemyType type) {
		double l = type.level;
		double p = type.power;
		x = U.r();
		y = U.r();
		z = U.r();
		
		for(String name: type.drops.keySet()) {
			if(h(type.drops.get(name))) return name;
		}
		
		if(f(0.01+0.003*p)) {
			if(g(1.0/3)) return "Ruby";
			if(g(1.0/3)) return "Aquamarine";
			if(g(1.0/3)) return "Quartz";
		}
		if(f(0.03+0.015*p)) {
			if(g(1.0/2)) {
				double a = U.r();
				if(a < 1.5-l/20) return "Vial";
				if(a < 2.5-l/20) return "Big Vial";
				if(a < 1) return "Huge Vial";
			}
			if(g(1.0/2)) {
				double a = U.r();
				if(a < 1.5-l/20) return "Bottle";
				if(a < 2.5-l/20) return "Big Bottle";
				if(a < 1) return "Huge Bottle";
			}
			return "Flask";
		}
		
		if(f(0.1)) {
			double a = U.r();
			if(a < 1.5-l/20) return "Scrap Metal";
			if(a < 2.5-l/20) return "Platinum";
			if(a < 1) return "Iridium";
		}
		double a = U.r();
		if(a < 1.5-l/20) return "Steam";
		if(a < 2.5-l/20) return "Cirrus Steam";
		if(a < 1) return "Nacreous Steam";
		
		return null;
	}
	private static boolean f(double p) {
		x-=p;
		return x<=0;
	}
	private static boolean g(double p) {
		y-=p;
		return y<=0;
	}
	private static boolean h(double p) {
		z-=p;
		return z<=0;
	}
}
