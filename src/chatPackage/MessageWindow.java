package chatPackage;
import javax.swing.*;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageWindow extends JFrame implements ActionListener{
	
	JPanel topPanel,centerPanel,leftPanel,bottomPanel;
	JButton sendButton,inboxButton,replyButton,prevButton,nextButton,exitButton;
	JLabel title,userLabel;
	JTextArea messageArea;
	JTextField toField;
	Color uniformColor=new Color(62, 63, 97);
	Connection conn;
	PreparedStatement pStatement;
	
	public MessageWindow() {
		setOtherInitialDetails();
		setTopPanel();
		setLeftPanel();
		setCenterPanel();
	}
	
	public void setVisibility() {
		setVisible(true);
	}
	
	public void makeConnection() {
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost/my_database","root","");
		}catch (Exception e) {
				System.out.println("An error occurred !");
		}
	}
	
	public void sendMessage() {
		try {
			pStatement=conn.prepareStatement("INSERT INTO messages (Sender,Message,Receiver) VALUES (?,?,?)");
			pStatement.setString(1, UserClass.currentUser);
			pStatement.setString(2, messageArea.getText());
			pStatement.setString(3, toField.getText());
			
			//executing the query
			pStatement.executeUpdate();
			JOptionPane.showMessageDialog(this, "The message was successfully sent to "+toField.getText(),"Success!",JOptionPane.INFORMATION_MESSAGE);
			
			//clearing the message fields
			toField.setText("");
			messageArea.setText("");
		
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Failure in sending the message","Failure!",JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void setOtherInitialDetails() {
		setSize(600,400);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10,10));
		setBackground(uniformColor);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setTopPanel() {
		topPanel=new JPanel();
		topPanel.setPreferredSize(new Dimension(100,60));
		topPanel.setBackground(uniformColor);
		
		title=new JLabel(" MINI CHAT ");
		title.setFont(new Font("Arial",Font.BOLD,25));
		title.setOpaque(false);
		title.setForeground(Color.WHITE);
		
		topPanel.add(title);
		add(topPanel,BorderLayout.NORTH);
	}
	
	public void styleLeftButton(JButton button ) {
		button.setForeground(Color.WHITE);
		button.setFocusable(false);
		button.setBackground(uniformColor);
		button.setFont(new Font("Arial",Font.PLAIN,18));
		button.addActionListener(this);
		leftPanel.add(button);
	}
	
	public void setLeftPanel() {
		leftPanel=new JPanel(new GridLayout(3,1,10,20));
		leftPanel.setPreferredSize(new Dimension(200,100));
		//leftPanel.setBackground(new Color(78, 149, 12));
		
		sendButton=new JButton("Send");
		styleLeftButton(sendButton);
		sendButton.setBackground(Color.BLUE);
		
		inboxButton=new JButton("Inbox");
		styleLeftButton(inboxButton);
		
		replyButton=new JButton("Reply");
		styleLeftButton(replyButton);
		replyButton.setEnabled(false);
		
		add(leftPanel,BorderLayout.WEST);
	}
	
	public void setCenterPanel() {
		centerPanel=new JPanel();
		centerPanel.setLayout(null);
		//centerPanel.setBackground(new Color(78, 149, 12));
		
		userLabel=new JLabel("👤 "+UserClass.currentUser);
		userLabel.setOpaque(true);
		userLabel.setBounds(260,0,120,50);
		userLabel.setForeground(Color.WHITE);
		userLabel.setBackground(Color.BLUE);
		
		toField=new JTextField();
		toField.setBounds(0,60,380,30);
		toField.setBackground(Color.BLACK);
		toField.setForeground(Color.WHITE);
		
		messageArea=new JTextArea();
		messageArea.setBackground(uniformColor);
		messageArea.setForeground(Color.WHITE);
		messageArea.setBounds(0,100,380,150);
		messageArea.setCaretColor(Color.WHITE);
		messageArea.setFont(new Font("Aria",Font.PLAIN,16));
		
		prevButton=new JButton("Previous");
		prevButton.setBounds(20,260,100,30);
		prevButton.setForeground(Color.WHITE);
		prevButton.setBackground(Color.BLACK);
		prevButton.setEnabled(false);
		prevButton.addActionListener(this);
		prevButton.setFocusable(false);
		
		nextButton=new JButton("Next");
		nextButton.setBounds(140,260,100,30);
		nextButton.setForeground(Color.WHITE);
		nextButton.setBackground(Color.BLACK);
		nextButton.setEnabled(false);
		nextButton.addActionListener(this);
		nextButton.setFocusable(false);
		
		exitButton=new JButton("Exit");
		exitButton.setBounds(260,260,100,30);
		exitButton.setForeground(Color.WHITE);
		exitButton.setBackground(Color.BLACK);
		exitButton.setFocusable(false);
		exitButton.addActionListener(this);
		
		centerPanel.add(userLabel);
		centerPanel.add(messageArea);
		centerPanel.add(toField);
		centerPanel.add(prevButton);
		centerPanel.add(nextButton);
		centerPanel.add(exitButton);
		
		add(centerPanel,BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sendButton) {
			sendMessage();
		}
		else if(e.getSource()==exitButton) {
			dispose();
			SignUp signUp=new SignUp(500, 500, "Sign-Up");
			signUp.setVisibility();
		}
		else if(e.getSource()==inboxButton) {
			dispose();
			InboxWindow inboxWindow=new InboxWindow();
			inboxWindow.setVisibility();
			inboxWindow.makeConnection();
			inboxWindow.doMainQuery();
		}
		
	}
	
	
}
