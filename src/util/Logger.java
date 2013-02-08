package util;

import model.GameModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/** Standard issue logger utility. */
public class Logger {
	private static Gson gson = new GsonBuilder().setExclusionStrategies(new ClientExclStrat()).create();
	private static final long initTime = System.currentTimeMillis();
	private static boolean printEnabled = true;
	/** Cannot instantiate this class. */
	private Logger() {}
	
	/** Logs the string representation of the given object and an associated timestamp to System.out */
	public static void log(Object obj) {
		if(printEnabled && GameConstants.enableLogging)
			System.out.println(objectToFormattedString(obj));
	}
	/** Logs the string representation of the given object and an associated timestamp to System.err */
	public static void err(Object obj) {
		if(printEnabled && GameConstants.enableLogging)
			System.err.println(objectToFormattedString(obj));
	}
	private static String objectToFormattedString(Object obj) {
		return stringToFormattedString((obj instanceof String) ? (String)obj : gson.toJson(obj));
	}
	private static String stringToFormattedString(String str) {
		return (System.currentTimeMillis()-initTime)+" "+str;
	}
	public static void logWithTicks(Object obj){
		logWithTicks(obj, GameModel.MAIN);
	}
	public static void logWithTicks(Object obj, GameModel gm){
		if(printEnabled && GameConstants.enableLogging )
		{
			String str=objectToFormattedString(obj);
			
			str=str.substring(str.indexOf(" "));
			str= GameModel.MAIN.tickNum+str;
			System.out.println(str);
			
			
		}
	}
	
	public static void toggleSystemPrinting() {
		printEnabled = !printEnabled;
	}
	public static void setSystemPrinting(boolean on) {
		printEnabled = on;
	}
}
