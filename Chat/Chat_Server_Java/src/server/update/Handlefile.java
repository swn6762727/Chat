package server.update;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Handlefile{
	synchronized public static void saveMessage(String receiver, String message)
			throws IOException{
		File Mfile = new File("Message" + receiver);
		if (!Mfile.exists())
			Mfile.createNewFile();
		PrintWriter Mout = new PrintWriter(new FileWriter(Mfile, true));
		Mout.print(message);
		Mout.close();
	}

	synchronized public static void saveApplication(String receiver, String message) throws IOException{
		File Afile = new File("Application" + receiver);
		if (!Afile.exists())
			Afile.createNewFile();
		PrintWriter Aout = new PrintWriter(new FileWriter(Afile, true));
		Aout.print(message);
		Aout.close();
	}

	synchronized public static void addFriend(String account, String ask_account) throws IOException{
		add(account,ask_account);
		add(ask_account,account);
	}

	private static void add(String account, String ask_account) throws IOException{
		BufferedReader Fin = new BufferedReader(new FileReader(account));
		BufferedReader fin = new BufferedReader(new FileReader(ask_account));
		String content = "";
		try{
			String ask_name = fin.readLine();
			content += Fin.readLine() + '\n';
			content += Fin.readLine() + '\n';
			int friendsnum = Integer.parseInt(Fin.readLine());
			friendsnum++;
			content += Integer.toString(friendsnum) + '\n';
			for (int i = 0; i < friendsnum-1; i++){
				content += Fin.readLine() + '\n';
				content += Fin.readLine() + '\n';
			}
			content += ask_account + '\n';
			content += ask_name + '\n';
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			fin.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			Fin.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		PrintWriter Fout = new PrintWriter(new FileWriter(account));
		Fout.print(content);
		Fout.close();
	}

	synchronized public static void saveAccept(String receiver, String message) throws IOException{
		File Afile = new File("Accept" + receiver);
		if (!Afile.exists())
			Afile.createNewFile();
		PrintWriter Aout = new PrintWriter(new FileWriter(Afile, true));
		Aout.print(message);
		Aout.close();
	}

	synchronized public static void saveRefuse(String receiver, String message) throws IOException{
		File Rfile = new File("Refuse" + receiver);
		if (!Rfile.exists())
			Rfile.createNewFile();
		PrintWriter Rout = new PrintWriter(new FileWriter(Rfile, true));
		Rout.print(message);
		Rout.close();
	}
}
