package seontalk.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import seontalk.util.FilePath;
import seontalk.util.Theme;

public class Setting extends JPanel{
	MainPage 		page 			= null;	//전체화면 객체주입되는 변수
	JLabel 			jlb 			= new JLabel(); //상단 제목 라벨
	Theme theme = new Theme();
	String name = null;
	public Setting(MainPage page,String name) {
		this.page = page;
		this.name = name;
		init();
	}
	public void init() {
		setLayout(null);
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		initLabel();
		add(jlb);
	}
	public void initLabel() {
		if(Theme.BLACK.equals(page.memVO.getTheme())) {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"setting01_rev.png"));
		}
		else {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"setting01.png"));
		}
		jlb.setBounds(15, 15, 200, 50);
		jlb.setText(name);
		jlb.setFont(new Font(page.memVO.getFont(),Font.PLAIN,26));
		jlb.setForeground(theme.setFontColor(page.memVO.getTheme()));
	}
}
