
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HelloUI extends JFrame{
	
	JTextField userNameInput = new JTextField();
	JPasswordField pwInput = new JPasswordField();
	JButton btnLog = new JButton();
	JButton btnRegist = new JButton();
	JPanel txtPanel;
	boolean everbtned=false;
	String userID=null;
	HelloUI(){
		super("Log In/Register");
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
			this.getContentPane().setBackground(new Color(80, 80, 80));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int height = this.getHeight(); 
		int width = this.getWidth(); 
		setLocation(Client.screenWidth-width/2, Client.screenHeight-height/2);
		this.setVisible(true);
	}
	 
	void jbInit(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,200);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		this.setTextField();
		this.setButton();
		this.setText();
	}
	
	
	void setTextField(){
		this.userNameInput.setBounds(new Rectangle(75,40,250,30));
		this.pwInput.setBounds(new Rectangle(75,80,250,30));
		this.getContentPane().add(this.userNameInput);
		this.getContentPane().add(this.pwInput);
	}
	
	void setButton(){
		this.btnLog.setBounds(new Rectangle(80,140,80,30));
		this.btnRegist.setBounds(new Rectangle(240,140,80,30));
		this.btnLog.setText("Log In");
		this.btnRegist.setText("Register");
		this.btnLog.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				userLogIn(e);
			}
		});
		this.btnRegist.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				userRegist(e);
			}
		});
		this.getContentPane().add(this.btnLog);
		this.getContentPane().add(this.btnRegist);
	}

	void setText(){
		this.txtPanel = new JPanel(){
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g){
				g.drawString("userID:", 28, 20);
				g.drawString("password:", 10, 60);
			}
		};
		this.txtPanel.setBounds(new Rectangle(0,40,75,110));
		this.getContentPane().add(this.txtPanel);
	}
	
	void userLogIn(ActionEvent e){
		everbtned=true;
		String password = this.pwInput.getText();
		userID = this.userNameInput.getText();
		SendMessage sendMSG =new SendMessage("Login",userID,password);
	}
	void userRegist(ActionEvent e){
		everbtned=true;
		RegisterUI regist = new RegisterUI();
	}

}
