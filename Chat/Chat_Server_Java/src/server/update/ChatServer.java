package server.update;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class ChatServer implements Runnable {
	protected ServerSocket listen_socket;
	Thread thread;
	Vector<Connection> clients = new Vector<>();

	public ChatServer() {
		startServerListen();
	}

	public void startServerListen() {
		try {
			listen_socket = new ServerSocket(Const.DEFAULT_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		processMsg("Server: listening on port " + Const.DEFAULT_PORT);
		thread = new Thread(this);
		thread.start();
	}

	 public void processMsg(String str) {
		System.out.println(str);
	}

	public static void main(String[] args) {
		ChatServer server = new ChatServer();
		System.out.println(server.toString());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true){
		        Socket client_socket = listen_socket.accept();
		        Connection c = new Connection(client_socket, this);
		        clients.add(c);
		        processMsg("One Client Comes in. IP: " + 
		        		client_socket.getInetAddress().toString());
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(String acc) throws Exception{
		if (Accountexist(acc)){
			for (Connection client : clients){
				if (client.account != null && client.account.equals(acc))
					return client;
			}
		}else{
			throw new Exception(Const.Accountnotexist);
		}
		return null;
	}
	
	private boolean Accountexist(String account){
		return (new File(account)).exists();
	}
}

