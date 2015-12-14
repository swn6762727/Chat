

import java.io.*;
//文件传输
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
		//���ļ�
		try{
			fin = new FileInputStream(file);
			//��ȡ�ļ�����
			size = fin.available();
			content = new byte[size];
			//���ֽڶ��ļ�
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
			//���ֽ�д�ļ�
			fout.write(cont, 0, cont.length);
			fout.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
