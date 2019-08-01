package seontalk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import seontalk.control.ConnectionCtrl;
import seontalk.util.FilePath;
import seontalk.vo.MemberVO;

public class Login extends JFrame {
	JPanel 			jp_logo 	= null;
	JTextField  	jtf_id		= null;
	JPasswordField  jtf_pw		= null;
	JButton 		jbtn_login 	= null;
	JLabel 			jlb_signup 	= null;
	JLabel 			jlb_search	= null;
	
	MemberVO client = null;
	
	ConnectionCtrl ctrl = new ConnectionCtrl();
	
	public Login() {
		init();
	}
	
	public void init() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setTitle("선톡 - 멘토링 어플리케이션");
		setSize(400,600);
		initLogo();
		initTextField();
		initButton();
		initLabel();
		initEvent();
		initLocation();
		getContentPane().setBackground(new Color(255,224,200));
		setResizable(false);
		setVisible(true);
	}
	
	public void initTextField() {
		Font font = new Font("HY견고딕",Font.ITALIC,18);
		jtf_id = new JTextField();
		jtf_pw = new JPasswordField();
		jtf_id.setText("아이디 입력");
		jtf_pw.setText("비밀번호 입력");
		jtf_id.setFont(font);
		jtf_id.setFocusTraversalKeysEnabled(false);
		jtf_pw.setFocusTraversalKeysEnabled(false);
		jtf_id.setBounds(50, 330, 300, 30);
		jtf_pw.setBounds(50, 360, 300, 30);
		add(jtf_id);
		add(jtf_pw);
	}
	
	public void initButton() {
		Font font = new Font("HY견고딕",Font.PLAIN,18);
		jbtn_login = new JButton("로  그  인");
		jbtn_login.setFont(font);
		jbtn_login.setBackground(new Color(231,164,100));
		jbtn_login.setRolloverEnabled(false);
		jbtn_login.setBounds(50, 405, 300, 60);
		add(jbtn_login);
	}
	
	public void initLabel() {
		Font font = new Font("HY견고딕",Font.PLAIN,12);
		jlb_signup = new JLabel("회원가입");
		jlb_search = new JLabel("계정/비밀번호 찾기");
		jlb_signup.setFont(font);
		jlb_search.setFont(font);
		jlb_signup.setOpaque(true);
		jlb_search.setOpaque(true);
		jlb_signup.setBackground(new Color(255,224,200));
		jlb_search.setBackground(new Color(255,224,200));
		jlb_signup.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_search.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_signup.setVerticalAlignment(SwingConstants.CENTER);
		jlb_search.setVerticalAlignment(SwingConstants.CENTER);
		jlb_signup.setBounds(90, 530, 100, 20);
		jlb_search.setBounds(210, 530, 120, 20);
		add(jlb_signup);
		add(jlb_search);
	}
	public void initLogo() {
		jp_logo = new JPanel() {
			public void paintComponent(Graphics g) {
				Image img = new ImageIcon(FilePath.SrcPath+"선토끼.png").getImage();
				Graphics2D g2 = (Graphics2D)g;
				g2.drawImage(img, 0, 0, null);
				setOpaque(true);
			}
		};
		jp_logo.setBounds(80, 70, 240, 240);
		add(jp_logo);
	}
	
	public void initLocation() {
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((monitor.width-getSize().width)/2, (monitor.height-getSize().height)/2);
	}
	
	public void initEvent() {
		jtf_id.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(jtf_id.getText())) {
					jtf_id.setText("아이디 입력");
				}
				super.focusLost(e);
			}
		});
		jtf_pw.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if("".equals(jtf_pw.getText())) {
					jtf_pw.setText("비밀번호 입력");
				}
				super.focusLost(e);
			}
		});
		jtf_id.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if("아이디 입력".equals(jtf_id.getText())) {
					jtf_id.setText("");
				}
				super.mouseClicked(e);
			}
		});
		jtf_pw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if("비밀번호 입력".equals(jtf_pw.getText())) {
					jtf_pw.setText("");
				}
				super.mouseClicked(e);
			}
		});
		jtf_id.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if("아이디 입력".equals(jtf_id.getText())) {
					jtf_id.setText("");
				}
				if(e.getKeyChar()==KeyEvent.VK_TAB) {
					if("비밀번호 입력".equals(jtf_pw.getText())) {
						jtf_pw.setText("");
					}
					jtf_pw.requestFocus();
				}
				super.keyPressed(e);
			}
		});
		jtf_pw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if("비밀번호 입력".equals(jtf_pw.getText())) {
					jtf_pw.setText("");
				}
				if(e.getKeyChar()==KeyEvent.VK_TAB) {
					if("아이디 입력".equals(jtf_id.getText())) {
						jtf_id.setText("");
					}
					jtf_id.requestFocus();
				}
				super.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==10) {
					jtf_id.requestFocus();
					MemberVO pVO = new MemberVO();
					pVO.setId(jtf_id.getText());
					pVO.setPw(jtf_pw.getText());
					MemberVO rVO = null;
					rVO = (MemberVO)ctrl.Connect("update", "login", pVO);
					int status = rVO.getStatus();
					if(status==1) {
						String result = rVO.getKeyword();
						if("아이디가 존재하지 않습니다.".equals(result)) {
							JOptionPane.showMessageDialog(Login.this, "아이디가 존재하지 않습니다.");
						}
						else if("비밀번호가 틀립니다.".equals(result)) {
							JOptionPane.showMessageDialog(Login.this, "비밀번호가 틀립니다.");
						}
						else if("이미 로그인되어있습니다.".equals(result)) {
							JOptionPane.showMessageDialog(Login.this, "이미 로그인되어있습니다.");
						}
						else {
							setVisible(false);
							List<Object> login = ctrl.ConnectSelect("select", "check_id", pVO);
							client = (MemberVO)login.get(0);
							new MainPage(client);
						}
					}
					else {
						JOptionPane.showMessageDialog(Login.this, "DB Connection Error");
					}
				}
				super.keyReleased(e);
			}
		});
		jbtn_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MemberVO pVO = new MemberVO();
				pVO.setId(jtf_id.getText());
				pVO.setPw(jtf_pw.getText());
				MemberVO rVO = null;
				rVO = (MemberVO)ctrl.Connect("update", "login", pVO);
				int status = rVO.getStatus();
				if(status==1) {
					String result = rVO.getKeyword();
					if("아이디가 존재하지 않습니다.".equals(result)) {
						JOptionPane.showMessageDialog(Login.this, "아이디가 존재하지 않습니다.");
					}
					else if("비밀번호가 틀립니다.".equals(result)) {
						JOptionPane.showMessageDialog(Login.this, "비밀번호가 틀립니다.");
					}
					else if("이미 로그인되어있습니다.".equals(result)) {
						JOptionPane.showMessageDialog(Login.this, "이미 로그인되어있습니다.");
					}
					else {
						setVisible(false);
						List<Object> login = ctrl.ConnectSelect("select", "check_id", pVO);
						client = (MemberVO)login.get(0);
						new MainPage(client);
					}
				}
				else {
					JOptionPane.showMessageDialog(Login.this, "DB Connection Error");
				}
			}
		});
		jlb_signup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SignUp();
				super.mouseClicked(e);
			}
		});
		jlb_search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "아직 안돼요....");
				super.mouseClicked(e);
			}
		});
	}
	public static void main(String[] args) {
		new Login();
	}
}
