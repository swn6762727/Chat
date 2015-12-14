

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JPanel;

public class FriendChathistoryPanel extends JPanel{
String FriendPath;
String  FriendID;
String Friendname;
String read;
String UserID;
BufferedReader buffreader ;
int line=15;
	public FriendChathistoryPanel(String path, String ID, String name,String UserID) {
		this.setLayout(null);
		this.setBackground(new Color(255,255,255));
		FriendPath=path;
		FriendID=ID;
		Friendname=name;
		this.UserID=UserID;
		File file =new File(FriendPath+"/"+ID+".txt");
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		FileInputStream fileinput;
		try {
			fileinput = new FileInputStream(file);
			InputStreamReader filereader= new InputStreamReader(fileinput);
			buffreader = new BufferedReader(filereader);
			read=buffreader.readLine();
			while(read!=null)
			{
				String id=read;
				
				String time=buffreader.readLine();
				read=buffreader.readLine();
				String recentname="";
				AvatarPanel avatar;
				System.out.println(id);
				System.out.println(ClientUI.ClientID);
				System.out.println(id.equals(ClientUI.ClientID));
				if(id.equals(ClientUI.ClientID))
					{
					avatar = new AvatarPanel(path+"/"+id+".jpg");
					recentname = ClientUI.Username;
					}
				else
					{
					avatar = new AvatarPanel(path+"/"+id+".jpg");
					recentname = Friendname;
					}				
				DialogboxPanel dialogpanel = new DialogboxPanel(recentname,time,read);
				dialogpanel.setBounds(60, line, dialogpanel.getWidth(), dialogpanel.getHeight());
				this.add(dialogpanel);	
				avatar.setBounds(10, line, 40, 40);
				this.add(avatar);
				line=line+dialogpanel.getHeight()+15;
				read=buffreader.readLine();
			}
			fileinput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		this.setSize(580, line);
		this.setBounds(0, 0, 580, this.getHeight());
	}
	public void addContent(String id,String content,String name,String time)
	{
		AvatarPanel avatar;
			avatar = new AvatarPanel(FriendPath+"/"+id+".jpg");		
		DialogboxPanel dialogpanel = new DialogboxPanel(name,time,content);
		dialogpanel.setBounds(60, line, dialogpanel.getWidth(), dialogpanel.getHeight());
		this.add(dialogpanel);
		avatar.setBounds(10, line, 40, 40);
		this.add(avatar);
		line=line+dialogpanel.getHeight()+15;
		this.setSize(580, line);
		this.setBounds(0, 0, 580, this.getHeight());
	}
}
