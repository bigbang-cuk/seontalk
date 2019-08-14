package seontalk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.util.FilePath;

public class SignUp_interest extends JPanel {
	JButton 	jbtn_select		= null;
	JLabel  	jlb_redo		= null;
	JLabel		jlb_interest1 	= null;
	JLabel		jlb_interest2 	= null;
	JComboBox 	jcb_interest1 	= null;
	JComboBox 	jcb_interest2 	= null;
	String[]	interestList1	= {"(필수항목)","자바,JSP","오라클 SQL","자바스크립트","HTML","안드로이드"};
	String[]	interestList2	= {"(선택항목)","자바,JSP","오라클 SQL","자바스크립트","HTML","안드로이드"};
	SignUp		page			= null;
	
	public SignUp_interest(SignUp page) {
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
		initInput();
		initEvent();
	}
	public void initLabel() {
		Font font  = new Font("HY견고딕",Font.PLAIN,16);
		Font redo   = new Font("HY견고딕",Font.PLAIN,16);
		jlb_interest1 = new JLabel("관심분야 1");
		jlb_interest2 = new JLabel("관심분야 2");
		jlb_redo   = new JLabel("뒤로가기"
				,new ImageIcon(FilePath.SrcPath+"preview01.png")
				,SwingConstants.LEFT);
		jlb_redo.setFont(redo);
		jlb_interest1.setFont(font);
		jlb_interest2.setFont(font);
		jlb_redo.setOpaque(true);
		jlb_interest1.setOpaque(true);
		jlb_interest2.setOpaque(true);
		jlb_redo.setBackground(new Color(255,224,200));
		jlb_interest1.setBackground(new Color(255,224,200));
		jlb_interest2.setBackground(new Color(255,224,200));
		jlb_interest1.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_interest2.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_redo.setVerticalAlignment(SwingConstants.CENTER);
		jlb_interest1.setVerticalAlignment(SwingConstants.CENTER);
		jlb_interest2.setVerticalAlignment(SwingConstants.CENTER);
		jlb_redo.setBounds(5, 5, 220, 50);
		jlb_interest1.setBounds(80, 250, 100, 50);
		jlb_interest2.setBounds(80, 310, 100, 50);
		add(jlb_redo);
		add(jlb_interest1);
		add(jlb_interest2);
	}
	public void initButton() {
		Font font = new Font("HY견고딕",Font.PLAIN,18);
		jbtn_select = new JButton("다  음");
		jbtn_select.setFont(font);
		jbtn_select.setBackground(new Color(231,164,100));
		jbtn_select.setFocusable(false);
		jbtn_select.setBounds(80, 500, 340, 60);
		add(jbtn_select);
	}
	public void initInput() {
		Font font  = new Font("HY견고딕",Font.PLAIN,16);
		jcb_interest1 = new JComboBox(interestList1);
		jcb_interest2 = new JComboBox(interestList2);
		jcb_interest1.setFont(font);
		jcb_interest2.setFont(font);
		jcb_interest1.setBounds(185, 265, 220, 30);
		jcb_interest2.setBounds(185, 325, 220, 30);
		add(jcb_interest1);
		add(jcb_interest2);
	}
	public void initEvent() {
		jlb_redo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				page.remove(page.jp_interest);
				page.add(page.jp_mentor);
				page.revalidate();
				page.repaint();
				super.mouseClicked(e);
			}
		});
		jbtn_select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sel1 = (String)jcb_interest1.getSelectedItem();
				String sel2 = (String)jcb_interest2.getSelectedItem();
				if(interestList1[0].equals(sel1)) {
					JOptionPane.showMessageDialog(SignUp_interest.this, "필수항목을 선택하세요.");
				}
				else {
					if(sel1.equals(sel2)) {
						JOptionPane.showMessageDialog(SignUp_interest.this, "같은 분야를 선택할 수 없습니다.");
					}
					else {
						page.memVO.setInterest1(sel1);
						if(!(interestList2[0].equals(sel2))) {
							page.memVO.setInterest2(sel2);
						}
						else {
							page.memVO.setInterest2("");
						}
						page.remove(page.jp_interest);
						if(page.jp_signup==null) {
							page.jp_signup 	= new SignUp_info(page);
						}
						page.add(page.jp_signup);
						page.revalidate();
						page.repaint();
					}
				}
			}
		});
	}
	
}
