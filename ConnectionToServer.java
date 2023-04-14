import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;
/*
 * This is a Java code for handling communication from the client to the server. 
 * It includes a constructor with parameters for connecting to the server and starting a new thread, and a run() method for handling the communication. 
 * The run() method includes a while loop that keeps running while the communication is active. Inside the while loop, it receives a message from the server using the talker.receive() method, 
 * and then checks the message against a series of if else statements to handle various responses from the server. 
 * Depending on the response, it sends a message to the server using the talker.send() method, or performs other actions, such as changing the GUI from login to chat. 
 */

public class ConnectionToServer implements Runnable // handles communication from the client to the server
{
	Talker 	talker;
	Client 	mainClientJFrame;
	
	ConnectionToServer(Talker talker, Client frame)
	{
		this.talker=talker;
		this.mainClientJFrame=frame;
		new Thread(this).start();
	}
	
	ConnectionToServer(String domain, int port, String id,  Client frame) throws IOException, UnknownHostException
	{
		talker=new Talker(domain,port,id);	
		this.mainClientJFrame=frame;
		new Thread(this).start();
	}
	@Override
	public void run() //short synchronous exchange with server to either register or to log in if server accepted account
	{
		while(true)
		{
			try 
			{
				String str;
				str = talker.recieve();
			
				if(str.equals("Welcome! Enter Username and Password"))
				{
					talker.send(mainClientJFrame.getUsernameTextField().getText().trim() + ":" + mainClientJFrame.getPasswordTextField().getText().trim());
				}
				else if(str.equals("Username or Password incorrect"))
				{
					//Todo JDialog
				}
				else if(str.equals("Welcome! Enter Username"))
				{
					talker.send(mainClientJFrame.getUsernameTextField().getText().trim());
				}
				else if(str.equals("Username taken: try something else or logging in instead"))
				{
					//Deal with it
				}
				else if(str.equals("What Password?"))
				{
					talker.send(mainClientJFrame.getPasswordTextField().getText().trim());
				}
				else if(str.equals("User Logged in"))
				{
					System.out.println(" yay ");
					SwingUtilities.invokeLater( new Runnable()            
					{       
						public void run()            
						{    
							mainClientJFrame.changeGUIFromLoginToChat();
						}
					});
					
				}
			} 
			catch(SocketException s)
			{
				System.exit(1); // todo figure out a better way to stop this thread when the socket is closed
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
	}	
}
