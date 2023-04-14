import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/*
* 
* This code defines a GUI client in Java using Swing. The client allows users to log in or register, and then presents a list of buddies that can be added. 
* The window layout is created using a FlowLayout. Two text fields are added to accept login credentials, which must not be empty. A login button sends a short synchronous message to the server using an instance of ConnectionToServer. A register button sends a similar message to the server to initiate account creation. 
* There is an implementation of the windowClosing method which gracefully closes the socket if it is open. The changeGUIFromLoginToChat method switches the GUI to display the chat interface with a buddy list and an "Add Buddy" button. Pressing the "Add Buddy" button prompts for a username and sends a command to the server to forward the request. 
* The main method initializes the talker and sets up the user interface. 
* 
*/

public class Client extends JFrame
{
	private JTextField 		usernameTextField;
	private JTextField 		passwordTextField;
	Talker 					myTalker;
	ConnectionToServer		myCts;
	public Client() 
	{
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) 
		    {
		        try 
		        {
		        	if(myTalker!=null)
		        	{	
		        		myTalker.socket.shutdownOutput();
		        		myTalker.socket.close();
		        	}
					
					e.getWindow().dispose();
				} catch (IOException e1) 
		        {
					System.out.println("Error when closing socket");
					System.exit(1);
				
				}
		        
		    }
		});
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		setUsernameTextField(new JTextField());
		getContentPane().add(getUsernameTextField());
		getUsernameTextField().setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username");
		getContentPane().add(lblNewLabel);
		
		setPasswordTextField(new JTextField());
		getContentPane().add(getPasswordTextField());
		getPasswordTextField().setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		getContentPane().add(lblNewLabel_1);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//construct CTS. use it to attempt to log in to an account using a short synchronous message.
				try 
				{	
					if(usernameTextField.getText().trim().equals(""))
					{
						usernameTextField.setText("CAN'T BE EMPTY");
						usernameTextField.setSelectedTextColor(Color.RED);
						usernameTextField.requestFocus();
						usernameTextField.selectAll();
					}
					else if(passwordTextField.getText().trim().equals(""))
					{
						passwordTextField.setText("CAN'T BE EMPTY");
						passwordTextField.setSelectedTextColor(Color.RED);
						passwordTextField.requestFocus();
						passwordTextField.selectAll();
					}
					else
					{
						ConnectionToServer myCts= new ConnectionToServer(myTalker, Client.this);
						myTalker.send("login");
					}
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		getContentPane().add(loginButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//construct CTS. use it to attempt to create an account using a short synchronous massage.
				try 
				{	
					if(usernameTextField.getText().trim().equals(""))
					{
						usernameTextField.setText("CAN'T BE EMPTY");
						usernameTextField.setSelectedTextColor(Color.RED);
						usernameTextField.requestFocus();
						usernameTextField.selectAll();
					}
					else if(passwordTextField.getText().trim().equals(""))
					{
						passwordTextField.setText("CAN'T BE EMPTY");
						passwordTextField.setSelectedTextColor(Color.RED);
						passwordTextField.requestFocus();
						passwordTextField.selectAll();
					}
					else
					{
						myCts= new ConnectionToServer(myTalker, Client.this);
						myCts.talker.send("register");
					}
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				
			}
		});
		getContentPane().add(registerButton);
		pack();
		setVisible(true);
		
	}
	public void changeGUIFromLoginToChat()
	{
		getContentPane().removeAll();
		
		DefaultListModel listModel= new DefaultListModel();
		listModel.addElement("Placeholder");
	    listModel.addElement("For");
	    listModel.addElement("Buddies");
	    JList list=new JList(listModel);
	    
	   
	    JButton addBuddyButton= new JButton("Add Buddy");
	    addBuddyButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				 //send a command to the server asking the server to forward
		        //the request to the User having that username
				
				String m = JOptionPane.showInputDialog("Buddy's Username?");
		        System.out.println(m+ "this does nothing yet...");
		       /*
		        try {
					myCts.talker.send("add buddy");
					myCts.talker.send(m);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		       */
			}
		});
	    
	    add(list);
	    add(addBuddyButton);
	    pack();
	    repaint();
		
		
		
	}
	public static void main(String[] args) 
	{
			try 
			{
				Client myClient=new Client();
				myClient.myTalker= new Talker("127.0.0.1", 3737, "client1");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
	}

	public JTextField getPasswordTextField() {
		return passwordTextField;
	}

	public void setPasswordTextField(JTextField passwordTextField) {
		this.passwordTextField = passwordTextField;
	}

	public JTextField getUsernameTextField() {
		return usernameTextField;
	}

	public void setUsernameTextField(JTextField usernameTextField) {
		this.usernameTextField = usernameTextField;
	}
		
}
