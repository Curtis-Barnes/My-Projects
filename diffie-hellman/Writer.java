/**
 * Curtis Barnes
 *
 *This class creates a basic client that can connect to a server. 
*/
import java.net.*;
import java.io.*;
import java.math.*;

public class Writer{
	private Socket client;
	private String sender;
	private byte[] keyBytes;
	public static String[] output;
	public Writer(Socket client, String sender){
		this.client = client;
		this.sender = sender;
			
	}

	public void sendMessage(String message){
		
			try{
				
				//byte[] keyBytes = {1,2,3,4,5}; //CHANGE LATER TO IMPLEMENT GENERATED KEY
			
					PrintWriter writer = new PrintWriter(client.getOutputStream(), true); //Creates a writer for the client class
					
					byte[] inputBytes = message.getBytes();
					System.out.println(new String(inputBytes));
					byte[] encrypt = new byte[inputBytes.length];
					int keyIndex = 0;
					System.out.println("length of input: " + inputBytes.length);
					for(int i =0; i < inputBytes.length; i++){
						
						byte encryptChar = (byte)(inputBytes[i] ^ keyBytes[i % (keyBytes.length)]);
						encrypt[i] = encryptChar;
						
					}					

					String encryptedMessage = new String(encrypt);
					AddOutput("Encrypted Message: " + encryptedMessage);
					WriteToFile();
					System.out.println(encryptedMessage);
					writer.println(encryptedMessage);
					
				
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}

	public void sendPublic(String message){
		
			try{
				
				
					PrintWriter writer = new PrintWriter(client.getOutputStream(), true); //Creates a writer for the client class
					
					byte[] inputBytes = message.getBytes();
					System.out.println(new String(inputBytes));
					Thread.sleep(200);
					writer.println(new String(inputBytes));
					writer.checkError();
					
				
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}

	public void AddOutput(String message){
		if(this.output == null){
			this.output = new String[1];
			this.output[0] = message;
		}else{
			String[] copy = new String[this.output.length + 1];
			for(int i = 0; i < this.output.length; i++){
				copy[i] = this.output[i];
			}
			copy[copy.length -1] = message;
			this.output = copy;
		}
	}

	public void WriteToFile(){
		try{
			PrintWriter fileWriter = new PrintWriter("Output" + sender + ".txt", "UTF-8");
			for(int i = 0; i < this.output.length; i++){
				fileWriter.println(this.output[i]);
			}
			fileWriter.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void SetKey(String message){
		keyBytes = message.getBytes();
	}

	
}

	

