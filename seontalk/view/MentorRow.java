package seontalk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class MentorRow  extends JPanel{
	JLabel jlb_rank = null;
	JLabel jlb_nick = null;
	JLabel jlb_interest = null;
	JPanel jp_profile = null;
	JPanel jp_blank = new JPanel();
	MainPage page = null;
	MemberVO user = null;
	ProfilePage pp = null;
	Theme theme = new Theme();
	public MentorRow(MainPage page,MemberVO user) {
		this.page = page;
		this.user = user;
		init();
		
		}
	public void init() {
		jp_profile = new ProfileImg(user.getProfile_img(),0,0,80,80);
		initLabel();
		initGroup();
		initMouse();
	}
	public void initGroup() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(jlb_rank,80,80,80)
				.addComponent(jp_profile,80,80,80)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jlb_nick,240,240,240)
						.addComponent(jlb_interest,240,240,240)
						)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jlb_rank,80,80,80)
						.addComponent(jp_profile,80,80,80)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jlb_nick,40,40,40)
								.addComponent(jlb_interest,40,40,40)
								)
						)
				);
	}
	public void initLabel() {
		jlb_rank = new JLabel("");
		jlb_nick = new JLabel(user.getNick());
		jlb_interest = new JLabel(user.getInterest1());
		jlb_rank.setOpaque(true);
		jlb_nick.setOpaque(true);
		jlb_interest.setOpaque(true);
		jlb_rank.setBackground(new Color(255,224,200));
		jlb_nick.setBackground(new Color(255,224,200));
		jlb_interest.setBackground(new Color(255,224,200));
		jlb_rank.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_rank.setVerticalAlignment(SwingConstants.CENTER);
		jlb_nick.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_interest.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_interest.setVerticalAlignment(SwingConstants.CENTER);
		jp_blank.setBackground(new Color(255,224,200));
		jlb_nick.setFont(new Font(page.memVO.getFont(),Font.PLAIN,16));
		jlb_nick.setForeground(theme.setFontColor(page.memVO.getTheme()));
		jlb_interest.setFont(new Font(page.memVO.getFont(),Font.PLAIN,16));
		jlb_interest.setForeground(theme.setFontColor(page.memVO.getTheme()));
	}
	public void initMouse() {
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
		jlb_nick.addMouseListener(new MouseAdapter() {
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
		jlb_interest.addMouseListener(new MouseAdapter() {
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
	}
}
