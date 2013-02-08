package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


/**
 * Graphics Exclusion Strategy. <br>
 * Designed to serialize and exclude graphics / client related field from serialization.
 */
public class ClientExclStrat implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> c) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if(f.getName().toLowerCase().contains(("renderer"))) return true;
		if(f.getName().startsWith("menu")) return true;
		return false;
	}
	

}
