package seontalk.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class FollowRow extends JPanel {
	JLabel jlb_id = null;
	JLabel jlb_nick = null;
	JPanel jp_grid = new JPanel();
	JPanel jp_profile = null;
	JPanel jp_blank = new JPanel();
	MainPage page = null;
	Theme theme = new Theme();
	MemberVO follow = null;
	ProfilePage pp  = null;

	public FollowRow(MainPage page, MemberVO follow) {
		this.page = page;
		this.follow = follow;
		init();
	}
	public void init() {
		initLabel();
		initGroup();
		initEvent();
	}
	public void initLabel() {
		jlb_nick = new JLabel(follow.getNick());
		jlb_id = new JLabel(follow.getId());
		// 라벨 투명도(배경색 보이기)
		jlb_nick.setOpaque(true);
		jlb_id.setOpaque(true);
		//jp_blank.setOpaque(true);
		
		// 라벨 배경색 설정
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_id.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		
		// 라벨 텍스트 위치설정
		jlb_nick.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_id.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_id.setVerticalAlignment(SwingConstants.CENTER);
		// 닉네임,분야 폰트설정
		jlb_nick.setFont(new Font(page.memVO.getFont(), Font.PLAIN, 16));
		jlb_nick.setForeground(theme.setFontColor(page.memVO.getTheme()));
		jlb_id.setFont(new Font(page.memVO.getFont(), Font.PLAIN, 16));
		jlb_id.setForeground(theme.setFontColor(page.memVO.getTheme()));
		// 빈공간 배경색설정
		jp_blank.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	public void initGroup() {
		jp_profile = new ProfileImg(follow.getProfile_img(), 0,0, 80,80);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(jp_blank)
			.addComponent(jp_profile, 80, 80, 80)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jlb_nick, 290, 290, 290)
				.addComponent(jlb_id, 290, 290, 290)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jp_blank)
				.addComponent(jp_profile, 80, 80, 80)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jlb_nick, 40, 40, 40)
					.addComponent(jlb_id, 40, 40, 40)
				)
			)
		);
	}
	public void initEvent() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i=0;i<page.ProfileList.size();i++) {
					ProfilePage pp = page.ProfileList.get(i);
					if(pp.user.getNick().equals(follow.getNick())) {
						pp.setVisible(false);
						page.ProfileList.remove(i);
					}
				}
				pp = new ProfilePage(page,follow);
				page.ProfileList.add(pp);
				super.mouseClicked(e);
			}
		});
	}
}

