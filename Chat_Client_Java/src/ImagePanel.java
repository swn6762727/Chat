

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//图片封装
public class ImagePanel extends JPanel {
	String ImagePath;
	JLabel ImageLabel;

	ImagePanel() {
		this.setLayout(null);
	}

	ImagePanel(String Path) {
		this.setLayout(null);
		ImageIcon image = new ImageIcon(Path);
		image.setImage(image.getImage().getScaledInstance(80, 80,
				Image.SCALE_DEFAULT));
		ImageLabel = new JLabel(image);
		this.add(ImageLabel);
		ImageLabel.setBounds(0, 0, 80, 80);
	}
	void setImage(String Path){
		super.removeAll();
		ImageIcon image = new ImageIcon(Path);
		image.setImage(image.getImage().getScaledInstance(80, 80,
				Image.SCALE_DEFAULT));
		ImageLabel = new JLabel(image);
		this.add(ImageLabel);
		ImageLabel.setBounds(0, 0, 80, 80);
	}

}