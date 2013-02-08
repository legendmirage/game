package client;

import java.util.ArrayList;

import NetworkUtil.Action;


/** The subsystem on the client side that talks with the server. 
 */
public interface ClientProtocol  {
	
	public void sendAction(Action action);
	public void receiveAction(Action action);
	public InitInfo getInitInfo();
	public void setInitInfo(InitInfo ii);
	public   ArrayList<Action> getPrev();

	
}
