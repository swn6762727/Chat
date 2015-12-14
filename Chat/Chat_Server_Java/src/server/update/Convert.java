package server.update;

import java.io.*;

public class Convert{
	static final String code = "ISO-8859-1";
	
	static String FiletoString(String filename) throws FileNotFoundException{
		File readfile = new File(filename);
		if (!readfile.exists()) throw new FileNotFoundException(filename);
		return FiletoString(readfile);
	}
	
	static String FiletoString(File file){
		InputStream fin;
		int size = 0;
		byte [] content = new byte[1];
		String result = null;
		//读文件
		try{
			fin = new FileInputStream(file);
			//获取文件长度
			size = fin.available();
			content = new byte[size];
			//按字节读文件
			fin.read(content, 0, size);
			fin.close();
			result = new String(content, code);
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	
	static void StringtoFile(String content, String filename){
		File writefile = new File(filename);
		if (!writefile.exists())
			try{
				writefile.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		StringtoFile(content, writefile);
	}
	
	static void StringtoFile(String content, File file){
		OutputStream fout;
		byte [] cont = new byte[1];
		try{
			cont = content.getBytes(code);
			fout = new FileOutputStream(file);
			//按字节写文件
			fout.write(cont, 0, cont.length);
			fout.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
