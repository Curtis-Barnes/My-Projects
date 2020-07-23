/**
 * Curtis Barnes
 *
 *This class creates a a basic server. 
*/
import java.net.*;
import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.security.SecureRandom;


public class simpleServer{

	public static void main(String[] args){
		
		try {
		
			//Server is waiting for the connection from clients 
			ServerSocket echoServer = new ServerSocket(0); //Creates a new ServerSocket that binds to the first available port
			System.out.println(echoServer.getLocalPort()); //Prints out the port that the server is using
		

			//while(true){
				System.out.println("server is waiting for the the connection...");
				Socket client = echoServer.accept(); //Variable references client once it connects to the server
				System.out.println("Here comes a request."); 
				//PrintWriter writer = new PrintWriter(client.getOutputStream(), true); //Creates printWriter to send information back to the client
				int p = getP();
				//System.out.println(Integer.toString(p));
				BigInteger g = getG(p);

				int n1 = 0; 
				SecureRandom rand = new SecureRandom();
				n1 = rand.nextInt(p - 1) + 1; 
				int b = -1;
				BigInteger a = (g.pow(n1).mod(BigInteger.valueOf(p)));

				System.out.println("g: " + g);
				Writer writer = new Writer(client, "Server");
				writer.AddOutput("-------------------SERVER SENDING-----------------------");
				writer.AddOutput("P: " + p);
				writer.sendPublic(Integer.toString(p));
				writer.AddOutput("G: " + g);
				writer.sendPublic("" + g);
				
				
				writer.AddOutput("A: " + a);
				//System.out.println("A: " + a);
				writer.sendPublic("" + a);
				//writer.WriteToFile();
			
				Listen thread = new Listen(client, "Server", p, g);
				thread.start();
				while(b == -1){
					BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
					Thread.sleep(200);
					String data = reader.readLine();
					b = Integer.parseInt(data);
					
				}
				
				BigInteger key = (BigInteger.valueOf(b).pow(n1).mod(BigInteger.valueOf(p)));
				thread.setKey("" + key);
				System.out.println("Key: " + key);
				writer.SetKey("" + key);
				//System.out.println(Integer.toString(g));	  
				while(true){
					
					
					System.out.println("Waiting for input:");
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					String inputString = reader.readLine();
					
					writer = new Writer(client, "Server");
					writer.SetKey("" + key);
					writer.AddOutput("-------------------SERVER SENDING-----------------------");
					writer.AddOutput("Input: " + inputString);
					writer.WriteToFile();
					writer.sendMessage(inputString);
				}
				
				//BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));



				//writer.println("Hello, " + client.getInetAddress().getHostName() + "\nYour IP address is " + 					client.getInetAddress().getHostAddress()); //Prints clients DNS name and IP address
				//client.close(); //Disconnects the client
 
			//}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		 
	}

	public static int getP(){
		try{
			int num = 0; Random rand = new Random();
			num = rand.nextInt(1000) + 1; 
			while (!isPrime(num)) {
				num = rand.nextInt(1000) + 1; 
			}
			return num;
		}catch(Exception ex){
			ex.printStackTrace();
			return -1;
		}
	}

	private static boolean isPrime(int inputNum) { 
		if (inputNum <= 3 || inputNum % 2 == 0){
			 return inputNum == 2 || inputNum == 3;
		}
		int divisor = 3; 
		while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0)){
			divisor += 2;	
		} 
		return inputNum % divisor != 0; 
	} 

	public static BigInteger getG(int p){
		try{
			BigInteger[] results;
			boolean duplicateFound = false;
			for(int r = 2; r < p; r++){
				//System.out.println("r: " + Integer.toString(r));
				results = new BigInteger[p-1];
				for(int x = 0; x < results.length; x++){
				
					BigInteger currResult = (BigInteger.valueOf(r).pow(x).mod(BigInteger.valueOf(p)));

					results[x] = currResult;
					//System.out.println("" + results[x]);
					for(int i = 0; i < x; i++){
						//System.out.println("" + results[i]);
						if(currResult.equals(results[i])){
							
							//System.out.println("DUPLICATE FOUND ");
							duplicateFound = true;
							//break;
						}
					}
					if(duplicateFound == true){
						//System.out.println("HERE ");
						break;
					}else{
						results[x] = currResult;
					}	
				}
				if(duplicateFound == false){
					return BigInteger.valueOf(r);
				}else{
					duplicateFound = false;
				}
				
			}
			return BigInteger.valueOf(-1);
		}catch(Exception ex){
			ex.printStackTrace();
			return BigInteger.valueOf(-1);
		}
	}

  
}


