package seontalk.view;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import seontalk.util.FilePath;

public class ChatImg {
	String imgName = null;
	Image img = null;
	public JLabel jlb_img = null;
	public ChatImg(String imgName) {
		this.imgName = imgName;
		Resizing();
		init();
	}
	public void Resizing() {
		img = new ImageIcon(FilePath.ImgPath+imgName).getImage();
		int imgWidth = img.getWidth(null);
		int imgHeight = img.getHeight(null);
		if(imgWidth>300||imgHeight>200) {
			Image scaledImg = img.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
			img = new ImageIcon(scaledImg).getImage();
		}
	}
	public void init() {
		jlb_img = new JLabel(new ImageIcon(img));
		jlb_img.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					new ChatImgPage(imgName);
				}
				super.mouseClicked(e);
			}
		});
	}
}
