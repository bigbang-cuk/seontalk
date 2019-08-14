package seontalk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class MyProfileUpd extends JDialog {
	JLabel		jlb_name		= null;
	JLabel		jlb_nick		= null;
	JLabel		jlb_hp			= null;
	JLabel		jlb_birth		= null;
	JLabel		jlb_interest1	= null;
	JLabel		jlb_interest2	= null;
	JButton		jbtn_checkNick  = null;
	JButton		jbtn_update		= null;
	JTextField 	jtf_name 	 	= null;
	JTextField 	jtf_nick 	 	= null;
	JTextField 	jtf_hp1 	 	= null;
	JTextField 	jtf_hp2 	 	= null;
	JTextField 	jtf_hp3 	 	= null;
	JComboBox 	jcb_year 	 	= null;
	JComboBox 	jcb_month 	 	= null;
	JComboBox 	jcb_day 	 	= null;
	JComboBox 	jcb_interest1 	= null;
	JComboBox 	jcb_interest2 	= null;
	String[]	interestList1	= {"(필수항목)","자바,JSP","오라클 SQL","자바스크립트","HTML","안드로이드"};
	String[]	interestList2	= {"(선택항목)","자바,JSP","오라클 SQL","자바스크립트","HTML","안드로이드"};

	MainPage page = null;
	Theme theme = new Theme();
	
	int checkNick = 0;
	
	public MyProfileUpd(MainPage page) {
		this.page = page;
		init();
	}
	public void init() {
		setLayout(null);
		setSize(300, 450);
		initLabel();
		initButton();
		initTextField();
		initCombo();
		initEvent();
		getContentPane().setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		setResizable(false);
		setVisible(true);
	}
	public void initLabel() {
		Font font  = new Font(page.memVO.getFont(),Font.PLAIN,12);
		jlb_name = new JLabel("이름");
		jlb_nick = new JLabel("닉네임");
		jlb_hp = new JLabel("전화번호");
		jlb_birth = new JLabel("생년월일");
		jlb_interest1 = new JLabel("관심분야1");
		jlb_interest2 = new JLabel("관심분야2");
		jlb_name.setFont(font);
		jlb_nick.setFont(font);
		jlb_hp.setFont(font);
		jlb_birth.setFont(font);
		jlb_interest1.setFont(font);
		jlb_interest2.setFont(font);
		jlb_name.setOpaque(true);
		jlb_nick.setOpaque(true);
		jlb_hp.setOpaque(true);
		jlb_birth.setOpaque(true);
		jlb_interest1.setOpaque(true);
		jlb_interest2.setOpaque(true);
		jlb_name.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_hp.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_birth.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_interest1.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_interest2.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_name.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_nick.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_hp.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_birth.setVerticalAlignment(SwingConstants.CENTER);
		jlb_interest1.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_interest2.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_name.setVerticalAlignment(SwingConstants.CENTER);
		jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_hp.setVerticalAlignment(SwingConstants.CENTER);
		jlb_birth.setVerticalAlignment(SwingConstants.CENTER);
		jlb_interest1.setVerticalAlignment(SwingConstants.CENTER);
		jlb_interest2.setVerticalAlignment(SwingConstants.CENTER);
		jlb_name.setBounds(20, 50, 60, 20);
		jlb_nick.setBounds(20, 100, 60, 20);
		jlb_hp.setBounds(20, 150, 60, 20);
		jlb_birth.setBounds(20, 200, 60, 20);
		jlb_interest1.setBounds(20, 250, 65, 20);
		jlb_interest2.setBounds(20, 300, 65, 20);
		add(jlb_name);
		add(jlb_nick);
		add(jlb_hp);
		add(jlb_birth);
		add(jlb_interest1);
		add(jlb_interest2);
	}
	public void initButton() {
		Font font = new Font(page.memVO.getFont(),Font.PLAIN,18);
		Font font2 = new Font(page.memVO.getFont(),Font.PLAIN,10);
		jbtn_checkNick  = new JButton("중복검사");
		jbtn_update  = new JButton("수  정");
		jbtn_checkNick.setFont(font2);
		jbtn_update.setFont(font);
		jbtn_checkNick.setBackground(Color.LIGHT_GRAY);
		jbtn_update.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_checkNick.setFocusable(false);
		jbtn_update.setFocusable(false);
		jbtn_checkNick.setBounds(210, 100, 80, 20);
		jbtn_update.setBounds(40, 360, 220, 40);
		add(jbtn_checkNick);
		add(jbtn_update);
	}
	public void initTextField() {
		Font font  = new Font(page.memVO.getFont(),Font.PLAIN,12);
		jtf_name	= new JTextField();
		jtf_nick	= new JTextField();
		jtf_hp1 	= new JTextField();
		jtf_hp2 	= new JTextField();
		jtf_hp3 	= new JTextField();
		jtf_name.setFont(font);
		jtf_nick.setFont(font);
		jtf_hp1.setFont(font);
		jtf_hp2.setFont(font);
		jtf_hp3.setFont(font);
		jtf_name.setBounds(80, 50, 120, 20);
		jtf_nick.setBounds(80, 100, 120, 20);
		jtf_hp1.setBounds(80, 150, 50, 20);
		jtf_hp2.setBounds(135, 150, 50, 20);
		jtf_hp3.setBounds(190, 150, 50, 20);
		add(jtf_name);
		add(jtf_nick);
		add(jtf_hp1);
		add(jtf_hp2);
		add(jtf_hp3);
	}
	public void initCombo() {
		Font font  = new Font("HY견고딕",Font.PLAIN,12);
		String [] yearList= new String[100];
		for(int i=0;i<yearList.length;i++) {
			int year = 1920+i;
			yearList[i] = String.valueOf(year);
		}
		String [] monthList= {"01","02","03","04","05","06","07","08","09","10","11","12"};
		jcb_year = new JComboBox(yearList);
		jcb_month = new JComboBox(monthList);
		jcb_day = new JComboBox();
		jcb_year.setFont(font);
		jcb_month.setFont(font);
		jcb_day.setFont(font);
		jcb_year.setBounds(80, 200, 70, 20);
		jcb_month.setBounds(155, 200, 50, 20);
		jcb_day.setBounds(210, 200, 50, 20);
		add(jcb_year);
		add(jcb_month);
		add(jcb_day);
		jcb_interest1 = new JComboBox(interestList1);
		jcb_interest2 = new JComboBox(interestList2);
		jcb_interest1.setFont(font);
		jcb_interest2.setFont(font);
		jcb_interest1.setBounds(90, 250, 150, 20);
		jcb_interest2.setBounds(90, 300, 150, 20);
		add(jcb_interest1);
		add(jcb_interest2);
	}
	public String[] setComboDay(JComboBox combo_month) {
		String [] dayList = null;
		if("01"==combo_month.getSelectedItem().toString()||
				"03"==combo_month.getSelectedItem().toString()||
				"05"==combo_month.getSelectedItem().toString()||
				"07"==combo_month.getSelectedItem().toString()||
				"08"==combo_month.getSelectedItem().toString()||
				"10"==combo_month.getSelectedItem().toString()||
				"12"==combo_month.getSelectedItem().toString()) {
			dayList= new String[31];
			for(int i=0;i<dayList.length;i++) {
				String day = String.valueOf(i+1);
				if(i<9) {
					day = "0"+day;
				}
				dayList[i] = day;
			}
		}
		else if("04"==combo_month.getSelectedItem().toString()||
				"06"==combo_month.getSelectedItem().toString()||
				"09"==combo_month.getSelectedItem().toString()||
				"11"==combo_month.getSelectedItem().toString()) {
			dayList= new String[30];
			for(int i=0;i<dayList.length;i++) {
				String day = String.valueOf(i+1);
				if(i<9) {
					day = "0"+day;
				}
				dayList[i] = day;
			}
		}
		else if("02"==combo_month.getSelectedItem().toString()) {
			dayList= new String[28];
			for(int i=0;i<dayList.length;i++) {
				String day = String.valueOf(i+1);
				if(i<9) {
					day = "0"+day;
				}
				dayList[i] = day;
			}
		}
		return dayList;
	}
	public void initEvent() {
		jcb_month.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font  = new Font("HY견고딕",Font.PLAIN,12);
				remove(jcb_day);
				jcb_day = null;
				String [] dayList = setComboDay(jcb_month);
				jcb_day = new JComboBox(dayList);
				jcb_day.setBounds(210, 200, 50, 20);
				jcb_day.setFont(font);
				add(jcb_day);
				revalidate();
				repaint();
			}
		});
		jbtn_checkNick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtf_nick.getText().length()==0) {
					JOptionPane.showMessageDialog(MyProfileUpd.this, "닉네임를 입력하세요");
					jtf_nick.requestFocus();
				}
				else {
					MemberVO pVO = new MemberVO();
					pVO.setNick(jtf_nick.getText());
					List<Object> VOList = page.ctrl.ConnectSelect("select", "check_nick", pVO);
					if(VOList.size()!=0) {
						if(((MemberVO)VOList.get(0)).getNick().equals(page.memVO.getNick())) {
							int result = JOptionPane.showConfirmDialog(MyProfileUpd.this, "기존 닉네임을 사용하시겠습니까?"
									, "닉네임 중복검사", JOptionPane.YES_NO_OPTION);
							if(result==JOptionPane.OK_OPTION) {
								page.memVO.setNick(jtf_nick.getText());
								checkNick = 1;
							}
						}
						else {
							JOptionPane.showMessageDialog(MyProfileUpd.this, "이미 존재하는 닉네임입니다");
							jtf_nick.requestFocus();
						}
					}
					else {
						JOptionPane.showMessageDialog(MyProfileUpd.this, "사용가능한 닉네임입니다");
						page.memVO.setNick(jtf_nick.getText());
						checkNick = 1;
					}
				}
			}
		});
		jbtn_update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sel1 = (String)jcb_interest1.getSelectedItem();
				String sel2 = (String)jcb_interest2.getSelectedItem();
				if(jtf_name.getText().length()==0) {
					JOptionPane.showMessageDialog(MyProfileUpd.this, "이름 입력하세요.");
					jtf_name.requestFocus();
				}
				else if(jtf_nick.getText().length()==0) {
					JOptionPane.showMessageDialog(MyProfileUpd.this, "닉네임을 입력하세요.");
					jtf_nick.requestFocus();
				}
				else if(jtf_hp1.getText().length()==0
					||jtf_hp2.getText().length()==0
					||jtf_hp3.getText().length()==0) {
					JOptionPane.showMessageDialog(MyProfileUpd.this, "전화번호를 입력하세요.");
					jtf_hp1.requestFocus();
				}
				else if(jcb_day.getSelectedItem()==null
					||jcb_day.getSelectedItem().toString().length()==0) {
					JOptionPane.showMessageDialog(MyProfileUpd.this, "생년월일을 확인하세요.");
					jcb_year.requestFocus();
				}
				else if(interestList1[0].equals(sel1)) {
					JOptionPane.showMessageDialog(MyProfileUpd.this, "필수항목을 선택하세요.");
				}
				else {
					if(sel1.equals(sel2)) {
						JOptionPane.showMessageDialog(MyProfileUpd.this, "같은 분야를 선택할 수 없습니다.");
					}
					else {
						page.memVO.setInterest1(sel1);
						if(!(interestList2[0].equals(sel2))) {
							page.memVO.setInterest2(sel2);
						}
						else {
							page.memVO.setInterest2("");
						}
						page.memVO.setName(jtf_name.getText());
						page.memVO.setHp(jtf_hp1.getText()+"-"+jtf_hp2.getText()+"-"+jtf_hp3.getText());
						page.memVO.setBirth(jcb_year.getSelectedItem().toString()
								+"/"+jcb_month.getSelectedItem().toString()
								+"/"+jcb_day.getSelectedItem().toString());
						if(checkNick==1&&page.memVO.getNick().equals(jtf_nick.getText())) {
							MemberVO rVO = null;
							rVO = (MemberVO)page.ctrl.Connect("update", "upd_info", page.memVO);
							if(rVO.getStatus()==1) {
								page.setTitle(page.memVO.getNick()+"-선톡");
								JOptionPane.showMessageDialog(MyProfileUpd.this, "수정되었습니다");
								MyProfileUpd.this.setVisible(false);
								if(page.jp_myPage.mp!=null) {
									page.jp_myPage.mp.setVisible(false);
								}
								page.jp_myPage.mp = new MyProfile(page);
								page.getChatList();
							}
							else {
								JOptionPane.showMessageDialog(MyProfileUpd.this, "수정 실패");
							}
						}
						else {
							JOptionPane.showMessageDialog(MyProfileUpd.this, "닉네임 중복검사를 해야합니다");
							jtf_nick.requestFocus();
							checkNick=0;
						}
					}
				}
			}
		});
	}
}
