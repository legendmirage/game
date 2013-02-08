package NetworkUtil;

import java.io.UnsupportedEncodingException;

import util.Logger;

import client.ActionType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class ConstantsAndUtil {
	public static String serverAddress = "localhost";
	public static final boolean easyMode = false;
	public static boolean fake = true; //System.getProperty("server-real") == null ? true : false;
	public static boolean UDP=false;
	public static final int slush = 1500;
	public static final int idRequestLen = 12;
	public static final int clientStartingAddressTCP = 28778;
	public static final int clientStartingAddressUDP = 38778;
	public static final int serverTCP = 45671;
	public static final int serverUDP = 12345;
	public static final int serverDiff = 10;
	
	public static final byte actionIdentifier=1;// First byte of packet to signify action.
	public static final byte pastStates=2;// First byte of packet to signify previous action to apply quickly.
	public static final byte donePast=3;//Signal end of past states
	public static final byte newPlayer=4;//Signal end of past states
	public static final byte idRequest = 5;
	public static final byte idResponsePrefix = 6;
	public static final byte serializedPlayer = 7;
	public static final byte serializedBattlePlayer=8;
	public static final byte startGame=9;
	public static final byte udpActionSet=10;
	private static ActionType[] aTypes= ActionType.values();
	private static int sizeOfId = 4;
	private static int sizeOfAction = 2;
	private static int sizeOfTicks = 4;
	private static int sizeOfStrLen = 1;
	
	/**
	 * This function must be called both client and server side on each class
	 * you're going to want to serialize and send
	 */
	public static void initializeObjects(EndPoint ep) {
		Kryo kryo = ep.getKryo();
		kryo.register(byte[].class);
	}
	public static int readInt(byte[] buf, int offset){
		int ret=0;
		for(int i =offset; i<offset+4; i++)
		{
			int toAdd=buf[i];
			if(toAdd<0)
				toAdd=256+toAdd;
			int shift =     ((offset-i)*8);
			shift=24+shift;
			toAdd= toAdd<<shift;
			ret+=toAdd;
		}
		return ret;
		
	}
	public static int readInt(byte[] buf)
	{
		return readInt(buf,0);
	}
	
	public static Action parseAction(byte[] buf){
		return parseAction(buf,1);
	}
	public static Action parseAction(byte[] buf, int offset)
	{
		int id=ConstantsAndUtil.readInt(buf,offset);
		String args = "";
		for(int i=0; i < buf[5+offset]; i++)
		{
			args = args+ (char)buf[6+offset+i];
		}
		Action a;
		//int timeStep=ConstantsAndUtil.readInt(buf, 7+ buf[6]);
		a= new Action(id, aTypes[buf[4+offset]], args);
		return a;
	}
	public static int actionCopyToByteStream(Action act, int offset, byte[] stream){
		byte[] toEnc=new byte[1];
		try {
			toEnc = act.arg.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		int x=offset;
		if(stream.length < offset+sizeOfId  + sizeOfAction+sizeOfStrLen+toEnc.length+ sizeOfTicks){
			Logger.log("COPYING ARRAY");
			byte[] st2= new byte[offset+sizeOfId  + sizeOfAction+sizeOfStrLen+toEnc.length+ sizeOfTicks];
			System.arraycopy(stream, 0, st2, 0, stream.length);
			stream=st2;
		}
		//sizeOfId
		stream[x++]=(byte)((act.userID>>24)&0xff);
		stream[x++]=(byte)((act.userID>>16)&0xff);
		stream[x++]=(byte)((act.userID>>8)&0xff);
		stream[x++]=(byte)((act.userID)   &0xff);	
		
		//Type
		stream[x++]=(byte)(act.type.ordinal()&0xff);
		//length
		stream[x++]=(byte)(act.arg.length());
		
		for(int i=0;i<act.arg.length();i++)
			stream[x++]=toEnc[i];
	
		int timeStep=act.tickNum;
		stream[x++]=(byte)((timeStep>>24)&0xff);
		stream[x++]=(byte)((timeStep>>16)&0xff);
		stream[x++]=(byte)((timeStep>>8)&0xff);
		stream[x++]=(byte)(timeStep   &0xff);	
		return x;
	}
	public static byte[] actionToStream(Action act,int offset){
		byte[] toEnc=new byte[1];
		try {
			toEnc = act.arg.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int x=offset;
		byte[] stream = new byte[offset+sizeOfId  + sizeOfAction+sizeOfStrLen+toEnc.length+ sizeOfTicks];
	
		//sizeOfId
		stream[x++]=(byte)((act.userID>>24)&0xff);
		stream[x++]=(byte)((act.userID>>16)&0xff);
		stream[x++]=(byte)((act.userID>>8)&0xff);
		stream[x++]=(byte)((act.userID)   &0xff);	
		
		//Type
		stream[x++]=(byte)(act.type.ordinal()&0xff);
		//length
		stream[x++]=(byte)(act.arg.length());
		
		for(int i=0;i<act.arg.length();i++)
			stream[x++]=toEnc[i];
	
		int timeStep=act.tickNum;
		stream[x++]=(byte)((timeStep>>24)&0xff);
		stream[x++]=(byte)((timeStep>>16)&0xff);
		stream[x++]=(byte)((timeStep>>8)&0xff);
		stream[x++]=(byte)(timeStep   &0xff);	
		return stream;
	}
	public static byte[] intToByteApped(byte[] dest,int num, int offSet)
	{
		dest[offSet++]=(byte)((num>>24)&0xff);
		dest[offSet++]=(byte)((num>>16)&0xff);
		dest[offSet++]=(byte)((num>>8)&0xff);
		dest[offSet++]=(byte)((num)&0xff);
		
		return dest;
	}
	public static byte[] appendInts( int offSet, int ... nums)
	{
		byte[] dest = new byte[offSet + nums.length*4];
		for(int i =0; i < nums.length; i++){
			int num=nums[i];
			dest[offSet++]=(byte)((num>>24)&0xff);
			dest[offSet++]=(byte)((num>>16)&0xff);
			dest[offSet++]=(byte)((num>>8)&0xff);
			dest[offSet++]=(byte)((num)&0xff);
		}
		return dest;
	}
}
