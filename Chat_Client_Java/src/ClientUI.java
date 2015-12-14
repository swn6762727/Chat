

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ClientUI extends JFrame{
	static String ClientID=null;
	static String Username=null;
	int Friendnum=0;
	static JFriendlist friendlist;
	static String ChatingFriendID=null;
	static String ChatingFriendName=null;
	static JLabel ChatingLabel;
	
	static JScrollPane ChatWindowPanel;
	static JScrollBar sBar;	
	static JScrollPane FriendlistPanel;
	
	JPanel UserPanel=new JPanel();
	JPanel InputmessagePanel = new JPanel();
	JPanel ToolsPanel= new JPanel();
	static JPanel ChatingfriendPanel=new JPanel();
	JButton SendMSG = new JButton("Send");
	JButton AddFriend = new JButton("Add Friend");
	
	static int screenWidth,screenHeight;
	
	ClientUI() throws IOException{
		
		friendlist = new JFriendlist(ClientID);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);;
		this.setSize(1000, 800);
		this.setResizable(false);
		this.setLayout(null);
		this.getContentPane().setBackground(new Color(80, 80, 80));
		this.ChatfriendPanelInt();
		this.UserPanelInt();
		this.ToolsPanelInt();
		this.InputmessagePanelInt();
		this.FriendInt();
		int height = this.getHeight(); 
		int width = this.getWidth(); 
		this.setLocation(Client.screenWidth-width/2, Client.screenHeight-height/2);
		this.setTitle(Username+"  "+ClientID);
		this.setVisible(true);
	}
	
    //当前对话好友窗口初始化
	private void ChatfriendPanelInt() {
		this.ChatingfriendPanel.setBackground(new Color(128, 128, 128));
		this.ChatingfriendPanel.setBounds(400, 0, 600, 40);
		ChatingLabel = new JLabel(ChatingFriendName);
		this.ChatingfriendPanel.add(ChatingLabel);
		this.getContentPane().add(ChatingfriendPanel);
		
	}
	
	//小工具栏
	private void ToolsPanelInt() {
		this.getContentPane().add(ToolsPanel);
		this.ToolsPanel.setBackground(new Color(150, 150, 150));
		this.ToolsPanel.setBounds(400, 670, 600, 30);
	}
	//文本输入设置
	private void InputmessagePanelInt() {
		this.getContentPane().add(InputmessagePanel);
		this.InputmessagePanel.setBackground(new Color(120, 120, 120));
		this.InputmessagePanel.setBounds(400, 700, 600, 100);
		InputmessagePanel.setLayout(null);
		
		//文本输入区
		JTextArea textarea = new JTextArea(2,30);
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true); 
		JScrollPane scrolltext=new JScrollPane(textarea);
		scrolltext.setBounds(20, 20, 480, 40);
		InputmessagePanel.add(scrolltext);
		
		//发送消息按钮
		InputmessagePanel.add(SendMSG);
		SendMSG.setBounds(520, 30, 60, 20);
		SendMSG.addActionListener((event)->{
		String content = textarea.getText();
		System.out.println(content);
		if(ChatingFriendID!=null)
		{
			try {
				recordmessage(content);					//聊天记录载入
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			SendMessage sendMSG =new SendMessage("Message",ChatingFriendID,content,"Message Over");
		}
		textarea.setText(null);
		});
		textarea.addKeyListener(new KeyAdapter()  {
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					e.consume();						//吸收掉添加的回车
					String content = textarea.getText();
					try {
						recordmessage(content);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(ChatingFriendID!=null)
					{
						SendMessage sendMSG =new SendMessage("Message",ChatingFriendID,content,"Message Over") ;
					}
					textarea.setText(null);
				}
				
		}
		});
	}
	//好友列表设置
	
	private void FriendInt() {
		ChatWindowPanel=new JScrollPane(friendlist.ChatlistPanel);//聊天窗口初始化
		this.ChatWindowPanel.setBackground(new Color(255, 255, 255));
		this.getContentPane().add(ChatWindowPanel);
		this.ChatWindowPanel.setBounds(400, 40, 600, 630 );
		
		sBar = ChatWindowPanel.getVerticalScrollBar();//滚轮置底
		ClientUI.sBar.setValue(ClientUI.sBar.getMaximum());
		
		FriendlistPanel=new JScrollPane(friendlist.FriendlistPanel);//好友列表初始化
		this.FriendlistPanel.setBackground(new Color(202, 249, 254));
		this.getContentPane().add(FriendlistPanel);
		FriendlistPanel.setBounds(120, 0, 280, 800);
		
		
	}
	
	//用户信息
	void UserPanelInt(){
		this.getContentPane().add(UserPanel);
		UserPanel.setBackground(new Color(80, 80, 80));
		UserPanel.setBounds(0, 0, 120, 800);
		UserPanel.setLayout(null);
		ImagePanel UserImag = new ImagePanel("user/"+ClientUI.ClientID+"/"+ClientUI.ClientID+".jpg");
		UserPanel.add(UserImag);
		UserImag.setBounds(20, 40, 80, 80);
		
		this.UserPanel.add(AddFriend);
		AddFriend.setBounds(10, 140, 100, 30);
		AddFriend.addActionListener((event)->{AddUI addui = new AddUI();});
	}
	
public static void AddNewCard(FriendPanel friendpanel){
	int k=ClientUI.friendlist.friendlistnum;//新好友card添加
	ClientUI.friendlist.friend.add( friendpanel );
	ClientUI.friendlist.friend.elementAt(k).card.setBounds(5,k*100+5,250,90);
	ClientUI.friendlist.FriendlistPanel.add(ClientUI.friendlist.friend.elementAt(k).card);
	ClientUI.friendlist.FriendlistPanel.repaint();
	
	ClientUI.friendlist.friendlistnum++;
	ClientUI.friendlist.friend.elementAt(k).card.setVisible(true);
}
	//好友列表重新加载
	static void ResetFriendlist() throws IOException{
		friendlist = new JFriendlist(ClientID);
		ChatWindowPanel=new JScrollPane(friendlist.ChatlistPanel);
		FriendlistPanel=new JScrollPane(friendlist.FriendlistPanel);
	}
	public void recordmessage(String content){
		int num=friendlist.GetNumFromID(ChatingFriendID);
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String date = now.format(formatter).toString()+"\n";
		
		friendlist.friend.elementAt(num).chat.addContent(ClientID, content,Username,date);
		friendlist.ChatlistPanel.setPreferredSize(new Dimension(580,friendlist.friend.elementAt(num).chat.getHeight()));
		friendlist.friend.elementAt(num).chat.repaint();

		String path="user/"+ClientID+"/"+ChatingFriendID+".txt";
		String hostcontent=ClientID+"\n"+date+content+"\n";//将消息加入聊天记录
		try {
			new FileWriteThread(path,hostcontent).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		friendlist.ChatlistPanel.repaint();
	}

}
