package client;

/** This is a data object containing all the info that the client needs to launch. */
public class InitInfo {
	
	public int clientID;
	public long randSeed;
	public boolean firstPlayer= true;
	public int numTicks;
	public boolean start=true;
	public InitInfo() {
	}
}
