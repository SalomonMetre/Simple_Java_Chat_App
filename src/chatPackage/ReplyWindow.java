package chatPackage;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class ReplyWindow extends InboxWindow {
	
	public ReplyWindow() {
		super();
		toField.setText(UserClass.answerTo);
		toField.setEditable(false);
		messageArea.setEditable(true);
		inboxButton.setBackground(uniformColor);
		inboxButton.setEnabled(true);
	}
	
	@Override
	public void doMainQuery() {
		
	}
	
	@Override
	public void setLeftPanel() {
		super.setLeftPanel();
		inboxButton.setEnabled(false);
		sendButton.setBackground(uniformColor);
		sendButton.setEnabled(false);
		replyButton.setEnabled(true);
		replyButton.setBackground(Color.BLUE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(e.getSource()==replyButton) {
			sendMessage();
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
