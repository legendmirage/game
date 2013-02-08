package client;

import java.io.IOException;
import java.util.ArrayList;

import util.Logger;

import NetworkUtil.Action;
import NetworkUtil.ConstantsAndUtil;
import NetworkUtil.NTPClient;
import NetworkUtil.PacketOfData;



import model.GameModel;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;	


/** This fake client protocol doesn't actually talk with the server. <br>
 * Instead, it just simulates a perfect server. <br>
 */
public class UDPClientProtocol extends Thread implements ClientProtocol{
	private ArrayList<PacketOfData> toSend=new ArrayList<PacketOfData>();
	private Client myClient;
	private int  listeningPortDelta;
	private InitInfo ii=null;
	public Boolean gotPrevMoves =false;
	public static ArrayList<Action> prev;
	public boolean shallWePlayAGame=false;
	public final double delta=NTPClient.getDelta();
	
	public UDPClientProtocol(String host) throws IOException{
		Client cl = new Client(65536,16384);
		cl.start();
		cl.connect(500,host , ConstantsAndUtil.serverTCP, ConstantsAndUtil.serverUDP);
		this.myClient=cl;
		int x =0;
		Logger.log("UDP IS ACTIVE GO GO GO");
		ConstantsAndUtil.initializeObjects(cl.getEndPoint());
		while(true)
		{
	        try {
				if(x>50)
				{	
					break;
				}
		        Server server = new Server() ;
		        server.start();
				server.bind(ConstantsAndUtil.clientStartingAddressTCP+x,ConstantsAndUtil.clientStartingAddressUDP+x);
				server.getKryo().register(byte[].class);
				server.getKryo().register(EpicGameContainer.class);
				server.getKryo().register(GameModel.class);

				server.addListener(new UDPClientListener(this));
				this.listeningPortDelta=x;
				break;

			} catch (IOException e) {
				x++;
			}
		}
		if(x>50)
			throw new IOException();
	}

	@Override
	/*
	 * This adds a packet of data to the send queue
	 * @param action to send
	 * @return nothing
	 */
	public void sendAction(Action action) {
		PacketOfData p = new PacketOfData(action,false);
		p.setClient(myClient);
		toSend.add(p);
		//GameModel.MAIN.addAction(action);
	}
	public Client getClientToServer()
	{
		return myClient;
	}


	public void getMyId(){
		byte[] req= new byte[4];
		int x =0;
		req[x++]=(byte)ConstantsAndUtil.idRequest;
		req[x++]=(byte)(this.listeningPortDelta>>8&0xff);
		req[x++]=(byte)(this.listeningPortDelta&0xff);
		req[x++]=(byte)((ConstantsAndUtil.UDP==true)? 1 : 0);

		PacketOfData pa= new PacketOfData(req,true);
		pa.setClient(myClient);
		synchronized(toSend)
		{
			toSend.add(pa);
		}
		
	}

	@Override
	public void run() {
		while (true) {
			synchronized (this.toSend) {
				while (toSend.size() > 0) {
					PacketOfData prep = toSend.remove(0);
					prep.send();
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void receiveAction(Action action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InitInfo getInitInfo() {
		if(ii==null) this.getMyId();
		while (ii==null || !shallWePlayAGame){
			Thread.yield();
				
		}
		return ii;
	}

	@Override
	public void setInitInfo(InitInfo inf) {
		this.ii= inf;
		
	}

	@Override
	public ArrayList<Action> getPrev() {
		return prev;
	}


}
