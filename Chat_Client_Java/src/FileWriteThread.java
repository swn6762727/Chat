import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FileWriteThread extends Thread{
	public FileWriteThread(String filename,String content) throws IOException{
		File file=new File(filename);
		PrintWriter printwriter=new PrintWriter(new FileWriter(file,true));
		printwriter.print(content);
        printwriter.close();
	}

}
