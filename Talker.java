import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/*
* This is a Java class named "Talker" that allows communication between a client and a server via a socket connection.
* The class contains two constructors: One takes a Socket object and initializes the BufferedReader and DataOutputStream objects to allow for receiving and sending data between the client and server. 
* The other constructor takes a string representing the domain the client should connect to, an integer port number, and a string id that represents the identifier for the client. 
* This constructor sets up the socket connection and initializes the BufferedReader and DataOutputStream objects.

* The Talker class has four methods: send(), receive(), receiveMessage() and a constructor. 
* The send() method takes a string message and writes it to the DataOutputStream object, sending it to the server. 
* The receive() method reads a string from the BufferedReader object, representing a message received from the server. 
* The receiveMessage() method is the same as the receive() method, but it prints the received message to the console.


* Overall, this class allows for sending and receiving messages between a client and server using socket communication.
 */

public class Talker
{
	
	BufferedReader 		br;
	DataOutputStream	dos;
	String 				id;
	Socket 				socket;
	
	Talker(Socket socket) throws IOException//for server
	{
		id="pending";
		dos = new DataOutputStream (socket.getOutputStream());
	    br  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	Talker(String domain, int port, String id) throws UnknownHostException, IOException //for client
	{
		this.id=id;

		socket = new Socket(domain, port);
		dos  = new DataOutputStream(socket.getOutputStream());
		br   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	};
	
	public void send(String message) throws IOException
	{
		message=message+"\n";
		dos.writeBytes(message);
		System.out.println("ID "+ id + " Message Sent: " + message);
	}
	
	public String recieve() throws IOException 
	{
		String s = br.readLine();
		System.out.println("ID "+ id + " Message Recieved: " + s);
		return s;
	}
	
	public void recieveMessage() throws IOException 
	{
		String s;
		s=br.readLine();
		System.out.println("ID "+ id + " Message Recieved: " + s);
	}
	
	
	
}
