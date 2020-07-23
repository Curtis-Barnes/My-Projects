import java.net.*;
import java.io.*;
import java.math.BigInteger;

public class Listen extends Thread{
	private Socket client;
	//private DatagramPacket dp;
	//byte[] rec;
	private String expected = "";
	private String reciever;
	private int p;
	private BigInteger g;
	private int a;
	private byte[] key = {-1};
	
	public Listen(Socket client, String reciever){
		this.client = client;
		this.p = -1;
		this.g = BigInteger.valueOf(-1);
		this.a = -1;
		this.reciever = reciever;
		//this.dp = dp;
			
	}
	
	public Listen(Socket client, String reciever, int p, BigInteger g){
		this.client = client;
		this.p = p;
		this.g = g;
		this.a = -1;
		this.reciever = reciever;
		//this.dp = dp;
			
	}

	@Override
	public void run(){
		try{
			
			while(true){
					try{
						
							BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
							reader.ready();
							String data = reader.readLine();
							Writer writer = new Writer(client, reciever);
							
							
							if(data != null){
								
								if(key[0] == -1){

									
									System.out.println(data);
	
									writer.AddOutput("-------------------MESSAGE RECIEVED-----------------------");
									if(p == -1){
										//System.out.println("P: " + data);
										p = Integer.parseInt(data);
										writer.AddOutput("P: " + data);
										writer.WriteToFile();
									}else if(g == BigInteger.valueOf(-1)){
										//System.out.println("G: " + data);
										g = new BigInteger(data);
										writer.AddOutput("G: " + data);
										writer.WriteToFile();
									}else if(a == -1){
										//System.out.println("A: " + data);
										a = Integer.parseInt(data);
										writer.AddOutput("A: " + data);
										writer.WriteToFile();
									}
									


								
								}else{
									writer.AddOutput("-------------------MESSAGE RECIEVED-----------------------");
									writer.AddOutput("Message Recieved: " + data);
									System.out.println(data);

									byte[] inputBytes = data.getBytes();
									//byte[] keyBytes = {1,2,3,4,5};
									byte[] decrypt = new byte[inputBytes.length];
									String encrypt = "";
									int keyIndex = 0;
									for(int i =0; i < inputBytes.length; i++){
							
										if(keyIndex == (key.length)){
											keyIndex = 0;
										}
										byte encryptChar = (byte)(inputBytes[i] ^ key[i % (key.length)]);
										//System.out.println(encryptChar);
										decrypt[i] = encryptChar;
										keyIndex++;
									}
								
									//decrypt = encrypt.getBytes();
									String s = new String(decrypt);
									writer.AddOutput("Decrypted Message: " + s);
									writer.WriteToFile();
									//System.out.println(s);
									//System.out.println(new String(decrypt));
									
								}
								

							}
						
			
				
						
						
					}catch(Exception e){
						e.printStackTrace();
						break;
					}
							
				}
		}catch(Exception e){
			System.err.println(e);
		}
	}
	public void setExpected(String message){
		expected = message;
	}

	public void setKey(String key){
		this.key = key.getBytes();
	}

	public int getA(){
		return a;
	}

	public BigInteger getG(){
		return g;
	}

	public int getP(){
		return p;
	}

	


	

	

}
