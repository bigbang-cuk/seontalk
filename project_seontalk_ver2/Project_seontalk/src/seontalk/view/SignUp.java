package seontalk.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import seontalk.vo.MemberVO;

public class SignUp extends JFrame {
	SignUp_mentor 	jp_mentor 	= null;
	SignUp_interest jp_interest = null;
	SignUp_info 	jp_signup 	= null;
	SignUp_confirm 	jp_confirm  = null;
	
	MemberVO memVO = new MemberVO();
	
	public SignUp() {
		jp_mentor 	= new SignUp_mentor(this); 
		init();
	}
	
	public void init() {
		setTitle("선톡 - 회원가입");
		setSize(500,700);
		initLocation();
		add(jp_mentor);
		setResizable(false);
		setVisible(true);
	}

	public void initLocation() {
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((monitor.width-getSize().width)/2, (monitor.height-getSize().height)/2);
	}
	
}
