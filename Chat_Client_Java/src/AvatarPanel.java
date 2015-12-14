
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
//用户对话头像
public class AvatarPanel extends JPanel{
String ImagePath;
JLabel ImageLabel;
AvatarPanel(String Path)
{
	this.setLayout(null);
	ImageIcon image=new ImageIcon(Path);
	image.setImage(image.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT));
	ImageLabel = new JLabel(image);
	this.add(ImageLabel);
	ImageLabel.setBounds(0, 0, 40, 40);
}

}
