import java.net.Socket;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Client {
	static Socket socket;
	static ClientUI client;
	static HelloUI hello;
	static int screenWidth,screenHeight;
	static InputStream socketIn;
	public static void main(String[] args) throws IOException {

		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包 
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸 
		screenWidth = screenSize.width/2; // 获取屏幕的宽
		screenHeight = screenSize.height/2; // 获取屏幕的高
		
		hello=new HelloUI();
		try{
			socket = new Socket("127.0.0.1",6543);
			InputStream socketIn = Client.socket.getInputStream();
        }catch(Exception e){}
		Thread GetFromServer = new GetMessage();
		GetFromServer.start();
        System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
	}
}
