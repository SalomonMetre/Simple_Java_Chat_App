package chatPackage;

import java.awt.Color;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InboxWindow extends MessageWindow{
	
	ResultSet rs;
	public InboxWindow() {
		super();
		toField.setEditable(false);
		messageArea.setEditable(false);
		doMainQuery();
	}
	
	public void doMainQuery() {
		try {
			pStatement=conn.prepareStatement("SELECT * FROM messages WHERE Receiver=?",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			pStatement.setString(1, UserClass.currentUser);
			rs=pStatement.executeQuery();
			if(rs.next()) {
				toField.setText("From : "+rs.getString("Sender"));
				messageArea.setText(rs.getString("Message"));
				UserClass.answerTo=rs.getString("Sender");
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void setLeftPanel() {
		super.setLeftPanel();
		sendButton.setBackground(uniformColor);
		replyButton.setEnabled(true);
		inboxButton.setBackground(Color.BLUE);
	}
	
	@Override
	public void setCenterPanel() {
		super.setCenterPanel();
		prevButton.setEnabled(true);
		nextButton.setEnabled(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exitButton) {
			dispose();
			SignUp signUp=new SignUp(500, 500, "Sign-Up");
			signUp.setVisibility();
		}
		else if(e.getSource()==prevButton) {
			try {
				if(rs.relative(-1)) {
					toField.setText("From : "+rs.getString("Sender"));
					messageArea.setText(rs.getString("Message"));
					UserClass.answerTo=rs.getString("Sender");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==nextButton) {
			try {
				if(rs.next()) {
					toField.setText("From : "+rs.getString("Sender"));
					messageArea.setText(rs.getString("Message"));
					UserClass.answerTo=rs.getString("Sender");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==replyButton) {
			dispose();
			ReplyWindow replyWindow=new ReplyWindow();
			replyWindow.setVisibility();
			replyWindow.makeConnection();
		}
		else if(e.getSource()==sendButton) {
			dispose();
			MessageWindow messageWindow=new MessageWindow();
			messageWindow.setVisibility();
			messageWindow.makeConnection();
		}
	}
	
}
