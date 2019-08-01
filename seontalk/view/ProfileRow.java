package seontalk.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class ProfileRow extends JPanel{
	JLabel jlb_nick = null;
	JLabel jlb_interest = null;
	JPanel jp_profile = null;
	MainPage page = null;
	MemberVO user = null;
	Theme theme = new Theme();
	public ProfileRow(MainPage page, MemberVO user) {
		this.page = page;
		this.user = user;
		init();
	}
	public void init() {
		//프로필 사진 붙이기
		jp_profile = new ProfileImg(user.getProfile_img(),0,0,80,80);
		initLabel();
		initGroup();
		initEvent();
	}
	public void initLabel() {
		//라벨이름 붙이기
		jlb_nick = new JLabel(user.getNick());
		if("".equals(user.getInterest2())) {
			jlb_interest = new JLabel(user.getInterest1());
		}
		else {
			jlb_interest = new JLabel(user.getInterest1()+"/"+user.getInterest2());
		}
		//라벨 투명도(배경색 보이기)
		jlb_nick.setOpaque(true);
		jlb_interest.setOpaque(true);
		//라벨 배경색 설정
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_interest.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		//라벨 텍스트 위치설정
		jlb_nick.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_interest.setHorizontalAlignment(SwingConstants.LEFT);
		jlb_interest.setVerticalAlignment(SwingConstants.CENTER);
		//닉네임,분야 폰트설정
		jlb_nick.setFont(new Font(page.memVO.getFont(),Font.PLAIN,16));
		jlb_nick.setForeground(theme.setFontColor(page.memVO.getTheme()));
		jlb_interest.setFont(new Font(page.memVO.getFont(),Font.PLAIN,12));
		jlb_interest.setForeground(theme.setFontColor(page.memVO.getTheme()));
	}
	public void initGroup() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(jp_profile,80,80,80)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jlb_nick,320,320,320)
				.addComponent(jlb_interest,320,320,320)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jp_profile,80,80,80)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jlb_nick,40,40,40)
					.addComponent(jlb_interest,40,40,40)
				)
			)
		);
	}
	public void initEvent() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new ProfilePage(page,user);
				super.mouseClicked(e);
			}
		});
	}
}
