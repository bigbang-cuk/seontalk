package seontalk.view;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import seontalk.util.FilePath;

public class ChatImgPage extends JFrame {
	String imgName = null;
	JLabel jlb_img = null;
	PopupMenu pm = null;
	MenuItem item = null;
	public ChatImgPage(String imgName) {
		this.imgName = imgName;
		init();
	}
	public void init() {
		ImageIcon img = new ImageIcon(FilePath.ImgPath+imgName);
		int imgWidth = img.getIconWidth();
		int imgHeight = img.getIconHeight();
		pm = new PopupMenu();
		item = new MenuItem("저장하기");
		pm.add(item);
		add(pm);
		jlb_img = new JLabel(img);
		add(jlb_img);
		initEvent();
		setTitle(imgName+" - 원본");
		setSize(imgWidth+20,imgHeight+40);
		setVisible(true);
	}
	public void initEvent() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3) {
					pm.show(ChatImgPage.this, e.getX(), e.getY());
				}
				super.mouseClicked(e);
			}
		});
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, imgName+"\n다운받으시겠습니까?", "이미지 다운로드"
													, JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					FileInputStream is = null;
					FileOutputStream os = null;
					String root = null;
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("경로 선택");
					jfc.setMultiSelectionEnabled(false);
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result2 = jfc.showSaveDialog(null);
					if(result2==0) {
						root = jfc.getSelectedFile().getPath();
						try {
							File file = new File(FilePath.ImgPath+imgName);
							is = new FileInputStream(file);
							os = new FileOutputStream(root+"\\"+imgName);
							int readBuffer = 0;
							int size = (int)file.length();
							byte[] buffer = new byte[size];
							while((readBuffer = is.read(buffer)) != -1) {
								os.write(buffer,0,readBuffer);
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						} finally {
							try {
								is.close();
								os.close();
							} catch (Exception e3) {
								e3.printStackTrace();
							}
						}
						int result3 = JOptionPane.showConfirmDialog(null
								, "다운로드가 완료되었습니다\n폴더를 여시겠습니까?", "다운로드 성공"
								, JOptionPane.YES_NO_OPTION);
						if(result3==JOptionPane.YES_OPTION) {
							try {
								Desktop desktop = Desktop.getDesktop();
								File folder = new File(root);
								desktop.open(folder);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
					}
				}
			}
		});
	}
}
