package debug;

public abstract class DebugCommand {
	
	/** Commands take in arguments and return back a string w/ a status message */
	public abstract String run(String args);
}
