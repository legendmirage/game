package NetworkUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class NTPClient {

	public static double getDelta(){
		try {
			String serverName="1.pool.ntp.org";
		DatagramSocket socket = new DatagramSocket();
		InetAddress address;
			address = InetAddress.getByName(serverName);
		byte[] buf = new NtpMessage().toByteArray();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 123);
		
		NtpMessage.encodeTimestamp(packet.getData(), 40,
			(System.currentTimeMillis()/1000.0) + 2208988800.0);
		
		socket.send(packet);
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		double destinationTimestamp =
			(System.currentTimeMillis()/1000.0) + 2208988800.0;
	    NtpMessage msg = new NtpMessage(packet.getData());
		
		double localClockOffset =
			((msg.receiveTimestamp - msg.originateTimestamp) +
			(msg.transmitTimestamp - destinationTimestamp)) / 2;		
		
		socket.close();
			
		return localClockOffset;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
