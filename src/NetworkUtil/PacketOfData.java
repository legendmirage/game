package NetworkUtil;

import java.io.UnsupportedEncodingException;

import util.Logger;

import client.ActionType;

import com.esotericsoftware.kryonet.Client;

public class PacketOfData {
	private byte[] stream;

	private Client cl;
	private final boolean tcp;
	/*
	 * Take an action and create base packet length of 7 1 for each bit of
	 * action, 4 for checksum
	 */

	/*
	 * For networking use only, please do not touch Sets up just a dimple bit
	 * stream, will break things if you play around probably
	 */
	public PacketOfData(byte[] bits, boolean isTCP) {
		this.stream = bits;
		this.tcp=isTCP;
	}

	/*
	 * Takes an action, and submits it to the server
	 */
	public PacketOfData(Action a, boolean isTCP) {
		this(a, null, isTCP);
		
	}

	/*
	 * internal method that takes an id and converts it to a byte stream
	 */

/**
 * 
 * @param a action to turn into packet
 * @param cl client which is optional to send it to
 * Produces a acket of data, with a stream that looks like
 * 				type[1]+id[4]+aType[1]+argLen[1]+arg[argLen]+timeStamp[4]
 */
	public PacketOfData(Action a, Client cl, boolean isTCP) {
		this.tcp=isTCP;
		stream = ConstantsAndUtil.actionToStream(a, 1);
		stream[0]=ConstantsAndUtil.actionIdentifier;
/*		
		if(a.type!=ActionType.DO_NOTHING){
			Logger.log("BELIEFS BELOW");
			Logger.log(a);
			Logger.log(ConstantsAndUtil.parseAction(stream));
			Logger.log("+++++");
		}
	*/
	}
	public void setClient(Client cl)
	{
		this.cl=cl;
	}
	public Client getClient()
	{
		return this.cl;
	}
	public void send(){
		if(this.tcp)
			sendTCP();
		else
			sendUDP();
	}
	private void sendTCP()
	{
		if(this.cl!=null && stream!=null)
			this.cl.sendTCP(stream);
	}
	private void sendUDP()
	{
		if(this.cl!=null && stream!=null)
			this.cl.sendUDP(stream);
	}
	public void sendPrevState()
	{
		if(this.cl!=null&&stream!=null){
			byte[] old=new byte[stream.length+1];
			old[0]=ConstantsAndUtil.pastStates;
			for(int x=0; x < stream.length;x++)
				old[x+1]=stream[x];
			this.cl.sendTCP(stream);
		}
	}
	public byte[] getData() {
		return stream;
	}
}
