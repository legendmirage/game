package server;

import java.io.IOException;
import java.util.HashMap;

import NetworkUtil.ConstantsAndUtil;
import NetworkUtil.NTPClient;

import org.newdawn.slick.SlickException;

import com.esotericsoftware.kryonet.Server;

public class ServerRunner {
	public static Integer highestID = 1;
	public static HashMap<Integer, ServerThread> idToServer = new HashMap<Integer, ServerThread>();
	public static Integer noPartner = 0;
	public static final double delta=NTPClient.getDelta();
	public static ThreadGroup serverThreads= new ThreadGroup("serverthreads");
	
	public static void main(String[] args) throws IOException, SlickException {
		
		Server server = new Server(65536,16384);
		server.start();
		server.getKryo().register(byte[].class);
		server.bind(ConstantsAndUtil.serverTCP, ConstantsAndUtil.serverUDP);
		server.addListener(new GameListener());
		

	}

}
