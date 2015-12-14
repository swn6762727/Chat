import java.io.*;

public class SendMessage {
String content="";
static private PrintWriter p;
public SendMessage(String ...str){
	
	for(String string:str)
	{
		this.content+=string+"\n";
	}
	try{
	    p = new PrintWriter(Client.socket.getOutputStream());
	}catch(Exception e){System.out.println("FUCK!");}
	p.print(content);
	p.flush();
}
//发送消息
}