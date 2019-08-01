package seontalk.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.util.Theme;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;

public class ChatRow extends JPanel{
	JLabel jlb_nick = null;
	JLabel jlb_lastChat = null;
	JPanel jp_profile = null;
	JLabel jlb_lastTime = null;
	MainPage page = null;
	MemberVO user = null;
	Theme theme = new Theme();
	ProfilePage pp = null;
	ChatRoom room = null;
	public ChatRow(MainPage page,MemberVO user,ChatRoom room) {
		this.page = page;
		this.user = user;
		this.room = room;
		//프로필 사진 붙이기
		jp_profile = new ProfileImg(user.getProfile_img(),0,0,80,80);
		initLabel();
		initGroup();
		initEvent();
	}
	public void initLabel() {
		//라벨이름 붙이기
		jlb_nick = new JLabel(user.getNick());
		//마지막 대화정보 얻기
		if(room.chatLog!=null) {
			ChatLogVO chatLog = (ChatLogVO)room.chatLog.get(room.chatLog.size()-1);
			jlb_lastChat = new JLabel(chatLog.getContent());
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat format3 = new SimpleDateFormat("HH:mm");
			Date temp = new Date();
			String date = format1.format(temp);
			int compare_date = date.compareTo(chatLog.getLog_date());
			if(compare_date!=0) {
				jlb_lastTime = new JLabel(chatLog.getLog_date());
			}
			else {
				String time = format2.format(temp);
				long minute = 0;
				try {
					Date time1 = format2.parse(time);
					Date time2 = format2.parse(chatLog.getLog_time());
					long diff = time1.getTime() -  time2.getTime();
					minute = diff / 60000;
					if(minute<5) {
						jlb_lastTime = new JLabel((int)minute+"분전");
					}
					else {
						Date log_time1 = format3.parse(chatLog.getLog_time());
						String log_time2 = format3.format(log_time1);
						jlb_lastTime = new JLabel(log_time2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else {
			jlb_lastChat = new JLabel("마지막 대화");
			jlb_lastTime = new JLabel("타임");
		}
		//라벨 투명도(배경색 보이기)
		jlb_nick.setOpaque(true);
		jlb_lastChat.setOpaque(true);
		jlb_lastTime.setOpaque(true);
		//라벨 배경색 설정
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_lastChat.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_lastTime.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		//라벨 텍스트 위치설정
		jlb_nick.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_lastChat.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_lastChat.setVerticalAlignment(SwingConstants.CENTER);
		jlb_lastTime.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_lastTime.setVerticalAlignment(SwingConstants.CENTER);
		//닉네임,분야 폰트설정
		jlb_nick.setFont(new Font(page.memVO.getFont(),Font.PLAIN,16));
		jlb_nick.setForeground(theme.setFontColor(page.memVO.getTheme()));
		jlb_lastChat.setFont(new Font(page.memVO.getFont(),Font.PLAIN,12));
		jlb_lastChat.setForeground(theme.setFontColor(page.memVO.getTheme()));
		jlb_lastTime.setFont(new Font(page.memVO.getFont(),Font.PLAIN,10));
		jlb_lastTime.setForeground(theme.setFontColor(page.memVO.getTheme()));
	}
	public void initGroup() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(jp_profile,80,80,80)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jlb_nick,240,240,240)
				.addComponent(jlb_lastChat,240,240,240)
			)
			.addComponent(jlb_lastTime,80,80,80)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jp_profile,80,80,80)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jlb_nick,40,40,40)
					.addComponent(jlb_lastChat,40,40,40)
				)
				.addComponent(jlb_lastTime,80,80,80)
			)
		);
	}
	public void initEvent() {
		jp_profile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i=0;i<page.ProfileList.size();i++) {
					ProfilePage pp = page.ProfileList.get(i);
					if(pp.user.getNick().equals(user.getNick())) {
						pp.setVisible(false);
						page.ProfileList.remove(i);
					}
				}
				pp = new ProfilePage(page,user);
				page.ProfileList.add(pp);
				super.mouseClicked(e);
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX()>=80) {
					if(e.getClickCount()==2) {
						ChatVO pVO = new ChatVO();
						pVO.setTalklist_num(room.chat.getTalklist_num());
						pVO.setNick(page.memVO.getNick());
						ChatVO rVO = (ChatVO)page.ctrl.Connect("update", "reading", pVO);
						if(1==rVO.getStatus()) {
							room.initLocation();
							room.setVisible(true);
							room.jsp_chat.getVerticalScrollBar().setValue(room.jsp_chat.getVerticalScrollBar().getMaximum());
						}
						else {
							JOptionPane.showMessageDialog(null, "DB Connection Error");
						}
					}
				}
				super.mouseClicked(e);
			}
		});
	}
}
