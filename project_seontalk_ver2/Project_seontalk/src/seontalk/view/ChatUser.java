package seontalk.view;

import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import seontalk.util.Theme;

public class ChatUser extends JPanel{
	JTextArea jta_user = new JTextArea();
	JLabel jlb_emo = null;
    
	Theme theme = new Theme();
    
	ProfileImg img = null;
    
    public ChatUser(MainPage page,String text) {
    	setLayout(new FlowLayout(FlowLayout.RIGHT));
    	jta_user.setOpaque(true);
    	jta_user.setBackground(theme.setInnerColor(page.memVO.getTheme()));
    	jta_user.setEditable(false);
    	jta_user.setLineWrap(true);
    	jta_user.setText(text);
    	Insets m = new Insets(5, 8, 5, 8);
    	jta_user.setMargin(m);
    	add(jta_user);
    	setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    }
    public ChatUser(MainPage page,ChatDownload file) {
    	setLayout(new FlowLayout(FlowLayout.RIGHT));
    	add(file);
    	setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    }
    public ChatUser(MainPage page,ImageIcon emoticon) {
    	setLayout(new FlowLayout(FlowLayout.RIGHT));
    	jlb_emo = new JLabel(emoticon);
    	add(jlb_emo);
    	setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    }
    public ChatUser(MainPage page,JLabel jlb) {
    	setLayout(new FlowLayout(FlowLayout.RIGHT));
    	add(jlb);
    	setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    }
}
