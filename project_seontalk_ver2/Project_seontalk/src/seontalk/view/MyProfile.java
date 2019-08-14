package seontalk.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.MemberVO;
 

public class MyProfile extends JFrame {
	JPanel jp_north = new JPanel();
	JPanel jp_south = new JPanel();
	//////////프로필 선언부////////////////////////////
    JPanel jp_profile = new JPanel();
    JLabel jlb_post 			= null;
    JLabel jlb_post_get 		= null;
    JLabel jlb_following 		= null;
    JLabel jlb_following_get 	= null;
    JLabel jlb_follower 		= null;
    JLabel jlb_follower_get 	= null;
    JLabel jlb_mentoring 		= null;
    JLabel jlb_jumsu 			= null;
    JLabel jlb_nickname 		= null;
    JLabel jlb_prfmsg 			= null;
    
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    
    /////////////////정보 리스트 선언부//////////////////////
    GridLayout bl = new GridLayout(6,0,20,20);
    
    JLabel jlb_mem_name			= null;
    JLabel jlb_user_name 		= null;
    JLabel jlb_ph 				= null;
    JLabel jlb_user_ph 			= null;
    
    JButton jbtn_myInfo 	= new JButton("내 정보 변경");
    JButton jbtn_myMsg 		= new JButton("상태메세지 변경");
    JButton jbtn_myPw 		= new JButton("비밀번호 변경");
    JButton jbtn_myDefault 	= new JButton("프로필이미지 제거");
    MainPage page = null;
    Theme theme = new Theme();
    
    List<Object> followingList = new Vector<>();
	List<Object> followerList  = new Vector<>();
    
    public MyProfile(MainPage page) {
    	this.page = page;
    	getList();
    	init();
    }
    public void getList() {
		followingList = page.ctrl.ConnectSelect("select", "following", page.memVO);
		followerList = page.ctrl.ConnectSelect("select", "follower", page.memVO);
    }
    public void init() {
    	setTitle(page.memVO.getNick()+"-내정보");
    	setSize(410,545);
    	initLabel();
    	initPanel();
    	initGridBack();
    	initButton();
    	initEvent();
    	initLocation();
    	setResizable(false);
    	setVisible(true);
    }
    public void initLocation() {
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension mainPageSize = page.getSize();
		Point point = page.getLocation();
		if(point.x<getSize().width) {
			if(monitor.height-(point.y+getSize().height)<0) {
				setLocation(point.x+(mainPageSize.width+5), monitor.height-getSize().height);
			}
			else {
				setLocation(point.x+(mainPageSize.width+5), point.y);
			}
		}
		else {
			if(monitor.height-(point.y+getSize().height)<0) {
				setLocation(point.x-(getSize().width+5), monitor.height-getSize().height);
			}
			else {
				setLocation(point.x-(getSize().width+5), point.y);
			}
		}
	}
    public void initPanel() {
    	//North Panel
    	jp_north.setLayout(gbl);
    	jp_north.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    	jp_north.setBorder(BorderFactory.createEmptyBorder(10 , 40 , 10 , 40)); 
    	jp_north.setSize(410, 200); 
    	jp_profile.setLayout(new BoxLayout(jp_profile,BoxLayout.Y_AXIS));
    	jp_profile.add(new ProfileImg(page.memVO.getProfile_img(),0,0,130,130));
    	jp_north.add(jp_profile);
    	add(jp_north,BorderLayout.NORTH);
    	
    	//South Panel
    	jp_south.setLayout(bl);
    	jp_south.setBorder(BorderFactory.createEmptyBorder(10 , 20 , 10 , 20)); 
    	jp_south.setSize(410,345);
    	jp_south.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
    	jp_south.setVisible(true);
    	add(jp_south);
    	
    }
    public void initLabel(){
    	Font font = new Font(page.memVO.getFont(),Font.PLAIN,16);
    	//North Label
    	jlb_post 			= new JLabel("게시물");            
    	jlb_post_get 		= new JLabel("0"+"명");        
    	jlb_following 		= new JLabel("팔로잉");       
    	jlb_following_get 	= new JLabel(followingList.size()+"명");   
    	jlb_follower 		= new JLabel("팔로워");        
    	jlb_follower_get 	= new JLabel(followerList.size()+"명");
    	if("mentor".equals(page.memVO.getMentoring())) {
    		jlb_mentoring 		= new JLabel("멘토");           
    	}
    	else {
    		jlb_mentoring 		= new JLabel("멘티");           
    	}
    	jlb_jumsu 			= new JLabel(String.valueOf(page.memVO.getRank_pt())+"pt");            
    	jlb_nickname 		= new JLabel(page.memVO.getNick());        
    	jlb_prfmsg 			= new JLabel(page.memVO.getProfile_msg());   
    	jlb_post.setHorizontalAlignment(SwingConstants.CENTER);
    	jlb_post_get.setHorizontalAlignment(SwingConstants.CENTER);
    	jlb_following.setHorizontalAlignment(SwingConstants.CENTER);
    	jlb_following_get.setHorizontalAlignment(SwingConstants.CENTER);
    	jlb_follower.setHorizontalAlignment(SwingConstants.CENTER);
    	jlb_follower_get.setHorizontalAlignment(SwingConstants.CENTER);
    	jlb_mentoring.setHorizontalAlignment(SwingConstants.LEFT);
    	jlb_jumsu.setHorizontalAlignment(SwingConstants.RIGHT);
    	jlb_nickname.setHorizontalAlignment(SwingConstants.CENTER);
    	jlb_prfmsg.setHorizontalAlignment(SwingConstants.CENTER);
    	jlb_post.setFont(font);
    	jlb_post_get.setFont(font);
    	jlb_following.setFont(font);
    	jlb_following_get.setFont(font);
    	jlb_follower.setFont(font);
    	jlb_follower_get.setFont(font);
    	jlb_mentoring.setFont(font);
    	jlb_jumsu.setFont(font);
    	jlb_nickname.setFont(font);
    	jlb_prfmsg.setFont(font);
    	jp_north.add(jlb_post);
    	jp_north.add(jlb_post_get);
    	jp_north.add(jlb_following);
    	jp_north.add(jlb_following_get);
    	jp_north.add(jlb_follower);
    	jp_north.add(jlb_follower_get);
    	jp_north.add(jlb_mentoring);
    	jp_north.add(jlb_jumsu);
    	jp_north.add(jlb_nickname);
    	jp_north.add(jlb_prfmsg);
    	
    	//South Label
    	jlb_mem_name 	= new JLabel("이름",JLabel.LEFT);  
    	jlb_user_name 	= new JLabel(page.memVO.getName());               
    	jlb_ph 			= new JLabel("전화번호",JLabel.LEFT);      
    	jlb_user_ph 		= new JLabel(page.memVO.getHp());    
    	jlb_user_name.setSize(10, 10);
    	jp_south.add(jlb_mem_name);
    	jp_south.add(jlb_user_name);
    	jp_south.add(jlb_ph);
    	jp_south.add(jlb_user_ph);
    }
    public void initButton() {
    	jbtn_myInfo.setHorizontalAlignment(SwingConstants.CENTER);
    	jbtn_myMsg.setHorizontalAlignment(SwingConstants.CENTER);
    	jbtn_myPw.setHorizontalAlignment(SwingConstants.CENTER);
    	jbtn_myDefault.setHorizontalAlignment(SwingConstants.CENTER);
    	jbtn_myInfo.setBackground(theme.setInnerColor(page.memVO.getTheme()));
    	jbtn_myMsg.setBackground(theme.setInnerColor(page.memVO.getTheme()));
    	jbtn_myPw.setBackground(theme.setInnerColor(page.memVO.getTheme()));
    	jbtn_myDefault.setBackground(theme.setInnerColor(page.memVO.getTheme()));
    	jbtn_myInfo.setRolloverEnabled(false);
    	jbtn_myMsg.setRolloverEnabled(false);
    	jbtn_myPw.setRolloverEnabled(false);
    	jbtn_myDefault.setRolloverEnabled(false);
    	jbtn_myInfo.setFocusable(false);
    	jbtn_myMsg.setFocusable(false);
    	jbtn_myPw.setFocusable(false);
    	jbtn_myDefault.setFocusable(false);
    	jp_south.add(jbtn_myInfo);
    	jp_south.add(jbtn_myPw);
    	jp_south.add(jbtn_myMsg);
    	jp_south.add(jbtn_myDefault);
    	
    }
    public void initGridBack() {
    	gbc.fill = GridBagConstraints.BOTH;
    	gbc.weightx = 1.0;
    	gbc.weighty = 1.0;
    	make(jp_profile, 0, 0, 170, 130,1,2); // 아래의 make함수를 지정
    	make(jlb_post, 1, 0, 80, 175,1,3);
    	make(jlb_post_get, 1, 1, 80, 175,1,3);
    	make(jlb_following, 2, 0, 80, 175,1,3);
    	make(jlb_following_get, 2, 1, 80, 175,1,3);
    	make(jlb_follower, 3, 0, 80, 175,1,3);
    	make(jlb_follower_get, 3, 1, 80, 175,1,3);
    	make(jlb_mentoring, 0, 2, 170, 25,1,1);
    	make(jlb_jumsu, 0, 2, 170, 25,1,1);
    	make(jlb_nickname, 0, 3, 170, 25,1,1);
    	make(jlb_prfmsg, 1, 3, 240, 25,4,1);
    }
    public void initEvent() {
    	jbtn_myInfo.addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent arg0) {
    			new MyProfileUpd(page);
    		}
    	});
    	jbtn_myPw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pw = JOptionPane.showInputDialog(MyProfile.this,"현재 비밀번호를 입력하세요.");
				String newPw = null;
				String newPwChk = null;
				if(pw!=null) {
					if(pw.equals(page.memVO.getPw())) {
						boolean isStop = false;
						while(!isStop) {
							newPw = JOptionPane.showInputDialog(MyProfile.this,"새로운 비밀번호를 입력하세요.");
							if(newPw==null) {
								isStop = true;
								break;
							}
							else if(pw.equals(newPw)) {
								JOptionPane.showMessageDialog(MyProfile.this, "현재 비밀번호과 다른 비밀번호를 입력하세요");
							}
							else {
								newPwChk = JOptionPane.showInputDialog(MyProfile.this,"비밀번호 확인");
								if(newPwChk==null) {
									isStop = true;
									break;
								}
								if(newPw.equals(newPwChk)) {
									page.memVO.setPw(newPw);
									MemberVO rVO = null;
									rVO = (MemberVO)page.ctrl.Connect("update", "upd_pw", page.memVO);
									if(rVO.getStatus()==1) {
										JOptionPane.showMessageDialog(MyProfile.this, "비밀번호가 변경되었습니다\n다시 로그인하세요");
										page.jp_myPage.mp.setVisible(false);
										isStop = true;
										new Login();
										page.Logout();
										page.dispose();
									}
									else {
										JOptionPane.showMessageDialog(MyProfile.this, "비밀번호 변경 실패");
										isStop = true;
									}
								}
								else {
									JOptionPane.showMessageDialog(MyProfile.this, "비밀번호가 다릅니다");
								}
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(MyProfile.this, "비밀번호가 다릅니다");
					}
				}
			}
		});
    	jp_profile.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent e) {
    			int result = JOptionPane.showConfirmDialog(MyProfile.this, "프로필 이미지를 변경할래요?");
    			if(result==0) {
    				JFileChooser jfc = new JFileChooser();
    				jfc.setDialogTitle("사진 선택");
    				jfc.setFileFilter(new FileNameExtensionFilter("JPG File", "jpg"));
    				jfc.setFileFilter(new FileNameExtensionFilter("PNG File", "png"));
    				jfc.setMultiSelectionEnabled(false);
    				int result2 = jfc.showSaveDialog(MyProfile.this);
    				if(result2==0) {
    					File file = jfc.getSelectedFile();
    					StringTokenizer st = new StringTokenizer(file.getName(), ".");
    					String imgName = file.getName();
    					String imgForm = "";
    					while(st.hasMoreTokens()) {
    						imgForm = st.nextToken();
    					}
    					try {
    						BufferedImage bi = ImageIO.read(file);
    						ImageIO.write(bi, imgForm, new File(FilePath.ImgPath+imgName));
						} catch (Exception e2) {
							e2.printStackTrace();
						}
    					page.memVO.setProfile_img(imgName);
    					MemberVO rVO = null;
    					rVO = (MemberVO)page.ctrl.Connect("update", "upd_img", page.memVO);
    					if(rVO.getStatus()==1) {
    						JOptionPane.showMessageDialog(MyProfile.this, "프로필사진이 변경되었습니다");
    						page.jp_myPage.mp.setVisible(false);
    						page.jp_myPage.mp = new MyProfile(page);
    					}
    					else {
    						JOptionPane.showMessageDialog(MyProfile.this, "프로필사진 변경 실패");
    					}
    				}
    			}
    			super.mouseClicked(e);
    		}
		});
    	jbtn_myMsg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = JOptionPane.showInputDialog(MyProfile.this, "상태메세지를 입력하세요");
				if(msg!=null) {
					page.memVO.setProfile_msg(msg);
					MemberVO rVO = null;
					rVO = (MemberVO)page.ctrl.Connect("update", "upd_msg", page.memVO);
					if(rVO.getStatus()==1) {
						JOptionPane.showMessageDialog(MyProfile.this, "상태메세지가 변경되었습니다");
						page.jp_myPage.mp.setVisible(false);
						page.jp_myPage.mp = new MyProfile(page);
					}
					else {
						JOptionPane.showMessageDialog(MyProfile.this, "상태메세지 변경 실패");
					}
				}
			}
		});
    	jbtn_myDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(MyProfile.this, "프로필사진을 제거하시겠습니까?"
											, "프로필사진 제거", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					page.memVO.setProfile_img("defaultImg.jpg");
					MemberVO rVO = null;
					rVO = (MemberVO)page.ctrl.Connect("update", "upd_img", page.memVO);
					if(rVO.getStatus()==1) {
						JOptionPane.showMessageDialog(MyProfile.this, "프로필사진이 변경되었습니다");
						page.jp_myPage.mp.setVisible(false);
						page.jp_myPage.mp = new MyProfile(page);
					}
					else {
						JOptionPane.showMessageDialog(MyProfile.this, "프로필사진 변경 실패");
					}
				}
			}
		});
    }
    
    // make함수를 내가 지정합니다.
    // jcomponent인 jbutton의 객체에 x,y의 좌표의 시작점에서 w,h 크기의 단추를 만듭니다
    public void make(JComponent c, int x, int y, int w, int h, int q, int r) {
    	gbc.gridx = x;
    	gbc.gridy = y;
    	gbc.ipadx=w;
    	gbc.ipady=h;
    	gbc.gridwidth = q;
    	gbc.gridheight = r;
    	gbl.setConstraints(c, gbc);
    	// GridBagLayout의 GridBagConstraints의 set하는 방법
    }
}
