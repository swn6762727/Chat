import java.awt.*;
import javax.swing.*;

public class AddRequestUI extends JFrame{
	JButton btnAccept = new JButton("Accept");
	JButton btnRefuse = new JButton("Refuse");
	JPanel txtPanel;
	String strangerID;
	String strangername;
	String UserPath;
	AddRequestUI(String ID,String name,String path){
		strangerID = ID;
		strangername=name;
		UserPath=path;
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.setTitle(ClientUI.ClientID);
		try {
			jbInit();
			this.getContentPane().setBackground(new Color(80, 80, 80));
			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	void jbInit(){
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(400,150);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		this.setButton();
		this.setText();
		int height = this.getHeight(); 
		int width = this.getWidth(); 
		this.setLocation(Client.screenWidth-width/2, Client.screenHeight-height/2);
	}
	
	void setButton(){
		this.btnAccept.setBounds(new Rectangle(80,80,80,30));
		this.btnRefuse.setBounds(new Rectangle(240,80,80,30));
		this.btnAccept.addActionListener((event)->{
			SendMessage sendMSG =new SendMessage("Accept",strangerID);
			FriendPanel friendpanel=new FriendPanel(UserPath,strangerID,strangername,ClientUI.friendlist.friendlistnum,ClientUI.ClientID);
			ClientUI.AddNewCard(friendpanel);
			this.dispose();
		});
		this.btnRefuse.addActionListener((event)->{
			SendMessage sendMSG =new SendMessage("Refuse",strangerID) ;
			this.dispose();
		});
		this.getContentPane().add(this.btnAccept);
		this.getContentPane().add(this.btnRefuse);
	}

	void setText(){
		this.txtPanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g){
				g.drawString(strangerID + " wants to add you", 80, 20);
			}
		};
		this.txtPanel.setBounds(new Rectangle(55,40,250,250));
		this.getContentPane().add(this.txtPanel);
	}

}
