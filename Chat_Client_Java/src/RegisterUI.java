import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

import javax.swing.*;

public class RegisterUI extends JFrame {

	JTextField userNameInput = new JTextField();
	JPasswordField pwInput = new JPasswordField();
	JButton btnLog = new JButton();
	JButton btnRegist = new JButton();
	JPanel txtPanel;
	ImagePanel image;
	boolean everbtned = false;
	String userID = null;
	String userImagePath;

	RegisterUI() {
		super("Register");
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
			this.getContentPane().setBackground(new Color(80, 80, 80));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int height = this.getHeight();
		int width = this.getWidth();
		setLocation(Client.screenWidth - width / 2, Client.screenHeight
				- height / 2);
		this.setVisible(true);
	}

	void jbInit() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(500, 200);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		this.setTextField();
		this.setButton();
		this.setText();
		this.setImage();
	}

	void setTextField() {
		this.userNameInput.setBounds(new Rectangle(175, 40, 250, 30));
		this.pwInput.setBounds(new Rectangle(175, 80, 250, 30));
		this.getContentPane().add(this.userNameInput);
		this.getContentPane().add(this.pwInput);
	}

	void setButton() {
		this.btnRegist.setBounds(new Rectangle(260, 140, 80, 30));
		this.btnRegist.setText("Register");
		this.btnRegist.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userRegist(e);
			}
		});
		this.getContentPane().add(this.btnRegist);
	}

	void setText() {
		this.txtPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				g.drawString("username:", 10, 20);
				g.drawString("password:", 10, 60);
			}
		};
		this.txtPanel.setBounds(new Rectangle(100, 40, 75, 110));
		this.getContentPane().add(this.txtPanel);
	}

	void setImage() {
		this.image = new ImagePanel(null);
		this.image.setBounds(10, 40, 80, 80);
		this.image.setBackground(new Color(255, 255, 255));
		// this.image.setImage();
		this.image.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FileDialog fd = new FileDialog(RegisterUI.this, "image",
						FileDialog.LOAD);
				fd.setVisible(true);
				String path = fd.getDirectory();
				String name = fd.getFile();
				RegisterUI.this.userImagePath = path + name;
				RegisterUI.this.image.setImage(RegisterUI.this.userImagePath);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {				
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		this.getContentPane().add(this.image);

	}

	void userRegist(ActionEvent e) {
		everbtned = true;
		String password = this.pwInput.getText();
		String username = this.userNameInput.getText();
		String img=null;
		String lenth="";
		try {
			img = Convert.FiletoString(userImagePath);
			lenth+=img.length();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SendMessage sendMSG = new SendMessage("Register",username,password,lenth,img);
		System.out.println("Register"+username+password+lenth);
		this.dispose();
	}

}

