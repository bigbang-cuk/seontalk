package seontalk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import seontalk.control.ConnectionCtrl;
import seontalk.util.FilePath;

public class SignUp_info extends JPanel{
	JButton 		jbtn_signup  	 = null;
	JButton			jbtn_checkID   	 = null;
	JButton			jbtn_checkNick   = null;
	JLabel  		jlb_redo	 	 = null;
	JLabel 			jlb_id 		 	 = null;
	JLabel 			jlb_pw 		 	 = null;
	JLabel 			jlb_pwchk	 	 = null;
	JLabel 			jlb_name 	 	 = null;
	JLabel 			jlb_nick 	 	 = null;
	JLabel 			jlb_hp 		 	 = null;
	JLabel 			jlb_birth 	 	 = null;
	JTextField 		jtf_id 		 	 = null;
	JPasswordField 	jtf_pw 		 	 = null;
	JPasswordField 	jtf_pwchk	 	 = null;
	JTextField 		jtf_name 	 	 = null;
	JTextField 		jtf_nick 	 	 = null;
	JTextField 		jtf_hp1 	 	 = null;
	JTextField 		jtf_hp2 	 	 = null;
	JTextField 		jtf_hp3 	 	 = null;
	JComboBox 		jcb_year 	 	 = null;
	JComboBox 		jcb_month 	 	 = null;
	JComboBox 		jcb_day 	 	 = null;
	SignUp 			page 		 	 = null;
	int 			checkId		 	 = 0;
	int 			checkNick	 	 = 0;
	
	public SignUp_info(SignUp page) {
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
		initCombo();
		initEvent();
	}
	public void initLabel() {
		Font font  = new Font("HY견고딕",Font.PLAIN,16);
		Color color = new Color(255,224,200);
		jlb_redo   = new JLabel("뒤로가기"
				,new ImageIcon(FilePath.SrcPath+"preview01.png")
				,SwingConstants.LEFT);
		jlb_id = new JLabel("아이디");
		jlb_pw = new JLabel("비밀번호");
		jlb_pwchk = new JLabel("비번확인");
		jlb_name = new JLabel("이름");
		jlb_nick = new JLabel("닉네임");
		jlb_hp = new JLabel("전화번호");
		jlb_birth = new JLabel("생년월일");
		jlb_redo.setFont(font);
		jlb_id.setFont(font);	
		jlb_pw.setFont(font);
		jlb_pwchk.setFont(font);
		jlb_name.setFont(font);
		jlb_nick.setFont(font);
		jlb_hp.setFont(font);
		jlb_birth.setFont(font);
		jlb_redo.setOpaque(true);
		jlb_id.setOpaque(true);
		jlb_pw.setOpaque(true);
		jlb_pwchk.setOpaque(true);
		jlb_name.setOpaque(true);
		jlb_nick.setOpaque(true);
		jlb_hp.setOpaque(true);
		jlb_birth.setOpaque(true);
		jlb_redo.setBackground(color);
		jlb_id.setBackground(color);
		jlb_pw.setBackground(color);
		jlb_pwchk.setBackground(color);
		jlb_name.setBackground(color);
		jlb_nick.setBackground(color);
		jlb_hp.setBackground(color);
		jlb_birth.setBackground(color);
		jlb_id.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_pw.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_pwchk.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_name.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_nick.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_hp.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_id.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_birth.setVerticalAlignment(SwingConstants.CENTER);
		jlb_id.setVerticalAlignment(SwingConstants.CENTER);
		jlb_pw.setVerticalAlignment(SwingConstants.CENTER);
		jlb_pwchk.setVerticalAlignment(SwingConstants.CENTER);
		jlb_name.setVerticalAlignment(SwingConstants.CENTER);
		jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_hp.setVerticalAlignment(SwingConstants.CENTER);
		jlb_birth.setVerticalAlignment(SwingConstants.CENTER);
		jlb_redo.setBounds(5, 5, 100, 50);
		jlb_id.setBounds(80, 100, 80, 30);
		jlb_pw.setBounds(80, 150, 80, 30);
		jlb_pwchk.setBounds(80, 200, 80, 30);
		jlb_name.setBounds(80, 250, 80, 30);
		jlb_nick.setBounds(80, 300, 80, 30);
		jlb_hp.setBounds(80, 350, 80, 30);
		jlb_birth.setBounds(80, 400, 80, 30);
		add(jlb_redo);
		add(jlb_id);
		add(jlb_pw);
		add(jlb_pwchk);
		add(jlb_name);
		add(jlb_nick);
		add(jlb_hp);
		add(jlb_birth);
	}
	public void initButton() {
		Font font = new Font("HY견고딕",Font.PLAIN,18);
		Font font2 = new Font("HY견고딕",Font.PLAIN,12);
		jbtn_signup = new JButton("다  음");
		jbtn_checkID  = new JButton("중복검사");
		jbtn_checkNick  = new JButton("중복검사");
		jbtn_signup.setFont(font);
		jbtn_checkID.setFont(font2);
		jbtn_checkNick.setFont(font2);
		jbtn_signup.setBackground(new Color(231,164,100));
		jbtn_checkID.setBackground(Color.LIGHT_GRAY);
		jbtn_checkNick.setBackground(Color.LIGHT_GRAY);
		jbtn_signup.setFocusable(false);
		jbtn_checkID.setFocusable(false);
		jbtn_checkNick.setFocusable(false);
		jbtn_signup.setBounds(80, 500, 340, 60);
		jbtn_checkID.setBounds(370, 100, 90, 30);
		jbtn_checkNick.setBounds(370, 300, 90, 30);
		add(jbtn_signup);
		add(jbtn_checkID);
		add(jbtn_checkNick);
	}
	public void initInput() {
		Font font  = new Font("HY견고딕",Font.PLAIN,16);
		jtf_id 		= new JTextField();
		jtf_pw 		= new JPasswordField();
		jtf_pwchk	= new JPasswordField();
		jtf_name	= new JTextField();
		jtf_nick	= new JTextField();
		jtf_hp1 	= new JTextField();
		jtf_hp2 	= new JTextField();
		jtf_hp3 	= new JTextField();
		jtf_id.setFont(font);
		jtf_name.setFont(font);
		jtf_nick.setFont(font);
		jtf_hp1.setFont(font);
		jtf_hp2.setFont(font);
		jtf_hp3.setFont(font);
		jtf_id.setBounds(160, 100, 200, 30);
		jtf_pw.setBounds(160, 150, 200, 30);
		jtf_pwchk.setBounds(160, 200, 200, 30);
		jtf_name.setBounds(160, 250, 200, 30);
		jtf_nick.setBounds(160, 300, 200, 30);
		jtf_hp1.setBounds(160, 350, 80, 30);
		jtf_hp2.setBounds(245, 350, 80, 30);
		jtf_hp3.setBounds(330, 350, 80, 30);
		add(jtf_id);
		add(jtf_pw);
		add(jtf_pwchk);
		add(jtf_name);
		add(jtf_nick);
		add(jtf_hp1);
		add(jtf_hp2);
		add(jtf_hp3);
		
	}
	public void initCombo() {
		Font font  = new Font("HY견고딕",Font.PLAIN,16);
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
		jcb_year.setBounds(160, 400, 100, 30);
		jcb_month.setBounds(265, 400, 60, 30);
		jcb_day.setBounds(330, 400, 60, 30);
		add(jcb_year);
		add(jcb_month);
		add(jcb_day);
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
		jlb_redo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				page.remove(page.jp_signup);
				page.add(page.jp_interest);
				page.revalidate();
				page.repaint();
				super.mouseClicked(e);
			}
		});
		jcb_month.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Font font  = new Font("HY견고딕",Font.PLAIN,16);
				remove(jcb_day);
				jcb_day = null;
				String [] dayList = setComboDay(jcb_month);
				jcb_day = new JComboBox(dayList);
				jcb_day.setBounds(330, 400, 60, 30);
				jcb_day.setFont(font);
				add(jcb_day);
				revalidate();
				repaint();
			}
		});
		jbtn_checkID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtf_id.getText().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "아이디를 입력하세요");
					jtf_id.requestFocus();
				}
				else {
					page.memVO.setId(jtf_id.getText());
					ConnectionCtrl ctrl = new ConnectionCtrl();
					List<Object> VOList = ctrl.ConnectSelect("select", "check_id", page.memVO);
					if(VOList.size()!=0) {
						JOptionPane.showMessageDialog(SignUp_info.this, "이미 존재합니다");
						jtf_id.requestFocus();
					}
					else {
						JOptionPane.showMessageDialog(SignUp_info.this, "사용가능한 아이디입니다");
						checkId = 1;
					}
				}
			}
		});
		jbtn_checkNick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtf_nick.getText().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "닉네임를 입력하세요");
					jtf_nick.requestFocus();
				}
				else {
					page.memVO.setNick(jtf_nick.getText());
					ConnectionCtrl ctrl = new ConnectionCtrl();
					List<Object> VOList = ctrl.ConnectSelect("select", "check_nick", page.memVO);
					if(VOList.size()!=0) {
						JOptionPane.showMessageDialog(SignUp_info.this, "이미 존재하는 닉네임입니다");
						jtf_nick.requestFocus();
					}
					else {
						JOptionPane.showMessageDialog(SignUp_info.this, "사용가능한 닉네임입니다");
						checkNick = 1;
					}
				}
			}
		});
		jbtn_signup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtf_id.getText().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "아이디를 입력하세요.");
					jtf_id.requestFocus();
				}
				else if(jtf_pw.getText().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "비밀번호를 입력하세요.");
					jtf_pw.requestFocus();
				}
				else if(jtf_pwchk.getText().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "비번확인을 입력하세요.");
					jtf_pwchk.requestFocus();
				}
				else if(jtf_name.getText().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "이름을 입력하세요.");
					jtf_name.requestFocus();
				}
				else if(jtf_nick.getText().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "닉네임을 입력하세요.");
					jtf_nick.requestFocus();
				}
				else if(jtf_hp1.getText().length()==0
					||jtf_hp2.getText().length()==0
					||jtf_hp3.getText().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "전화번호를 입력하세요.");
					jtf_hp1.requestFocus();
				}
				else if(jcb_day.getSelectedItem()==null
					||jcb_day.getSelectedItem().toString().length()==0) {
					JOptionPane.showMessageDialog(SignUp_info.this, "생년월일을 확인하세요.");
					jcb_year.requestFocus();
				}
				else {
					page.memVO.setPw(jtf_pw.getText());
					String pwCheck = jtf_pwchk.getText();
					page.memVO.setName(jtf_name.getText());
					page.memVO.setNick(jtf_nick.getText());
					page.memVO.setHp(jtf_hp1.getText()+"-"+jtf_hp2.getText()+"-"+jtf_hp3.getText());
					page.memVO.setBirth(jcb_year.getSelectedItem().toString()
							+"-"+jcb_month.getSelectedItem().toString()
							+"-"+jcb_day.getSelectedItem().toString());
					if(checkId==1&&page.memVO.getId().equals(jtf_id.getText())) {
						if(jtf_pw.getText().equals(jtf_pwchk.getText())) {
							if(checkNick==1&&page.memVO.getNick().equals(jtf_nick.getText())) {
								page.remove(page.jp_signup);
								if(page.jp_confirm==null) {
									page.jp_confirm  = new SignUp_confirm(page);
								}
								page.add(page.jp_confirm);
								page.revalidate();
								page.repaint();
							}
							else {
								JOptionPane.showMessageDialog(SignUp_info.this, "닉네임 중복검사를 해야합니다");
								jtf_nick.requestFocus();
								checkNick=0;
							}
						}
						else {
							JOptionPane.showMessageDialog(SignUp_info.this, "비밀번호가 서로 다릅니다");
							jtf_pw.requestFocus();
						}
					}
					else{
						JOptionPane.showMessageDialog(SignUp_info.this, "아이디 중복검사를 해야합니다");
						jtf_id.requestFocus();
						checkId=0;
					}
					
				}
			}
		});
	}
}
