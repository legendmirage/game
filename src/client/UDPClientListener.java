package client;

import java.util.ArrayList;
import java.util.HashMap;

import model.GameModel;
import util.Logger;
import util.ModelUtil;
import NetworkUtil.Action;
import NetworkUtil.ConstantsAndUtil;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class UDPClientListener extends Listener{
	private UDPClientProtocol rr;
	private Action[]  prevMoves = new Action[10];
	private ArrayList<Action> prev = new ArrayList<Action>();
	private HashMap<Action, Boolean> alreadySeen = new HashMap<Action, Boolean>();
	private ArrayList<Action> canRemove= new ArrayList<Action>();
	private int mostRectAction = 0;

	public UDPClientListener(UDPClientProtocol rc)
	{
		Action a = new Action(-1, ActionType.DO_NOTHING);
		for(int x =0; x< prevMoves.length; x++){
			prevMoves[x]=a;
			alreadySeen.put(a, true);
			canRemove.add(a);
		}
		this.rr=rc;
		UDPClientProtocol.prev=prev;
	}
	public void received (Connection connection, Object object) {
			byte[] recv;
			if(object instanceof byte[])
			{
				recv=(byte[])object;
				if(recv[0]==ConstantsAndUtil.startGame)
					Logger.log("ITS TIME TO PLAY A GAMEIOFPJEIOPSJFEOPSIJEPPO");
				if (recv[0]== ConstantsAndUtil.actionIdentifier)
				{
					Action a= ConstantsAndUtil.parseAction(recv);
					if(a.type==ActionType.GODMODE){
						EpicGameContainer.MAIN.godmode = !EpicGameContainer.MAIN.godmode;
						return;
					}
					prev.add(a);
					this.receiveAction(a);
				}
				else if(recv[0]== ConstantsAndUtil.udpActionSet)
				{
					int y=1;
					Logger.log(recv);
					synchronized(alreadySeen){
						for(int x=prevMoves.length-1; x>-1 ; x--){
							
							Action a = ConstantsAndUtil.parseAction(recv,y);
							y=y+a.arg.length()+4  + 2+4;
							if(a.tickNum>mostRectAction){
								mostRectAction=a.tickNum;
							}
							if(alreadySeen.containsKey(a)){
									continue;
							}else{
								Logger.log("ADDING ACTION");
								Logger.log(a);
								receiveAction(a);
								canRemove.add(a);
							}
							
							if(canRemove.size()>50){
								synchronized(canRemove){
									while(canRemove.size() > 20){
										if(alreadySeen.containsKey(canRemove.get(0)))
												alreadySeen.remove(canRemove.remove(0));
									}
								}
							}
						}
					}
				}
				else if (recv[0]== ConstantsAndUtil.pastStates)
				{
					int id=ConstantsAndUtil.readInt(recv,1);
					String args = "";
					for(int i=0; i < recv[6]; i++)
					{
						args = args+ (char)recv[i+6];
					}
					Action a;
					a= new Action(id, ActionType.values()[recv[5]], args);
					prev.add(a);
					
				}
				else if (recv[0]==ConstantsAndUtil.newPlayer)
				{
					Logger.log("NEW PLAYER JOINED AT "+ModelUtil.millisElapsed);
				}
				else if(recv[0]==ConstantsAndUtil.serializedBattlePlayer &&  EpicGameContainer.MAIN!=null)
				{	
					Action a = new Action(-1, ActionType.DEBUG, "RECIEVED serialized BattlePlayer, track down issue");
					Logger.log(a);
				}
				else if(recv[0]== ConstantsAndUtil.serializedPlayer &&EpicGameContainer.MAIN!=null)
				{
					Logger.log("CALLED SERIALZIED FIND THIS PACKET:  YOU SHOULD TELL BEN IMMEDIATELY HE REALLY FUCKED UP");
					//GameModel.MAIN.players.get(ConstantsAndUtil.readInt(recv,22)).updatePlayerInfo(recv);
				}
				else if(recv[0]==ConstantsAndUtil.donePast)
				{
					
					/*Collections.sort(prev);
					rr.gotPrevMoves=true;*/
					
				}
				else if(recv[0]==ConstantsAndUtil.startGame)
				{
					
					rr.shallWePlayAGame=true;
				}
				else if (recv[0]== ConstantsAndUtil.idResponsePrefix)
				{
					int x = 1;
					
					InitInfo ii = new InitInfo();
					ii.clientID=((recv[x++]<<24)+(recv[x++]<<16)+(recv[x++]<<8)+(recv[x++]));
					ii.randSeed=((recv[x++]<<24)+(recv[x++]<<16)+(recv[x++]<<8)+(recv[x++]));
					ii.numTicks=(recv[x++]<<24)+(recv[x++]<<16)+(recv[x++]<<8)+(recv[x++]);
					ii.firstPlayer=(recv[x++]%2==0);
					if(ii.firstPlayer  || rr.gotPrevMoves==true)
						rr.gotPrevMoves=true;
					else
						rr.gotPrevMoves=false;
					ii.start=false;
					rr.setInitInfo(ii);
				
	/*				Action next = new Action(recv[4],recv[5],recv[6]);
					GameModel.MAIN.addAction(next);
					int cSum=recv[0]<<24+recv[1]<<16+recv[2]<<8+recv[3];
	*/			}
			}
			//Default case

	}	
	public void finish(){
		
	}
	public void receiveAction(Action action) {
		if(action.type!= ActionType.DO_NOTHING)
			Logger.log(action);
		//Protocol required, adds to current game model
		//PROPER
		if(GameModel.MAIN!=null)
			GameModel.MAIN.addAction(action);
		if(action.type!=ActionType.DO_NOTHING)
			synchronized(prev){prev.add(action);}
	}
	
	/*private byte[] updateToSend(Action ... acts)
	{
		/*
		byte[] ret = new byte[previousActions[0].length+
		                      previousActions[1].length+
		                      next.length];
		System.arraycopy(previousActions[0], 0, ret, 0, previousActions[0].length);
		System.arraycopy(previousActions[1], 0, ret, previousActions[0].length, previousActions[1].length);
		System.arraycopy(next, 0, ret,previousActions[1].length+previousActions[0].length, next.length);
		previousActions[0]=previousActions[1];
		previousActions[1]=previousActions[2];
		previousActions[3]=next;
		return ret;
		return null;
	}
*/


}
