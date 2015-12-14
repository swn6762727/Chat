package server.update;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Connection extends Thread{
	public final static int ACCOUNT_LENGTH = 10;
	public String account;
	protected String name, password;
	protected File local_account;
	protected Socket client;
	protected BufferedReader in;
	protected PrintWriter out;
	protected BufferedReader fin;
	protected PrintWriter fout;
	protected int friendsnum;
	private boolean flag = false;
	ChatServer server;

	// Initialize the streams and start the thread
	public Connection(Socket client_socket, ChatServer server_){
		client = client_socket;
		server = server_;
		account = name = password = null;
		try{
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new PrintWriter(client.getOutputStream());
		}catch(IOException e){
			try{
				client.close();
			}catch(IOException e2){
				e2.printStackTrace();
			}
			e.printStackTrace();
			return;
		}
		this.start();
	}

	// Provide the service.
	public void run(){
		String line;
		while(true){
			// read in a line
			line = receiveMsg();
			handleMsg(line);
			if(line == null || flag)
				break;
		}
		try{
			client.close();
		}catch(IOException e2){
			e2.printStackTrace();
		}
	}

	private void handleMsg(String Header){
		server.processMsg("receive: " + Header);
		if(Header == null){
			sayGoodbye();
			return;
		}
		switch(Header){
			case "Register":
				server.processMsg("Regis");

				Register();
				break;
			case Const.Login:
				Login();
				break;
			case Const.Message:
				Message();
				break;
			case Const.Application:
				Application();
				break;
			case Const.Accept:
				Accept();
				break;
			case Const.Refuse:
				Refuse();
				break;
			case Const.Transmission:
				Transmission();
				break;
		}
	}

	private void sayGoodbye(){
		server.processMsg("One Client Leave. IP: "
				+ client.getInetAddress().toString());
	}

	private void Register(){
		try{
			name = receiveMsg();
			server.processMsg("1");
			password = receiveMsg();
			server.processMsg("2");
			local_account = generateAccount();
			server.processMsg("3");
			RegisterFile();
			server.processMsg("4");
			reveivePicture();
			server.processMsg("1");
			sendMsg(Const.Registersuccessful + '\n' + account + '\n');
			server.processMsg("1");
			noticeServer(account + " Register\n");
			server.processMsg("1");
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void Login(){
		account = receiveMsg();
		password = receiveMsg();
		local_account = new File(account);
		if(Account_exists()){
			checkPassword();
		}
	}

	private void Message(){
		String Msg = Const.Message + '\n' + account + '\n';
		String ask_account = receiveMsg();
		String temp;
		do{
			temp = receiveMsg();
			Msg += temp + '\n';
		}while(!temp.equals(Const.MessageOver));
		try{
			sendMessage(server.getConnection(ask_account), ask_account, Msg);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void Application(){
		String ask_account = receiveMsg();
		System.out.println(ask_account);
		try{
			sendApplication(server.getConnection(ask_account), ask_account);
		}catch(Exception e){
			e.printStackTrace();
			accountnotexist(ask_account);
		}
	}

	private void Accept(){
		String ask_account = receiveMsg();
		addFriends(ask_account);
		try{
			sendAccept(server.getConnection(ask_account), ask_account);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void Refuse(){
		String ask_account = receiveMsg();
		try{
			sendRefuse(server.getConnection(ask_account), ask_account);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void Transmission(){
		String ask_account = receiveMsg();
		try{
			sendFile(server.getConnection(ask_account), ask_account);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void reveivePicture(){
		int length = Integer.valueOf(receiveMsg());
		System.out.println(length);
		char[] con = new char[length];
		try{
			in.read(con, 0, length);
		}catch(IOException e){
			e.printStackTrace();
		}
		receiveMsg();
		String content = new String(con);
		Convert.StringtoFile(content, account + ".jpg");
	}
	
	private File generateAccount() throws IOException{
		File account_info;
		do{
			int temp = (int) (Math.random() * (1 << ACCOUNT_LENGTH));
			account = Integer.toString(temp);
			account_info = new File(account);
		}while(account_info.exists());

		account_info.createNewFile();
		return account_info;
	}

	private void RegisterFile() throws IOException{
		System.out.println("fuck0");
		fout = new PrintWriter(new FileWriter(local_account));
		//fout.println(name);
		//fout.println(password);
		//fout.println(1);
		//fout.println(account);
		fout.println(name);
		fout.close();
	}

	private boolean Account_exists(){
		if(local_account.exists())
			return true;
		String error = "Error\n" + Const.Accountnotexist + '\n';
		try{
			sendMsg(error);
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}

	private void checkPassword(){
		String pw;
		try{
			fin = new BufferedReader(new FileReader(account));
			name = readLine();
			pw = readLine();
			if(!Passwordwrong(pw)){
				Loginsucceed();
			}
			fin.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private boolean Passwordwrong(String pw){
		if(password.equals(pw))
			return false;
		String error = "Error\n"+ Const.PasswordWrong + '\n';
		try{
			sendMsg(error);
		}catch(IOException e){
			e.printStackTrace();
		}
		return true;
	}

	private void Loginsucceed(){
		try{
			System.out.println(account);
			checkAccount(server.getConnection(account));
		}catch(Exception e1){
			e1.printStackTrace();
		}
		sendInfo();
		Logincheck();
	}

	private void sendInfo(){
		String ID = null;
		String success = Const.Loginsuccessful + '\n';
		success += name + '\n';
		String content = "";
		try{
			content = Convert.FiletoString(account + ".jpg");
			success += content.length() + '\n';
			success += content + '\n';
		}catch(FileNotFoundException e1){
			e1.printStackTrace();
		}
		friendsnum = Integer.parseInt(readLine());
		success += Integer.toString(friendsnum) + '\n';
		for(int i = 0; i < friendsnum; i++){
			ID = readLine();
			success += ID + '\n';
			success += readLine() + '\n';
			try{
				content = Convert.FiletoString(ID + ".jpg");
				success += content.length() + '\n';
				success += content + '\n';
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}
		try{
			sendMsg(success);
		}catch(IOException e){
			e.printStackTrace();
		}	
	}

	private void checkAccount(Connection otherconnection){
		String error = Const.OtherAccountLogin + '\n';
		if (otherconnection != null && otherconnection != this){
			try{
				otherconnection.sendMsg(error);
			}catch(IOException e){
				e.printStackTrace();
			}
			otherconnection.flag = true;
		}
	}

	private void Logincheck(){
		BufferedReader reader;
		String msg;
		File accept = new File(Const.Accept + account);
		File refuse = new File(Const.Refuse + account);
		File application = new File(Const.Application +  account);
		File message = new File(Const.Message + account);
		if (accept.exists()){
			try{
				reader = new BufferedReader(new FileReader(accept));
				while(true){
					msg = reader.readLine();
					if (msg == null) break;
					msg += '\n' + reader.readLine() + '\n';
					msg += reader.readLine() + '\n';
					sendMsg(msg);
				}
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			accept.delete();
		}
		if (refuse.exists()){
			try{
				reader = new BufferedReader(new FileReader(refuse));
				while(true){
					msg = reader.readLine();
					if (msg == null) break;
					msg += '\n' + reader.readLine() + '\n';
					sendMsg(msg);
				}
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			refuse.delete();
		}
		if (application.exists()){
			try{
				reader = new BufferedReader(new FileReader(application));
				while(true){
					msg = reader.readLine();
					if (msg == null) break;
					msg += '\n' + reader.readLine() + '\n';
					msg += reader.readLine() + '\n';
					sendMsg(msg);
				}
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			application.delete();
		}
		if(message.exists()){
			try{
				reader = new BufferedReader(new FileReader(message));
				while(true){
					String line;
					msg = "";
					line = reader.readLine();
					if (line == null) break;
					do{
						msg += line + '\n';
						line = reader.readLine();
					}while(!line.equals(Const.MessageOver));
					msg += line + '\n';
					sendMsg(msg);
				}
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			message.delete();
		}
	}

	private void sendMessage(Connection receiver, String acc, String message){
		try{
			if(receiver != null){
				receiver.sendMsg(message);
				System.out.println("Get receiver!\n");
			}else{
				Handlefile.saveMessage(acc, message);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void sendApplication(Connection receiver, String acc){
		String message = "";
		String content = "";
		try{
			content = Convert.FiletoString(account + ".jpg");
			message = Const.Application+ '\n' 
					+ account + '\n' 
					+ name + '\n'
					+ content.length() + '\n'
					+ content + '\n';
			
			if(receiver != null){
				receiver.sendMsg(message);
			}else{
				Handlefile.saveApplication(acc, message);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void accountnotexist(String ask_account){
		String error = Const.NotFound + '\n' + ask_account + '\n';
		try{
			sendMsg(error);
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}

	private void addFriends(String ask_account){
		friendsnum++;
		try{
			Handlefile.addFriend(account, ask_account);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void sendAccept(Connection receiver, String acc){
		String message = "";
		String content = "";
		try{
			content = Convert.FiletoString(account + ".jpg");
			message = Const.Accept + '\n' 
					+ account + '\n' 
					+ name + '\n' 
					+ content.length() + '\n'
					+ content + '\n';			
			if(receiver != null){
				receiver.friendsnum++;
				receiver.sendMsg(message);
			}else{
				Handlefile.saveAccept(acc, message);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void sendRefuse(Connection receiver, String acc){
		String message = Const.Refuse + '\n' + account + '\n';
		try{
			if(receiver != null){
				receiver.friendsnum++;
				receiver.sendMsg(message);
			}else{
				Handlefile.saveRefuse(acc, message);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void sendFile(Connection receiver, String ask_account){
		String filename = receiveMsg();
		String content = "";
		String read = "";
		read = receiveMsg();
		while(!read.equals(Const.FileOver)){
			content += read + '\n';
			read = receiveMsg();
		}
		String Message = Const.Transmission + '\n'
						+ account + '\n'
						+ filename + '\n'
						+ content
						+ Const.FileOver + '\n';
		try{
			if(receiver != null){
				receiver.sendMsg(Message);
			}else{
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}	
		
	}

	synchronized public void sendMsg(String msg) throws IOException{
		out.print(msg);
		out.flush();
	}

	public String receiveMsg(){
		String msg = new String();
		try{
			msg = in.readLine();
		}catch(IOException e){
			e.printStackTrace();
			msg = null;
		}
		return msg;
	}

	public String readLine(){
		String msg = new String();
		try{
			msg = fin.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		return msg;
	}

	private void noticeServer(String Msg){
		server.processMsg(Msg + " success!\n");
	}
}
