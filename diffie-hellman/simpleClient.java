/**
 * Curtis Barnes
 *
 *This class creates a basic client that can connect to a server. 
*/
import java.net.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.security.SecureRandom;

public class simpleClient{

	public static void main(String[] args){
		if(args.length < 2){ //If a text file hasn't been specified
      			System.err.println("Requires name and port number");
      			return;
    		}else{

			try{
				
				byte[] keyBytes = {1,2,3,4,5};
				InetAddress IP = InetAddress.getByName(args[0]); //Gets the clients Inet address
				Socket me = new Socket(IP, Integer.parseInt(args[1])); //Creates a socket for the client
				//BufferedReader reader = new BufferedReader(new InputStreamReader(me.getInputStream())); //Creates reader object to recieve information from the server
				
				int p = -1;
				BigInteger g = BigInteger.valueOf(-1);
				int a = -1;
				BigInteger key = BigInteger.valueOf(-1);
				BigInteger b = BigInteger.valueOf(-1);

				Listen thread = new Listen(me, "Client");
				thread.start();
				
				while(key.equals(BigInteger.valueOf(-1))){
					
					while(p == -1){
						Thread.sleep(10);
						p = thread.getP();
						
					}
					System.out.println("P: " + p);
					while(g == BigInteger.valueOf(-1)){
						Thread.sleep(10);
						g = thread.getG();
					}
					System.out.println("G: " + g);
					while(a == -1){
						Thread.sleep(10);
						a = thread.getA();
					}

					System.out.println("A: " + a);
					
					int n2 = 0; 
					SecureRandom rand = new SecureRandom();
					n2 = rand.nextInt(p - 1) + 1; 
					b = (g.pow(n2).mod(BigInteger.valueOf(p)));
					key = (BigInteger.valueOf(a).pow(n2).mod(BigInteger.valueOf(p)));
					System.out.println("Key: " + key);
					
				}

				Writer writer = new Writer(me, "Client");
				writer.SetKey("" + key);
				thread.setKey("" + key);
				writer.AddOutput("B: " + b);
				writer.sendPublic("" + b);
				writer.WriteToFile();
				
				while(true){
					//PrintWriter writer = new PrintWriter(me.getOutputStream(), true); //Creates a writer for the client class				
				

					System.out.println("Waiting for input:");
					writer = new Writer(me, "Client");
					writer.SetKey("" + key);
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					
					String inputString = reader.readLine();
					writer.AddOutput("-------------------CLIENT SENDING-----------------------");
					
					
					writer.AddOutput("Input: " + inputString);
					writer.WriteToFile();
					writer.sendMessage(inputString);
					
					//byte[] inputBytes = inputString.getBytes();
					//System.out.println(new String(inputBytes));
					//byte[] encrypt = new byte[inputBytes.length];
					//int keyIndex = 0;
					//System.out.println("length of input: " + inputBytes.length);
					//for(int i =0; i < inputBytes.length; i++){
						
						//byte encryptChar = (byte)(inputBytes[i] ^ keyBytes[i % (keyBytes.length)]);
						//encrypt[i] = encryptChar;
						
					//}
					
					//int encrypt = (inputString.getBytes()[0] ^ key.getBytes()[0]);
					
					//System.out.println(new String(encrypt));

					//String encryptedMessage = new String(encrypt);
					//System.out.println(encryptedMessage);
					//writer.println(encryptedMessage);
					//System.out.println(inputString);

					////while(true){
						//reader = new BufferedReader(new InputStreamReader(me.getInputStream()));
						//String response = reader.readLine(); //Reads a line of text from the server
						//if(response != null){
							//System.out.println(response); //Prints out the line of text recieved from the server
							////break;		
						//}
						
					////}
				

					//String response = reader.readLine(); //Reads a line of text from the server
					//while(response != null){ //While there are more lines to read from the server
						//System.out.println(response); //Prints out the line of text recieved from the server
						//response = reader.readLine(); //Reads a line of text from the server
					//}
					
					//me.close();  //Disconnects the client from the server by closing the socket
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

  
}

class Listener extends Thread{
	private Socket me;
	//private DatagramPacket dp;
	//byte[] rec;
	//String data = "";
	
	public Listener(Socket me){
		this.me = me;
		//this.dp = dp;
			
	}

	public void run(){
		try{
			while(true){
				BufferedReader reader = new BufferedReader(new InputStreamReader(me.getInputStream()));
				String response = reader.readLine(); //Reads a line of text from the server
				if(response != null){
					System.out.println(response); //Prints out the line of text recieved from the server
							//break;		
				}
			}
		}catch(Exception e){
			System.err.println(e);
		}
	}


}
