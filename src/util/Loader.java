package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/** Utilities for loading resources and assets */
public class Loader {

	/**
	 * @param file_path of file you wish to read relative to /res (or in /res)
	 * @return BufferedReader to the file
	 */
	public static BufferedReader open(String file_path) {
	
		// normal packaged loading
		InputStream is;
		is = Loader.class.getClassLoader().getResourceAsStream(file_path);
	
		// when running from eclipse
		if(is == null) {
			// we add this exception to make our loader consistent with slick
			if(file_path.startsWith("res/")) file_path = file_path.substring(4);
			is = Loader.class.getClassLoader().getResourceAsStream(file_path);
		}
		
		return new BufferedReader(new InputStreamReader(is));
	}

}
