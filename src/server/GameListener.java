package server;

import java.io.IOException;
import java.util.Date;
import NetworkUtil.Action;
import NetworkUtil.ConstantsAndUtil;
import NetworkUtil.PacketOfData;
import util.Logger;





import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
public class GameListener extends Listener {
	/*
	 * (non-Javadoc)
	 * @see com.esotericsoftware.kryonet.Listener#received(com.esotericsoftware.kryonet.Connection, java.lang.Object)
	 * The general look of a packet is, header(Recieved, checksum failed.   
	 */
	public int mostRecentAction=-1;

	@Override	
	public void received (Connection conn, Object object) {
			if((object instanceof byte[])){
			
				try {
				byte[] recv=(byte[])object;
				//Default case
				// packet laid out like
				//type[1]+id[4]+aType[1]+argLen[1]+arg[argLen]+timeStamp[4]
				//1+4+1+1+argLen+4
				if (recv[0] ==ConstantsAndUtil.actionIdentifier)
				{
					Action a = ConstantsAndUtil.parseAction(recv);
					ServerThread se = ServerRunner.idToServer.get(a.userID);
					se.sendAction(a);
				}
				else if(recv[0]==ConstantsAndUtil.serializedBattlePlayer
						||recv[0]==ConstantsAndUtil.serializedPlayer)
				{
					
					Logger.log("DEAD CODE BEING CALLLED IN GAMELISTENER YOU SHOULD INVESTIGATE WHY");
					
				}
				else if(recv[0]==ConstantsAndUtil.idRequest){
						genId(recv,conn);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
				return;
	}	

	private void genId(byte[] recv, Connection conn) throws IOException
	{
			byte[] sendID=new byte[14];
			int seed =( new java.util.Random()).nextInt();
			int id=0;
			int x=0;
			sendID[x++]= ConstantsAndUtil.idResponsePrefix;
			synchronized(ServerRunner.highestID)
			{
				id=ServerRunner.highestID++;
			}
			
			sendID[x++]=(byte)((id>>24)&0xff);
			sendID[x++]=(byte)((id>>16)&0xff);
			sendID[x++]=(byte)((id>>8)&0xff);
			sendID[x++]=(byte)((id )&0xff);
			
			sendID[x++]=(byte)((seed>>24)&0xff);
			sendID[x++]=(byte)((seed>>16)&0xff);
			sendID[x++]=(byte)((seed>>8)&0xff);
			sendID[x++]=(byte)((seed )&0xff);

			

			PacketOfData idPacket= new PacketOfData(sendID,true);

			synchronized(ServerRunner.noPartner)
			{
				synchronized(ServerRunner.idToServer)
				{
					int delta=recv[2]+(recv[1]<<8);
					Client newConn = new Client(65536,16384);
					newConn.start();
					newConn.connect(500,conn.getRemoteAddressUDP().getHostName(), 
							ConstantsAndUtil.clientStartingAddressTCP+delta,ConstantsAndUtil.clientStartingAddressUDP+delta);
					if(ServerRunner.noPartner==0){
						ServerRunner.noPartner=id;		
						sendID[x++]=0;
						sendID[x++]=0;
						sendID[x++]=0;
						sendID[x++]=0;
						sendID[x++]=0x00;//
						
						ServerThread newThread=new ServerThread(newConn,id);
						ServerRunner.idToServer.put(id, newThread);
						newThread.timeStarted=(int) new Date().getTime();
						newThread.sendId(idPacket, id,(recv[recv.length-1]==0));
						newThread.start();
					}else{
						ServerThread working = ServerRunner.idToServer.get(ServerRunner.noPartner);
						int cTime=(int)((new Date().getTime())- working.timeStarted)+ConstantsAndUtil.slush;
						sendID[x++]=(byte)((cTime>>24)&0xff);
						sendID[x++]=(byte)((cTime>>16)&0xff);
						sendID[x++]=(byte)((cTime>>8)&0xff);
						sendID[x++]=(byte)((cTime )&0xff);

						sendID[x++]=0x01;
						working.addPlayer(newConn,id);
						working.sendId(idPacket, id,(recv[recv.length-1]==0));
					}
					
					
				}
			}

	}
}
