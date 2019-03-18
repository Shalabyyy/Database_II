package projectm3;

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

public class Client  
{ 
	final static int ServerPort = 1234; 
	static boolean flag=true;
	public static void main(String args[]) throws UnknownHostException, IOException  
	{ 
		Client c= new Client();
    	c.addClient();
		
	} 
	public void addClient() throws IOException,UnknownHostException{
		
		Scanner scn = new Scanner(System.in); 

		// getting localhost ip 
		InetAddress ip = InetAddress.getByName("localhost"); 

		// establish the connection 
		Socket s = new Socket(ip, ServerPort); 

		// obtaining input and out streams 
		DataInputStream dis = new DataInputStream(s.getInputStream()); 
		DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

		// sendMessage thread 
		Thread sendMessage = new Thread(new Runnable()  
		{ 
			@Override
			public void run() { 
				while (true) { 

					// read the message to deliver. 
					String msg = scn.nextLine(); 

					try {
						if(flag){
							// write on the output stream 
							dos.writeUTF(msg); 
						}
						else{
							dos.close();
							dis.close();
							s.close();
							break;
						}
					} catch (IOException e) { 
						e.printStackTrace(); 
					} 
				} 
			} 
		}); 

		// readMessage thread 
		Thread readMessage = new Thread(new Runnable()  
		{ 
			@Override
			public void run() { 

				while (true) { 
					try { 
						// read the message sent to this client 
						String msg = dis.readUTF();
						if(msg.contains("BYE")){
							flag=false;
							dos.close();
							dis.close();
							s.close();
							break;
						}
						else{
							System.out.println(msg); }
					} catch (IOException e) { 

						e.printStackTrace(); 
					} 
				} 
			} 
		}); 

		sendMessage.start(); 
		readMessage.start(); 

	}
} 
