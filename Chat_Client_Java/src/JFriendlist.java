

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class JFriendlist{
int recentfriend = 0;
String UserPath;
String UserID;
int friendlistnum=0;
String reader=null;
JPanel FriendlistPanel=new JPanel();
JPanel ChatlistPanel =new JPanel();
Vector<FriendPanel> friend = new Vector<>();
int k=0;
JFriendlist(String ID) throws IOException
{
	FriendlistPanel.setLayout(null);
	FriendlistPanel.setBackground(new Color(202, 249, 254));
	FriendlistPanel.setPreferredSize(new Dimension(280,800));
	FriendlistPanel.setBounds(0, 0, 280, 800);
	
	ChatlistPanel.setLayout(null);
	ChatlistPanel.setBackground(new Color(255, 255, 255));
	ChatlistPanel.setBounds(0, 0, 580, 630);
	
	UserID=ID;  //使用者ID;
	UserPath="user/"+UserID;
	
	File friendlist=new File(UserPath+"/friendlist.txt");
	try {
		//读取好友列表信息
		FileInputStream fileinput=new FileInputStream(friendlist);
		InputStreamReader filereader= new InputStreamReader(fileinput);
		BufferedReader buffreader = new BufferedReader(filereader);
		reader=buffreader.readLine();
		friendlistnum = Integer.parseInt(reader);
		for(k=0;k<friendlistnum;k++)
		{
			String friendID=reader=buffreader.readLine();
			String friendname=reader=buffreader.readLine();
			
			friend.add( new FriendPanel(UserPath,friendID,friendname,k,UserID) );
			FriendlistPanel.add(friend.elementAt(k).card);
			friend.elementAt(k).card.setBounds(5,k*100+5,250,90);
		}
		fileinput.close();
		//对第一个置顶的friend card进行初始化
		ClientUI.ChatingFriendID=friend.elementAt(0).FriendID;
		ClientUI.ChatingFriendName=friend.elementAt(0).Friendname;

		friend.elementAt(0).card.colornormal=new Color(27,86,235);
		friend.elementAt(0).card.changebackground("choose");
		//对第一个chatpanel进行初始化
		ChatlistPanel.add(friend.elementAt(0).chat);
		if(friend.elementAt(0).chat.getHeight()>615)
		{
			ChatlistPanel.setPreferredSize(new Dimension(280,friend.elementAt(0).chat.getHeight()));
			ChatlistPanel.setBounds(0, 0, 280, ChatlistPanel.getHeight());
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//检测好友列表长度是否大于最高宽度
	if(FriendlistPanel.getHeight()>790)
	{
		FriendlistPanel.setPreferredSize(new Dimension(260,FriendlistPanel.getHeight()+20));
		FriendlistPanel.setBounds(0, 0, 260, FriendlistPanel.getHeight());
	}
	
}

//给出friend的位置，将该friend置顶
public void setlayout(int number){

	for(int i=0;i<friendlistnum;i++)
	{
		
		if(number==0)
		{
			break;
		}
		if(friend.elementAt(i).card.num==0)
		{
			friend.elementAt(i).card.colornormal=new Color(108,219,234);
			friend.elementAt(i).card.changebackground("Released");
		}
		if(friend.elementAt(i).card.num<number)
		{
			friend.elementAt(i).card.num+=1;
			continue;
		}
		if(friend.elementAt(i).card.num==number)//更换聊天记录窗口
		{
			friend.elementAt(i).card.num=0;
			friend.elementAt(i).card.colornormal=new Color(27,86,235);
			friend.elementAt(i).card.changebackground("choose");
			ChatlistPanel.removeAll();
			ChatlistPanel.add(friend.elementAt(i).chat);
			if(friend.elementAt(i).chat.getHeight()>615)
				{
				ChatlistPanel.setPreferredSize(new Dimension(580,friend.elementAt(i).chat.getHeight()));
				ChatlistPanel.setSize(580,friend.elementAt(i).chat.getHeight());
				}
			else
				{
				ChatlistPanel.setPreferredSize(new Dimension(580,630));
				}
			
			ChatlistPanel.setBounds(0, 0, 580,ChatlistPanel.getHeight());
			ClientUI.ChatingFriendID=friend.elementAt(i).FriendID;
			ClientUI.ChatingFriendName=friend.elementAt(i).Friendname;
			ClientUI.ChatingfriendPanel.repaint();
			
		}
	}
	for(int i=0;i<friendlistnum;i++)
	{
		friend.elementAt(i).card.setBounds(5,friend.elementAt(i).card.num*100+5,250,90);
	}
	
	ClientUI.sBar.setValue(ClientUI.sBar.getMaximum());
}

public int GetNumFromID(String ID){
	int changenum=0;
	for(int i=0;i<friendlistnum;i++)
	{
		if(friend.elementAt(i).FriendID.equals(ID))
			{
			return changenum=i;
			}
	}
	return 0;
}

}

class FriendPanel {
	FriendCardPanel card;
	FriendChathistoryPanel chat;
	String FriendID;
	String Friendname;
	public FriendPanel(String Path, String ID, String name,int k,String UserID) {
	FriendID=ID;
	Friendname=name;
	card = new FriendCardPanel(Path,ID,name,k);
	chat = new FriendChathistoryPanel(Path,ID,name,UserID);

	}
}