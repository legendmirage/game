package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import util.Logger;
import NetworkUtil.Action;
import NetworkUtil.ConstantsAndUtil;
import NetworkUtil.PacketOfData;
import client.ActionType;

import com.esotericsoftware.kryonet.Client;

public class ServerThread extends Thread {
	private  ArrayList<PacketOfData> toSend= new ArrayList<PacketOfData>();
	private Client conn1;
	private Client conn2 = null;
	private Boolean P1Moved = false;
	private Boolean P2Moved = false;
	private ArrayList<Action> prevMovesUDP = new ArrayList<Action>();
	private ArrayList<Action> movesSoFar= new ArrayList<Action>();
	public Integer lastTimeP1 =0;
	private boolean c1TCP=true;
	private boolean c2TCP=true;
	private ArrayList<Action> newActions = new ArrayList<Action>();
	//public  int lastTimeP2 =0;
	
	
	
	private int c1id;
	private int c2id;
	public int timeStarted;
	
	public ServerThread(Client endPoint, int id) {
		
		this.conn1 = endPoint;
		c1id=id;
		conn1.getKryo().register(byte[].class);
		Action a = new Action(0, ActionType.DO_NOTHING);
		for(int x =0; x< 10; x++){
			prevMovesUDP.add(a);
		}
	}

	public void addMove(Action a)
	{
		movesSoFar.add(a);
	}

	public void sendAll(Action a )
	{
		PacketOfData pa1 = new PacketOfData(a, c1TCP);
		PacketOfData pa2 = new PacketOfData(a, c2TCP);
		pa1.setClient(conn1);
		pa2.setClient(conn2);
		synchronized(toSend){
			toSend.add(pa1);
			toSend.add(pa2);
		}
	}
			
	public void sendAction(Action a)
	{
		synchronized(lastTimeP1)
		{
			if(a.userID%2==0)
				synchronized(P1Moved){if(P1Moved==true) return; P1Moved=true;}
			else
				synchronized(P2Moved){if(P2Moved==true) return; P2Moved=true;}
			synchronized(toSend){
			
				if(c2TCP)
				{
					PacketOfData pa2 = new PacketOfData(a, c2TCP);
					pa2.setClient(conn2);
					toSend.add(pa2);
				}
				if(c1TCP){
					PacketOfData pa1 = new PacketOfData(a, c1TCP);
					pa1.setClient(conn1);
					toSend.add(pa1);
				}
				if(!c1TCP ||!c2TCP){
					synchronized(newActions){
						newActions.add(a);
					}
				}
			}
		}
	}
	public void sendId(PacketOfData pa,int id, boolean isTCP)
	{
		pa.setClient(getClientById(id));
		if(id%2==1)
			c1TCP=isTCP;
		else
			c2TCP=isTCP;
		
		synchronized (toSend) {
			toSend.add(pa);
			if(conn2!=null){

				Date now = new Date();
				
				long next= (now.getTime());
				next+=5000;
				
				byte[] start = new byte[9];
				for(int i =0; i < 8; i++)
					start[1+i]=(byte)((next>>(56-i*8)) &0xff);
				
				
				start[0]= ConstantsAndUtil.startGame;
				PacketOfData st1 = new PacketOfData(start,true);
				st1.setClient(conn1);
				st1.send();
				
				PacketOfData st2 = new PacketOfData(start,true);
				st2.setClient(conn2);

				
				st2.send();
			}


		}	
	}

	public void addPlayer(Client end,int id) {
		int x =0;
		this.conn2 = end;
		c2id=id;
		end.getKryo().register(byte[].class);
		ServerRunner.idToServer.put(id, this);

		byte[] fin =new byte[1];
		fin[0]=(byte)ConstantsAndUtil.donePast;
		
		
		fin =new byte[5];
		fin[x++]=(byte)ConstantsAndUtil.newPlayer;
		fin[x++]=(byte)(id>>24&0xff);
		fin[x++]=(byte)(id>>16&0xff);
		fin[x++]=(byte)(id>>8&0xff);
		fin[x++]=(byte)(id   &0xff);

		PacketOfData  donePast = new PacketOfData(fin,true);
		PacketOfData initFinal = new PacketOfData(fin,true);
		initFinal.setClient(conn1);
		donePast.setClient(conn2);
		synchronized(toSend){
			toSend.add(donePast);
			toSend.add(initFinal);
			
		}
	}
	private void updateActions(){
			Collections.sort(newActions);
			for(int x =0; x < newActions.size(); x++){
				prevMovesUDP.remove(9);
				prevMovesUDP.add(0, newActions.remove(0));
			}
			
	}
	private byte[] prevToByteArray(){
		int x=0;
		int sizeOfId = 4;
		int sizeOfAction = 2;
		int sizeOfTicks = 4;		
		byte[] stream= new byte[1+(sizeOfId  + sizeOfAction+1+1+ sizeOfTicks)*15];
		//Logger.log(stream.length);
		byte[] preStream=stream.clone();
		stream[x++]=(byte)NetworkUtil.ConstantsAndUtil.udpActionSet;
		for(Action act : prevMovesUDP){
			if(stream[1]==-1){
				Logger.log(stream);
				ConstantsAndUtil.actionCopyToByteStream(act, 1,stream);
			}
			x=ConstantsAndUtil.actionCopyToByteStream(act, x,stream);
		}
		return stream;
	}
	private void doUDPNoAct()
	{
		Action a = new Action(0,ActionType.DO_NOTHING,"");
		newActions.add(a);
		updateActions();
		byte[] toSend=prevToByteArray();
		if(!c1TCP){
			PacketOfData pa = new PacketOfData(toSend,c1TCP);
			pa.setClient(conn1);
			this.toSend.add(pa);
		}
		if(!c2TCP){
			PacketOfData pa = new PacketOfData(toSend,c2TCP);
			pa.setClient(conn2);
			this.toSend.add(pa);
		}
		
	}
	private void doTCPNoAct(){
			//lastTimeP2+=3;
			Action a = new Action(0,ActionType.DO_NOTHING,"");
			if(c1TCP){
				PacketOfData p1=new PacketOfData(a, c1TCP);
				p1.setClient(conn1);
				toSend.add(p1);
				
			}
			if(c2TCP){
				PacketOfData p2=new PacketOfData(a, c2TCP);
				p2.setClient(conn2);
				toSend.add(p2);							
			}

	}
	@Override
	public void run() {
		while (true) {
			synchronized (this.toSend) {
				synchronized(lastTimeP1){
					synchronized(newActions){
						synchronized(P1Moved){synchronized (P2Moved) {
							P1Moved=false;
							P2Moved=false;
						}}
						if(toSend.size()==0)
						{
							lastTimeP1++;
							if(c1TCP || c2TCP)
								doTCPNoAct();
							if(!c1TCP|| !c2TCP)
								doUDPNoAct();
						}else if(!c1TCP||!c2TCP)
						{
							byte[] toSendTo=prevToByteArray();
							if(!c1TCP){
								PacketOfData pa = new PacketOfData(toSendTo, false);
								pa.setClient(conn1);
								toSend.add(pa);
							}
							if(!c2TCP){
								PacketOfData pa = new PacketOfData(toSendTo, false);
								pa.setClient(conn2);
								toSend.add(pa);
							}
						}
						
						while (toSend.size() > 0) {
							PacketOfData prep = toSend.remove(0);
							//byte[] dater=prep.getData();
							prep.send();
						}
					}
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	private Client getClientById(int id)
	{
		if(id==c1id)
			return conn1;
		else if(id==c2id)
			return conn2;
		else
			return null;
	}

	private Client getOtherClientById(int id)
	{
		if(id==c1id)
			return conn2;
		else if(id==c2id)
			return conn1;
		else
			return null;
	}


}
