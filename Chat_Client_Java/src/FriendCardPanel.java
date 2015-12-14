
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class FriendCardPanel extends JPanel{
int num=0;
String FriendID;
String FriendName;
JPanel Friendcard=new JPanel();
ImagePanel img;
Color colornormal=new Color(108,219,234);
Color colorenter=new Color(108,205,250);
Color colorpress=new Color(26,138,238);
FriendCardPanel(String Path,String ID,String name,int k){
	FriendID=ID;
	FriendName=name;
	num=k;
	this.setBackground(new Color(108,219,234));
	this.setLayout(null);
	this.addMouseListener(new MouseListener(){
		public void  mouseClicked(MouseEvent e) {
			ClientUI.friendlist.setlayout(num);
		  }

		@Override
		public void mousePressed(MouseEvent e) {
			changebackground("Pressed");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			changebackground("Released");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			changebackground("Enter");
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			changebackground("Exited");
		}
	});
	img=new ImagePanel(Path+"/"+ID+".jpg");	
	this.add(img);
	img.setBounds(10, 5, 80, 80);
	JLabel label = new JLabel(name);
	this.add(label);
	label.setBounds(110, 35, 150, 20);
}
public void changebackground(String Type) {
	switch(Type){
		case"Exited":this.setBackground(colornormal);break;
		case"Pressed":this.setBackground(colorpress);break;
		case"Enter":this.setBackground(colorenter);break;
		case"Released":this.setBackground(colornormal);break;
		case"choose":this.setBackground(new Color(27,86,235));break;
		default:break;
	}
}
}
