package chatPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;

public class SignUp extends JFrame implements ActionListener {
	JPanel titlePanel,footPanel,centerPanel;
	Color titlePanelColor=new Color(243, 194, 11);
	JButton clearButton,signUpButton,exitButton,loginButton;
	JLabel userName,passWord,confPassWord,gender;
	JTextField fUserName;
	JPasswordField fPassWord,fConfPassWord;
	JComboBox fGender;
	String [] genderList= {"Male","Female"};
	Connection conn;
	PreparedStatement pstmt;
	String userNameString,passwordString,confPasswordString,genderString;
	
	public SignUp(int width, int height,String title) {
		initialSetup(width,height);
		setTitlePanel(title);
		setCenterPanel();
		setFootPanel();
	}
	
	public void setVisibility() {
		setVisible(true);
	}
	
	public void initialSetup(int width, int height){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width,height);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(20, 47, 74));
		setLayout(new BorderLayout(10,10));
		setResizable(false);
		try {
		conn=DriverManager.getConnection("jdbc:mysql://localhost/my_database","root","");
		}catch (Exception e) {
			System.out.println("An error occurred !");
		}
	}
	
	public void setTitlePanel(String title) {
		titlePanel=new JPanel();
		titlePanel.setBackground(titlePanelColor);
		titlePanel.setPreferredSize(new Dimension(500,60));
		JLabel titleLabel=new JLabel(title);
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font("Arial",Font.BOLD,30));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setVerticalAlignment(JLabel.CENTER);
		titleLabel.setForeground(Color.BLUE);
		titleLabel.setBackground(titlePanelColor);
		titlePanel.add(titleLabel);
		add(titlePanel,BorderLayout.NORTH);
	}
	
	public void setFootPanel() {
		footPanel=new JPanel();
		footPanel.setBackground(titlePanelColor);
		footPanel.setPreferredSize(new Dimension(500,60));
		footPanel.setLayout(new GridLayout(1,3,5,5));
		
		signUpButton=new JButton("SIGN UP");
		styleFootButton(signUpButton);
		
		loginButton=new JButton("LOG IN");
		styleFootButton(loginButton);
		
		clearButton=new JButton("CLEAR");
		styleFootButton(clearButton);
		
		exitButton=new JButton("EXIT");
		styleFootButton(exitButton);
		
		add(footPanel,BorderLayout.SOUTH);
	}
	
	public void styleFootButton(JButton button) {
		button.setBackground(Color.BLACK);
		button.setForeground(Color.GREEN);
		button.setFont(new Font("Arial",Font.BOLD,20));
		button.setFocusable(false);
		button.addActionListener(this);
		footPanel.add(button);
	}
	
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
		
		confPassWord=new JLabel(" Confirm Password ");
		styleCenterLabels(confPassWord,100,180,150,50);
		
		fConfPassWord=new JPasswordField();
		fConfPassWord.setBounds(280,180,150,50);
		centerPanel.add(fConfPassWord);
		
		gender=new JLabel("Gender ");
		styleCenterLabels(gender,100,240,150,50);
		
		fGender=new JComboBox<String>(genderList);
		fGender.setBounds(280,240,150,50);
		fGender.setSelectedItem("Male");
		centerPanel.add(fGender);
		
		add(centerPanel,BorderLayout.CENTER);
		
	}
	
	public void styleCenterLabels(JLabel label, int x, int y, int width, int height) {
		label.setOpaque(true);
		label.setBounds(x,y,width,height);
		label.setAlignmentX(JLabel.CENTER);
		label.setAlignmentY(JLabel.CENTER);
		label.setFont(new Font("Arial",Font.BOLD,15));
		centerPanel.add(label);
	}
	
	public void styleCenterTextField(JTextField tField, int x, int y, int width, int height) {
		tField.setBounds(x,y,width,height);
		centerPanel.add(tField);
	}
	
	public void registerUser() {
		try {
			userNameString=fUserName.getText();
			passwordString=fPassWord.getText();
			confPasswordString=fConfPassWord.getText();
			genderString=fGender.getSelectedItem().toString();
			
			if(userNameString.isBlank() || passwordString.isBlank() || !passwordString.equals(confPasswordString)) {
				JOptionPane.showMessageDialog(this," Registration failed !\nSome fields may be empty\nPlease also ensure passwords match! !!!", "Failure !",JOptionPane.WARNING_MESSAGE);
			}
			else {
				pstmt=conn.prepareStatement("INSERT INTO users (UserName,UserPassWord,UserGender) VALUES (?,?,?)");
				pstmt.setString(1, userNameString);
				pstmt.setString(2, passwordString);
				pstmt.setString(3, genderString);
					
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this,"The user is successfully registered !!!","Success!!!",JOptionPane.INFORMATION_MESSAGE);
			}
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this," Failed registration !!!", "Failure !",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void clearFields() {
		fUserName.setText(""); 
		fPassWord.setText(""); 
		fGender.setSelectedItem("Male");
		fConfPassWord.setText("");
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
			registerUser();
		}
		else if(e.getSource()==loginButton) {
			this.dispose();
			Login login=new Login();
			login.setVisibility();
		}
	}
}
