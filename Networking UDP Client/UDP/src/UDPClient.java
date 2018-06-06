
import java.net.*;

public class UDPClient {
	
	private static final int DEFAULT_MESSAGE_COUNT = 10; 
	private static final  String DEFAULT_MESSAGE = "ping";
	private static final int READ_TIMEOUT = 1000;
	
	private String hostname; 
	private int port; 

	public UDPClient(String hostname, String port) {
		 this.hostname = hostname; 
		 this.port = Integer.parseInt(port); 
	}

	
	
	/**
	 * Our UDP Ping 
	 */
	public String ping() { 
		
		DatagramSocket clientSocket = null;
		InetAddress IPAddress = null;
		DatagramPacket sendPacket = null;
		
		DatagramPacket receivePacket; 
		
		String response = null; 
		
		try {
			// PREPARE FOR SEND 
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName(hostname);
			
			// Data transfer blocks 
			// byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			
			sendPacket = new DatagramPacket(DEFAULT_MESSAGE.getBytes(), DEFAULT_MESSAGE.length(), IPAddress, port);
			clientSocket.send(sendPacket);
			
			System.out.println("sent message");
			
			// PREPARE FOR RECEIVE BLOCK 
			receivePacket = new DatagramPacket(receiveData, receiveData.length); 
			clientSocket.setSoTimeout(READ_TIMEOUT);
			clientSocket.receive(receivePacket);  //  read response // set a 1 second time 
			System.out.println("Receive returned!");
			
			response = new String(receivePacket.getData());
			// System.out.println("FROM SERVER:" + modifiedSentence);
			
			
		}
		catch(SocketException ex)  {
			System.err.println("ping() experienced SocketException: " + ex);
		}
		catch(Exception ex)  {
			System.err.println("ping() experienced Exception: " + ex);
		}
		finally {
			clientSocket.close();
		}
		
		
		return response; 
		
	}

	public static void main(String[] args) {
	

		if(args.length != 2 )  {
			System.err.println("UDPClient needs a service-host and service-port");
			System.exit(9);
		}
		
		
		System.out.println("Starting the process ...");
		
		String hostname = args[0]; 
		String port = args[1];

		UDPClient client = new UDPClient(hostname, port); 
		
		int count = 0; 
		while(count++ < DEFAULT_MESSAGE_COUNT)  {
			String response = client.ping(); 
			System.out.printf("[%d] Received %s back from %s:%s", count, response, hostname, port);
		
		}
		
	}

}
