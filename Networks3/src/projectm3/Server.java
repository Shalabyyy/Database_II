package projectm3;
import java.io.*; 
import java.util.*; 
import java.net.*; 

public class Server implements Runnable{

	public final static int port=1234;
	public String names[];
	public int index;
	public Server(){
		this.names=new String[10];
		this.index=0;
		for(int i=0; i<10;i++){
			names[i]="!"+i;
			System.out.println(names[i]);
		}

	}
	public static void main(String args[]) throws IOException{

		Server s1= new Server(); //edit
		ServerSocket ss= new ServerSocket(port);
		Socket s;
		while(true){
			s = ss.accept(); 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			System.out.println("What is Your Name?");
			Thread t;
			String received = dis.readUTF(); 
			String name="blank";
			if(received.charAt(0)=='#'){
				name=received.substring(1);
				s1.names[s1.index]=name;
				s1.index++;
				t= new Thread(name); //int thread
				t.start();
			}
			else if(received.equals("BYE")){
				System.out.println("logged out on Server 1");
				dos.writeUTF("BYE");
				dos.close();
				dis.close();
				s.close();
				break; 
			}
			if(received.contains("@")){
				StringTokenizer st = new StringTokenizer(received, "@"); 
                String MsgToSend = st.nextToken(); 
                String recipient = st.nextToken();
                for(int i=0; i<10;i++){
    				if(s1.names[i].equals(recipient)){
    					 dos.writeUTF(s1.names[i]+" : "+MsgToSend); 
                         break;
    				}
    			}
                for(int i=0; i<10;i++){
    				if(i==9){
    					System.out.println("not found maybe on another server");
    				}
    			}
                
                
			}
			dis.close(); 
            dos.close(); 
			

		}

	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
