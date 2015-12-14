

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddUI extends JFrame{
	
	JTextField userNameInput = new JTextField();
	JButton btnAdd = new JButton();
	AddUI(){
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
			this.getContentPane().setBackground(new Color(17, 213, 244));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int height = this.getHeight(); 
		int width = this.getWidth(); 
		this.setLocation(Client.screenWidth-width/2, Client.screenHeight-height/2);
		this.setVisible(true);
	}
	 
	void jbInit(){
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(400,150);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		this.setTextField();
		this.setButton();
		this.setText();
	}
	
	
	void setTextField(){
		this.userNameInput.setBounds(new Rectangle(210,30,120,30));
		this.getContentPane().add(this.userNameInput);
	}
	
	void setButton(){
		this.btnAdd.setBounds(new Rectangle(140,75,120,30));
		this.btnAdd.setText("Add Friend");
		AddUI temp = this;
		this.btnAdd.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				String friendid = userNameInput.getText();
				SendMessage sendMSG =new SendMessage("Application",friendid);
				temp.dispose();
			}
		});
		this.getContentPane().add(this.btnAdd);
	}

	void setText(){
		JLabel text = new JLabel("Please input friend's ID:");
		this.getContentPane().add(text);
		text.setBounds(50,30,180,30);
	}
}
