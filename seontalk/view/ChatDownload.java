package seontalk.view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import seontalk.util.FilePath;

public class ChatDownload extends JButton {
	public ChatDownload(String fileName) {
		setText(fileName);
		Font font = new Font("HY견고딕", Font.PLAIN, 12);
		setFont(font);
		setIcon(new ImageIcon(FilePath.SrcPath+"download02.png"));
		Dimension size = new Dimension(170,70);
		setPreferredSize(size);
		setHorizontalAlignment(SwingConstants.LEFT);
		setFocusable(false);
		setRolloverEnabled(false);
		
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, fileName+"\n다운받으시겠습니까?", "파일 다운로드"
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
							File file = new File(FilePath.FilePath+fileName);
							is = new FileInputStream(file);
							os = new FileOutputStream(root+"\\"+fileName);
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
		});
	}
}
