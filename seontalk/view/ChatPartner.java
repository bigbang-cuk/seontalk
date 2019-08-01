package seontalk.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class ChatPartner extends JPanel{
	JTextArea jta_user = new JTextArea();
	JLabel 	  jlb_emo  = null;
    
	Theme theme = new Theme();
    
	MemberVO partner = null;
    ProfileImg img = null;
    
    public ChatPartner(MainPage page,String text,MemberVO partner) {
    	setLayout(new FlowLayout(FlowLayout.LEFT));
    	jta_user.setOpaque(true);
    	jta_user.setBackground(theme.setInnerColor(page.memVO.getTheme()));
    	jta_user.setEditable(false);
    	jta_user.setLineWrap(true);
    	jta_user.setText(text);
    	Insets m = new Insets(5, 8, 5, 8);
    	jta_user.setMargin(m);
    	Dimension profileSize = new Dimension(50,50);
    	img = new ProfileImg(partner.getProfile_img(), 0, 0, 50, 50);
    	img.setPreferredSize(profileSize);
    	add(img);
    	add(jta_user);
    	setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    }
    public ChatPartner(MainPage page,ChatDownload file,MemberVO partner) {
    	setLayout(new FlowLayout(FlowLayout.LEFT));
    	Dimension profileSize = new Dimension(50,50);
    	img = new ProfileImg(partner.getProfile_img(), 0, 0, 50, 50);
    	img.setPreferredSize(profileSize);
    	add(img);
    	add(file);
    	setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    }
    public ChatPartner(MainPage page,JLabel jlb,MemberVO partner) {
    	setLayout(new FlowLayout(FlowLayout.LEFT));
    	Dimension profileSize = new Dimension(50,50);
    	img = new ProfileImg(partner.getProfile_img(), 0, 0, 50, 50);
    	img.setPreferredSize(profileSize);
    	add(img);
    	add(jlb);
    	setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    }
    public ChatPartner(MainPage page,ImageIcon emoticon,MemberVO partner) {
    	setLayout(new FlowLayout(FlowLayout.LEFT));
    	Dimension profileSize = new Dimension(50,50);
    	img = new ProfileImg(partner.getProfile_img(), 0, 0, 50, 50);
    	img.setPreferredSize(profileSize);
    	jlb_emo = new JLabel(emoticon);
    	add(img);
    	add(jlb_emo);
    	setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    }
}
