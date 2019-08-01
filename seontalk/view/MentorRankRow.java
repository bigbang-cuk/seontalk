package seontalk.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class MentorRankRow extends JPanel{
	JLabel jlb_rank = null;
	JLabel jlb_nick = null;
	JLabel jlb_interest = null;
	JPanel jp_profile = null;
	JPanel jp_blank = new JPanel();
	MainPage page = null;
	MemberVO user = null;
	ProfilePage pp = null;
	int rank = 0;
	Theme theme = new Theme();
	public MentorRankRow(MainPage page,MemberVO user,int rank) {
		this.page = page;
		this.user = user;
		this.rank = rank;
		init();
	}
	public void init() {
		//빈공간 배경색설정
		jp_blank.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		initLabel();
		initGroup();
		initMouse();
	}
	public void initLabel() {
		//라벨이름 붙이기
		jlb_rank = new JLabel(String.valueOf(rank));
		jlb_nick = new JLabel(user.getNick());
		jlb_interest = new JLabel(user.getInterest1());
		//라벨 투명도(배경색 보이기)
		jlb_rank.setOpaque(true);
		jlb_nick.setOpaque(true);
		jlb_interest.setOpaque(true);
		//라벨 배경색 설정
		jlb_rank.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_interest.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		//라벨 텍스트 위치설정
		jlb_rank.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_rank.setVerticalAlignment(SwingConstants.CENTER);
		if(rank==1) {
			jlb_nick.setHorizontalAlignment(SwingConstants.CENTER);
			jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
			jlb_interest.setHorizontalAlignment(SwingConstants.CENTER);
			jlb_interest.setVerticalAlignment(SwingConstants.CENTER);
		}
		else {
			jlb_nick.setHorizontalAlignment(SwingConstants.LEFT);
			jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
			jlb_interest.setHorizontalAlignment(SwingConstants.LEFT);
			jlb_interest.setVerticalAlignment(SwingConstants.CENTER);
		}
		//닉네임,분야 폰트설정
		jlb_nick.setFont(new Font(page.memVO.getFont(),Font.PLAIN,16));
		jlb_nick.setForeground(theme.setFontColor(page.memVO.getTheme()));
		jlb_interest.setFont(new Font(page.memVO.getFont(),Font.PLAIN,16));
		jlb_interest.setForeground(theme.setFontColor(page.memVO.getTheme()));
	}
	public void initGroup() {
		if(rank==1) {
			jp_profile = new ProfileImg(user.getProfile_img(),0,0,160,160);
			if(Theme.BLACK.equals(page.memVO.getTheme())) {
				jlb_rank.setIcon(new ImageIcon(FilePath.SrcPath+"medal01_1_rev.png"));
			}
			else {
				jlb_rank.setIcon(new ImageIcon(FilePath.SrcPath+"medal01_1.png"));
			}
			jlb_rank.setFont(new Font(page.memVO.getFont(),Font.PLAIN,50));
			jlb_rank.setForeground(theme.setFontColor(page.memVO.getTheme()));
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(jlb_rank,120,120,120)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jp_profile,160,160,160)
					.addComponent(jlb_nick,160,160,160)
					.addComponent(jlb_interest,160,160,160)
				)
				.addComponent(jp_blank)
			);
			layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jlb_rank,220,220,220)
					.addGroup(layout.createSequentialGroup()
						.addComponent(jp_profile,160,160,160)
						.addComponent(jlb_nick,40,40,40)
						.addComponent(jlb_interest,20,20,20)
					)
					.addComponent(jp_blank)
				)
			);
		}
		else {
			jp_profile = new ProfileImg(user.getProfile_img(),0,0,80,80);
			if(rank<=3) {
				if(Theme.BLACK.equals(page.memVO.getTheme())) {
					jlb_rank.setIcon(new ImageIcon(FilePath.SrcPath+"medal01_"+rank+"_rev.png"));
				}
				else {
					jlb_rank.setIcon(new ImageIcon(FilePath.SrcPath+"medal01_"+rank+".png"));
				}
			}
			jlb_rank.setFont(new Font(page.memVO.getFont(),Font.PLAIN,20));
			jlb_rank.setForeground(theme.setFontColor(page.memVO.getTheme()));
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(jlb_rank,80,80,80)
				.addComponent(jp_profile,80,80,80)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jlb_nick,250,250,250)
					.addComponent(jlb_interest,250,250,250)
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
