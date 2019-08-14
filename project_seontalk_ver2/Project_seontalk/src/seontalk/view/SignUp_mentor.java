package seontalk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.util.FilePath;

public class SignUp_mentor extends JPanel {
	JButton jbtn_mentor = null;
	JButton jbtn_mentee = null;
	JLabel	jlb_mentor	= null;
	JLabel	jlb_mentee	= null;
	SignUp  page		= null;
	public SignUp_mentor(SignUp page) {
		this.page = page;
		init();
	}
	public void init() {
		Dimension size = new Dimension(500, 700);
		setPreferredSize(size);
		setBackground(new Color(255,224,200));
		setLayout(null);
		initLabel();
		initButton();
		initEvent();
	}
	public void initLabel() {
		Font font  = new Font("HY견고딕",Font.BOLD,30);
		jlb_mentor = new JLabel("멘토");
		jlb_mentee = new JLabel("멘티");
		jlb_mentor.setFont(font);
		jlb_mentee.setFont(font);
		jlb_mentor.setOpaque(true);
		jlb_mentee.setOpaque(true);
		jlb_mentor.setBackground(new Color(255,224,200));
		jlb_mentee.setBackground(new Color(255,224,200));
		jlb_mentor.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_mentee.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_mentor.setVerticalAlignment(SwingConstants.CENTER);
		jlb_mentee.setVerticalAlignment(SwingConstants.CENTER);
		jlb_mentor.setBounds(20, 500, 220, 50);
		jlb_mentee.setBounds(260, 500, 220, 50);
		add(jlb_mentor);
		add(jlb_mentee);
	}
	public void initButton() {
		jbtn_mentor = new JButton(new ImageIcon(FilePath.SrcPath+"Teacher04.png"));
		jbtn_mentee = new JButton(new ImageIcon(FilePath.SrcPath+"students04.png"));
		jbtn_mentor.setRolloverIcon(new ImageIcon(FilePath.SrcPath+"Teacher05.png"));
		jbtn_mentee.setRolloverIcon(new ImageIcon(FilePath.SrcPath+"students05.png"));
		jbtn_mentor.setBackground(new Color(231,164,100));
		jbtn_mentee.setBackground(new Color(231,164,100));
		jbtn_mentor.setFocusable(false);
		jbtn_mentee.setFocusable(false);
		jbtn_mentor.setBounds(20, 100, 220, 400);
		jbtn_mentee.setBounds(260, 100, 220, 400);
		add(jbtn_mentor);
		add(jbtn_mentee);
	}
	public void initEvent() {
		jbtn_mentor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page.memVO.setMentoring("mentor");
				page.remove(page.jp_mentor);
				if(page.jp_interest==null) {
					page.jp_interest = new SignUp_interest(page); 
				}
				page.add(page.jp_interest);
				page.revalidate();
				page.repaint();
			}
		});
		jbtn_mentee.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page.memVO.setMentoring("mentee");
				page.remove(page.jp_mentor);
				if(page.jp_interest==null) {
					page.jp_interest = new SignUp_interest(page);
				}
				page.add(page.jp_interest);
				page.revalidate();
				page.repaint();
			}
		});
	}
}
