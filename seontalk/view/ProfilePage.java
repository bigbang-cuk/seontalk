package seontalk.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.chat.Protocol;
import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;

public class ProfilePage extends JFrame {
	MainPage page = null;
	MemberVO user = null;
	Theme theme = new Theme();
	ProfileImg pImg = null;
	JPanel jp_bubble = new JPanel();
	JPanel jp_blank1 = new JPanel();
	JPanel jp_blank2 = new JPanel();
	JPanel jp_blank3 = new JPanel();
	JPanel jp_blank4 = new JPanel();
	JPanel jp_blank5 = new JPanel();
	JLabel jlb_nick = null;
	JLabel jlb_profile = null;
	JLabel jlb_interest = null;
	JButton jbtn_follow = new JButton("팔로우");
	JButton jbtn_unfollow = new JButton("언팔로우");
	JButton jbtn_chat = new JButton("1:1 채팅");
	JButton jbtn_blank = new JButton();
	JButton jbtn_blank2 = new JButton();
	public ProfilePage(MainPage page, MemberVO user) {
		this.page = page;
		this.user = user;
		init();
	}
	public void init() {
		setSize(380,600);
		pImg = new ProfileImg(user.getProfile_img(), 0, 0, 200, 200);
		initButton();
		initLabel();
		if(page.memVO.getId().equals(user.getId())) {
			initGroup(jbtn_blank,jbtn_blank2);
		}
		else {
			if("mentor".equals(user.getMentoring())) {
				initGroup(buttonSelect(),jbtn_chat);
			}
			else {
				initGroup(jbtn_blank,jbtn_chat);
			}
		}
		initLocation();
		initEvent();
		add(jp_bubble);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				page.ProfileList.remove(ProfilePage.this);
				super.windowClosing(e);
			}
		});
		setResizable(false);
		setVisible(true);
	}
	public JButton buttonSelect() {
		JButton jbtn = null;
		List<Object> following = page.ctrl.ConnectSelect("select", "following", page.memVO);
		for(int i=0;i<following.size();i++) {
			MemberVO followingVO = (MemberVO)following.get(i);
			if(followingVO.getNick().equals(user.getNick())) {
				jbtn = jbtn_unfollow;
				break;
			}
		}
		if(jbtn==null) {
			jbtn = jbtn_follow;
		}
		return jbtn;
	}
	
	public void initGroup(JButton jbtn,JButton jbtn2) {
		jp_blank1.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jp_blank2.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jp_blank3.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jp_blank4.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jp_blank5.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jp_blank1,380,380,380)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jp_blank2,90,90,90)
					.addComponent(pImg,200,200,200)
					.addComponent(jp_blank3,90,90,90)
				)
				.addComponent(jlb_nick,380,380,380)
				.addComponent(jlb_profile,380,380,380)
				.addComponent(jlb_interest,380,380,380)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jp_blank4,70,70,70)
					.addComponent(jbtn,120,120,120)
					.addComponent(jbtn2,120,120,120)
					.addComponent(jp_blank5,70,70,70)
				)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(jp_blank1,120,120,120)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(jp_blank2,200,200,200)
				.addComponent(pImg,200,200,200)
				.addComponent(jp_blank3,200,200,200)
			)
			.addComponent(jlb_nick,60,60,60)
			.addComponent(jlb_profile,60,60,60)
			.addComponent(jlb_interest,40,40,40)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(jp_blank4,100,100,100)
				.addComponent(jbtn,100,100,100)
				.addComponent(jbtn2,100,100,100)
				.addComponent(jp_blank5,100,100,100)
			)
		);
	}
	
	public void initButton() {
		Font font = new Font(page.memVO.getFont(),Font.PLAIN,page.memVO.getFont_size());
		jbtn_follow.setFont(font);
		jbtn_unfollow.setFont(font);
		jbtn_chat.setFont(font);
		jbtn_follow.setIcon(new ImageIcon(FilePath.SrcPath+"userplus01.png"));
		jbtn_unfollow.setIcon(new ImageIcon(FilePath.SrcPath+"userblock01.png"));
		jbtn_chat.setIcon(new ImageIcon(FilePath.SrcPath+"chat01.png"));
		jbtn_follow.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_unfollow.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_chat.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_blank.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_blank2.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_follow.setBorder(null);
		jbtn_unfollow.setBorder(null);
		jbtn_chat.setBorder(null);
		jbtn_blank.setBorder(null);
		jbtn_blank2.setBorder(null);
		jbtn_follow.setRolloverEnabled(false);
		jbtn_unfollow.setRolloverEnabled(false);
		jbtn_chat.setRolloverEnabled(false);
		jbtn_blank.setRolloverEnabled(false);
		jbtn_blank2.setRolloverEnabled(false);
		jbtn_follow.setFocusable(false);
		jbtn_unfollow.setFocusable(false);
		jbtn_chat.setFocusable(false);
		jbtn_blank.setFocusable(false);
		jbtn_blank2.setFocusable(false);
		jbtn_blank.setEnabled(false);
		jbtn_blank2.setEnabled(false);
	}
	
	public void initLabel() {
		Font nickFont = new Font(page.memVO.getFont(),Font.BOLD,page.memVO.getFont_size()+6);
		Font font = new Font(page.memVO.getFont(),Font.PLAIN,page.memVO.getFont_size()-6);
		jlb_nick = new JLabel(user.getNick());
		jlb_profile = new JLabel(user.getProfile_msg());
		if("".equals(user.getInterest2())) {
			jlb_interest = new JLabel(user.getInterest1());
		}
		else {
			jlb_interest = new JLabel(user.getInterest1()+"/"+user.getInterest2());
		}
		jlb_nick.setFont(nickFont);
		jlb_profile.setFont(font);
		jlb_interest.setFont(font);
		jlb_nick.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_profile.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_profile.setVerticalAlignment(SwingConstants.CENTER);
		jlb_interest.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_interest.setVerticalAlignment(SwingConstants.CENTER);
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_profile.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_interest.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_nick.setOpaque(true);
		jlb_profile.setOpaque(true);
		jlb_interest.setOpaque(true);
		
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
	
	public void initEvent() {
		jbtn_follow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page.memVO.setFollow_nick(user.getNick());
				MemberVO rVO = null;
				rVO = (MemberVO)page.ctrl.Connect("insert", "following", page.memVO);
				if(rVO.getStatus()==1) {
					JOptionPane.showMessageDialog(ProfilePage.this, "팔로잉되었습니다");
					remove(jp_bubble);
					jp_bubble = new JPanel();
					initGroup(jbtn_unfollow,jbtn_chat);
					add(jp_bubble);
					revalidate();
					repaint();
				}
				else {
					JOptionPane.showMessageDialog(ProfilePage.this, "팔로잉 실패");
				}
			}
		});
		jbtn_unfollow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page.memVO.setFollow_nick(user.getNick());
				MemberVO rVO = null;
				rVO = (MemberVO)page.ctrl.Connect("delete", "unfollow", page.memVO);
				if(rVO.getStatus()==1) {
					JOptionPane.showMessageDialog(ProfilePage.this, "언팔로우되었습니다");
					remove(jp_bubble);
					jp_bubble = new JPanel();
					initGroup(jbtn_follow,jbtn_chat);
					add(jp_bubble);
					revalidate();
					repaint();
				}
				else {
					JOptionPane.showMessageDialog(ProfilePage.this, "언팔로우 실패");
				}
				
			}
		});
		jbtn_chat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isRoom = false;
				ChatRoom room = null;
				for(int i=0;i<page.ChatRoomList.size();i++) {
					ChatRoom room2 = page.ChatRoomList.get(i);
					String nick = room2.user.getNick();
					if(user.getNick().equals(nick)) {
						isRoom = true;
						room = room2;
					}
				}
				if(isRoom) {
					ChatVO pVO = new ChatVO();
					pVO.setTalklist_num(room.chat.getTalklist_num());
					pVO.setNick(page.memVO.getNick());
					ChatVO rVO = (ChatVO)page.ctrl.Connect("update", "reading", pVO);
					if(1==rVO.getStatus()) {
						room.setVisible(true);
						setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(null, "DB Connection Error");
						setVisible(false);
					}
				}
				else {
					try {
						page.oos.writeObject(Protocol.ROOM_CREATE
								+Protocol.seperator+page.memVO.getNick()
								+Protocol.seperator+user.getNick());
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
		});
	}
}
