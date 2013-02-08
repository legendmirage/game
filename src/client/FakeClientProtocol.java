package client;

import java.util.ArrayList;

import NetworkUtil.Action;

import com.esotericsoftware.kryonet.Client;

import model.GameModel;

/** This fake client protocol doesn't actually talk with the server. <br>
 * Instead, it just simulates a perfect server. <br>
 */
public class FakeClientProtocol implements ClientProtocol{

	public FakeClientProtocol() {
		
	}
	
	@Override
	public void sendAction(Action action) {
		receiveAction(action);
	}
	public Client getClientToServer()
	{
		return null;
	}


	public void receiveAction(Action action) {
		//PROPER RECIEVING ACTION INTO queue
		GameModel.MAIN.addAction(action);
	}

	@Override
	public InitInfo getInitInfo() {
		InitInfo ii = new InitInfo();
		ii.clientID = 0;
		ii.randSeed = 55555;
		return ii;
	}

	@Override
	public void setInitInfo(InitInfo ii) {
		
	}

	@Override
	public ArrayList<Action> getPrev() {
		
		return new ArrayList<Action>();
	}

}
