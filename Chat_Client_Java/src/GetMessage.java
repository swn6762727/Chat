import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GetMessage extends Thread{
	String advice;
	BufferedReader reader;
	String UserPath=null;
	
	
	public void run(){
		try {
			reader = new BufferedReader(new InputStreamReader(Client.socket.getInputStream()));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		while(true)
        {
				try {
					advice = receiveMsg();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(advice);
			if(advice!=null)
			try{
			switch(advice){
			case "Register successful!":GetRegistsuccess();break;
			case "Login successful!":GetLoginsuccess();break;
			case "Error":GetError();break;
			case "Message":System.out.println("GetMessage");GetMessage();break;
			case "Accept":GetAccept();break;
			case "Refuse":GetRefuse();System.out.println("Refuse");break;
			case "Not Found":GetNotFound();break;
			case "Application":GetApplication();break;
			case "Other Account Login":GetOtherLogin();break;
			case "Transmission":GetTransmission();break;
			default:System.out.println(advice);
			}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	void GetRegistsuccess() throws IOException{
		advice=reader.readLine();
		JOptionPane.showMessageDialog(null, "Your ID is : "+advice,"Regist successful",JOptionPane.INFORMATION_MESSAGE,popupmessage("user/host/RegistSuccess.gif"));
		
	}
	//登陆成功，更换窗口，设置当前账户，更新好友列表，创建用户聊天记录
	void GetLoginsuccess() throws IOException {
		Client.client.ClientID=Client.hello.userID;
		Client.hello.dispose();
		try {
			Client.client.Username=reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		UserPath="user/"+Client.client.ClientID;
		File userfile =new File(UserPath);//检查账户文件是否存在
		if(!userfile.isDirectory());
		{
			userfile.mkdirs();
		}
		
		File friendlist =new File(UserPath+"/friendlist.txt");//检查聊天记录文件和好友列表
		if(friendlist.exists())
		{
			friendlist.delete();
		}
		friendlist.createNewFile();
		String FriendListPath=UserPath+"/friendlist.txt";
		
		
		SaveImage(UserPath,ClientUI.ClientID);
		
		String friendlistnum=reader.readLine();
		int n = Integer.parseInt(friendlistnum);
		new FileWriteThread(FriendListPath, friendlistnum+"\n").start();
		for (int i = 0; i < n; i++) {
			String ID = reader.readLine();
			String name =reader.readLine();
			SaveImage(UserPath,ID);
			new FileWriteThread(FriendListPath, ID+"\n"+name+"\n").start();
			File chathistory = new File(UserPath+"/"+ID+".txt");
			if(!chathistory.exists()){
				chathistory.createNewFile();
			}
		}
		Client.client=new ClientUI();
		Client.client.setVisible(true);
		
	}
	//收到错误信息
	void GetError() throws IOException{ 
		advice=reader.readLine();
		JOptionPane.showMessageDialog(null,advice,"Error",JOptionPane.INFORMATION_MESSAGE,popupmessage("user/host/Error.gif"));
	}
	//消息记录写入
	 void GetMessage() throws IOException{ 
		String ID=reader.readLine();
		String message=reader.readLine();
		String messageover=reader.readLine(); //多行消息等待扩展
		String ChatHistoryName = UserPath+"/"+ID+".txt";//构建聊天记录存储路径
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String date = now.format(formatter).toString()+"\n";
		
		String content=ID+"\n"+date+message+"\n";//将消息加入聊天记录
		new FileWriteThread(ChatHistoryName,content).start();
		
		int num=ClientUI.friendlist.GetNumFromID(ID);
		ClientUI.friendlist.friend.elementAt(num).chat.addContent(ID,message,ClientUI.friendlist.friend.elementAt(num).Friendname,date);//将新纪录加入聊天面板
		ClientUI.friendlist.ChatlistPanel.setPreferredSize(new Dimension(580,ClientUI.friendlist.friend.elementAt(num).chat.getHeight()));//重新设置聊天面板高度
		ClientUI.friendlist.friend.elementAt(num).chat.repaint();//重新绘制图案
	}
	//接收到加为好友同意信息
	void GetAccept() throws IOException{
		System.out.println("GetAccept");
		
		String ID=reader.readLine();
		String name=reader.readLine();
		SaveImage(UserPath,ID);
		
		JOptionPane.showMessageDialog(null, name+"("+ID+")"+"has accepted your invitation","Accept",JOptionPane.INFORMATION_MESSAGE,popupmessage("user/host/Accept.gif"));
		
		FriendPanel friendpanel=new FriendPanel(UserPath,ID,name,ClientUI.friendlist.friendlistnum,ClientUI.ClientID);
		ClientUI.AddNewCard(friendpanel);
		
		
	}
	//好友请求被拒绝
	void GetRefuse() throws IOException{
			advice=reader.readLine();
		JOptionPane.showMessageDialog(null, advice+"has refused your invitation","Refused",JOptionPane.INFORMATION_MESSAGE,popupmessage("user/host/Refuse.gif"));
	}
	//获知没有找到此人
	void GetNotFound() throws IOException{
		advice=reader.readLine();
		JOptionPane.showMessageDialog(null, advice+"Can not find this ID","Not found",JOptionPane.INFORMATION_MESSAGE,popupmessage("user/host/NotFound.gif"));
		
	}
	//接收到好友请求
	void GetApplication() throws IOException{
		System.out.println("GetApplication");
		String ID=null;
		String name=null;
		ID=reader.readLine();
		name=reader.readLine();
		SaveImage(UserPath,ID);
		AddRequestUI add=new AddRequestUI(ID,name,UserPath);
	}
	//接收到异地登陆消息
	void GetOtherLogin(){
		JOptionPane.showMessageDialog(null, advice+"Someone has loged in in other place","Warning",JOptionPane.INFORMATION_MESSAGE,popupmessage("user/host/OtherLogin.gif"));
		Client.client.dispose();
		this.stop();
	}
	//接收到文件传输信息
	void GetTransmission() throws IOException{
		
	}
	//图片存储
	public void SaveImage(String path,String id){
		int lenth=0;
		String tempreader=null;
		String img=null;
		try {
			tempreader = reader.readLine();
			lenth=Integer.parseInt(tempreader);
			char imgchar[] = new char[lenth];
			reader.read(imgchar,0,lenth);
			reader.readLine();
			img = new String(imgchar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Convert.StringtoFile(img, path+"/"+id+".jpg");
	}
	//命令读取
	public String receiveMsg() throws IOException {
		String msg = new String();
		try {
			msg = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
	//弹窗图片
	ImageIcon popupmessage(String Path){
		ImageIcon img=new ImageIcon(Path);
		img.setImage(img.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
		return img;
	 }
}
