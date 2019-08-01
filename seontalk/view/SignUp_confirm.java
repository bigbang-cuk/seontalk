package seontalk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.control.ConnectionCtrl;
import seontalk.vo.MemberVO;

public class SignUp_confirm extends JPanel {
	JButton 	jbtn_confirm 			= null;
	JButton 	jbtn_redo 				= null;
	ProfileImg 	prof_confirm 			= null;
	JLabel 		jlb_confirm_id 			= null;
	JLabel 		jlb_confirm_nick 		= null;
	JLabel 		jlb_confirm_mentor		= null;
	JLabel 		jlb_confirm_interest 	= null;
	SignUp		page					= null;
	
	public SignUp_confirm(SignUp page) {
		this.page = page;
		init();
	}
	public void init() {
		Dimension size = new Dimension(500, 700);
		setPreferredSize(size);
		setBackground(new Color(255,224,200));
		setLayout(null);
		initProfileImg();
		initLabel();
		initButton();
		initEvent();
	}
	public void initProfileImg() {
		prof_confirm = new ProfileImg("defaultImg.jpg", 0, 0, 200, 200);
		prof_confirm.setBounds(150, 50, 200, 200);
		add(prof_confirm);
	}
	public void initLabel() {
		Font font  = new Font("HY견고딕",Font.PLAIN,16);
		Color color = new Color(255,224,200);
		jlb_confirm_id 			= new JLabel(page.memVO.getId().toString());
		jlb_confirm_nick 		= new JLabel(page.memVO.getNick().toString());
		if("mentor".equals(page.memVO.getMentoring())) {
			jlb_confirm_mentor	= new JLabel("멘토");
		}
		else {
			jlb_confirm_mentor	= new JLabel("멘티");
		}
		if(page.memVO.getInterest2()!=null) {
			jlb_confirm_interest 	= new JLabel(page.memVO.getInterest1().toString()
											+"/"+page.memVO.getInterest2().toString());
		}
		else {
			jlb_confirm_interest 	= new JLabel(page.memVO.getInterest1().toString());
		}
		jlb_confirm_id.setFont(font);
		jlb_confirm_nick.setFont(font);
		jlb_confirm_mentor.setFont(font);
		jlb_confirm_interest.setFont(font);
		jlb_confirm_id.setOpaque(true);
		jlb_confirm_nick.setOpaque(true);
		jlb_confirm_mentor.setOpaque(true);
		jlb_confirm_interest.setOpaque(true);
		jlb_confirm_id.setBackground(color);
		jlb_confirm_nick.setBackground(color);
		jlb_confirm_mentor.setBackground(color);
		jlb_confirm_interest.setBackground(color);
		jlb_confirm_id.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_confirm_nick.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_confirm_mentor.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_confirm_interest.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_confirm_id.setVerticalAlignment(SwingConstants.CENTER);
		jlb_confirm_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_confirm_mentor.setVerticalAlignment(SwingConstants.CENTER);
		jlb_confirm_interest.setVerticalAlignment(SwingConstants.CENTER);
		jlb_confirm_id.setBounds(50, 250, 400, 50);
		jlb_confirm_nick.setBounds(50, 300, 400, 50);
		jlb_confirm_mentor.setBounds(50, 350, 400, 50);
		jlb_confirm_interest.setBounds(50, 400, 400, 50);
		add(jlb_confirm_id);
		add(jlb_confirm_nick);
		add(jlb_confirm_mentor);
		add(jlb_confirm_interest);
	}
	public void initButton() {
		Font font = new Font("HY견고딕",Font.PLAIN,18);
		jbtn_confirm = new JButton("맞아요 이대로 가입할게요");
		jbtn_redo 	 = new JButton("아니요 수정할게요");
		jbtn_confirm.setFont(font);
		jbtn_redo.setFont(font);
		jbtn_confirm.setBackground(new Color(231,164,100));
		jbtn_redo.setBackground(new Color(231,164,100));
		jbtn_confirm.setFocusable(false);
		jbtn_redo.setFocusable(false);
		jbtn_confirm.setBounds(80, 500, 340, 60);
		jbtn_redo.setBounds(80, 570, 340, 60);
		add(jbtn_confirm);
		add(jbtn_redo);
	}
	public void initEvent() {
		jbtn_confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConnectionCtrl ctrl = new ConnectionCtrl();
				MemberVO rVO = null;
				rVO = (MemberVO)ctrl.Connect(ConnectionCtrl.INSERT, "signup", page.memVO);
				if(rVO.getStatus()==1) {
					JOptionPane.showMessageDialog(SignUp_confirm.this, "가입이 완료되었습니다");
					page.setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(SignUp_confirm.this, "가입에 실패하였습니다");
					page.setVisible(false);
				}
			}
		});
		jbtn_redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page.remove(page.jp_confirm);
				page.add(page.jp_signup);
				page.revalidate();
				page.repaint();
			}
		});
	}
}
