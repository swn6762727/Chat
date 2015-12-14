import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DialogboxPanel extends JPanel{    			//对话显示图形
JLabel jlabel ;
String Content=null;
int width=400;
int height=1;
int lenth=0;
int labelnum=0;
JPanel dialogpanel = new JPanel();
JLabel hostLabel;
DialogboxPanel(String name,String date,String content){
	hostLabel = new JLabel(name+"  "+date);
	//hostLabel.setFont("");
	hostLabel.setBounds(10, 5, 250, 15);
	this.setLayout(null);
	this.add(hostLabel);
	Content=content;
	lenth=Content.length();
	height=lenth/50+1;
	DialogboxInt();
	dialogpanel.setLayout(null);
	this.setSize(420,(labelnum+1)*15+15);
	this.setBounds(0,0,420,(labelnum+1)*15+15);
}
private void DialogboxInt() {
	
	int begin=0;
	int end=0;
	int charcount=0;
	for(int i=0;i<lenth;i++){
		end=i;
		String str = Content.substring(begin, end+1);
		jlabel=new JLabel(str);
		int textW = jlabel.getFontMetrics(jlabel.getFont()).stringWidth(jlabel.getText());
		if(textW>=385||i==lenth-1)
		{
			labelnum++;
			if(i==lenth-1&&labelnum==1)
				width=textW+5;
			jlabel.setBounds(0, (labelnum-1)*15, width, 15);
			dialogpanel.add(jlabel);
			begin=i+1;
		}

	}
	dialogpanel.setBackground(Color.YELLOW);
	dialogpanel.setBounds(10,25,width,labelnum*15);
	
	this.add(dialogpanel);
}
}