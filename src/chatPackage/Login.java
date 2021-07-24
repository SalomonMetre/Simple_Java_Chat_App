package chatPackage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.*;
import java.util.Arrays;


public class Login extends SignUp{
	ResultSet rs;
	
	Login(){
		super(500,440,"Login");
		signUpButton.setEnabled(false);
	}
	
	@Override
	public void setCenterPanel() {
		centerPanel=new JPanel();
		centerPanel.setLayout(null);
		
		userName=new JLabel("Username ");
		styleCenterLabels(userName,100,60,150,50);
		
		fUserName=new JTextField();
		styleCenterTextField(fUserName, 280,60,150,50);
		
		passWord=new JLabel("Password ");
		styleCenterLabels(passWord,100,120,150,50);
		
		fPassWord=new JPasswordField();
		fPassWord.setBounds(280,120,150,50);
		centerPanel.add(fPassWord);
		
		add(centerPanel,BorderLayout.CENTER);
	}
	
	@Override
	public void clearFields() {
		fUserName.setText(""); 
		fPassWord.setText(""); 
	}
	
	public void loginUser() {
		try {
			pstmt=conn.prepareStatement("SELECT * FROM users WHERE UserName=? AND UserPassword=?");
			pstmt.setString(1, fUserName.getText());
			pstmt.setString(2, fPassWord.getText());
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
					JOptionPane.showMessageDialog(this, "Successful login !", "Success !",JOptionPane.INFORMATION_MESSAGE);
					UserClass.currentUser=rs.getString("UserName");
					this.dispose();
					MessageWindow messageWindow=new MessageWindow();
					messageWindow.setVisibility();
					messageWindow.makeConnection();
			}
			else
				JOptionPane.showMessageDialog(this, "Unknown user ! Please check again \nyour user name and your password", "Warning !",JOptionPane.WARNING_MESSAGE);
		}catch(Exception e) {
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==exitButton)
			dispose();
		else if(e.getSource()==clearButton) {
			clearFields();
		}
		else if(e.getSource()==signUpButton) {
			this.dispose();
			SignUp signUp=new SignUp(500, 500, "Sign Up");
			signUp.setVisibility();
		}
		else if(e.getSource()==loginButton) {
			loginUser();
		}
	}
}
