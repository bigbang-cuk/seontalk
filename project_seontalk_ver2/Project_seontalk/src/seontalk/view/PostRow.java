package seontalk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.MemberVO;
import seontalk.vo.PostVO;

public class PostRow extends JPanel {
	JLabel		jlb_title	= null;
	JLabel		jlb_nick	= null;
	JLabel		jlb_view	= null;
	JLabel		jlb_date	= null;
	JLabel		jlb_time	= null;
	
	MainPage page;
	Theme theme = new Theme();
	PostVO 	post = null;
	MemberVO user = null;
	ProfilePage pp = null;
	public PostRow(MainPage page,PostVO post) {
		this.page = page;
		this.post = post;
		getProfile();
		init();
	}
	public void getProfile() {
		MemberVO pVO = new MemberVO();
		pVO.setNick(post.getNick());
		List<Object> list = page.ctrl.ConnectSelect("select", "check_nick", pVO);
		user = (MemberVO)list.get(0);
	}
	public void init() {
		initLabel();
		initGroup();
		initEvent();
	}
	public void initLabel() {
		Font font  = new Font(page.memVO.getFont(),Font.PLAIN,14);
		Font font2 = new Font(page.memVO.getFont(),Font.PLAIN,9);
		if(1==post.getActivation()) {
			jlb_title	= new JLabel("   해당 게시글은 삭제되었습니다");
			Font font3  = new Font(page.memVO.getFont(),Font.PLAIN,12);
			jlb_title.setFont(font3);
			jlb_title.setForeground(Color.GRAY);
		}
		else {
			jlb_title	= new JLabel("   "+post.getPost_title());
			jlb_title.setFont(font);
		}
		jlb_nick	= new JLabel(post.getNick());
		jlb_view 	= new JLabel(String.valueOf(post.getView_cnt())
							, new ImageIcon(FilePath.SrcPath+"view03.png")
							, SwingConstants.LEFT);
		jlb_date	= new JLabel(post.getPost_date());
		jlb_time	= new JLabel(post.getPost_time()
							, new ImageIcon(FilePath.SrcPath+"clock03.png")
							, SwingConstants.LEFT);
		jlb_nick.setFont(font2);
		jlb_view.setFont(font2);
		jlb_date.setFont(font2);
		jlb_time.setFont(font2);
		jlb_title.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_view.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_date.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_time.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_title.setOpaque(true);
		jlb_nick.setOpaque(true);
		jlb_view.setOpaque(true);
		jlb_date.setOpaque(true);
		jlb_time.setOpaque(true);
				
	}
	public void initGroup() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addComponent(jlb_title,240,240,240)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jlb_nick,60,60,60)
				.addComponent(jlb_view,60,60,60)
			)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jlb_date,105,105,105)
				.addComponent(jlb_time,105,105,105)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(jlb_title,40,40,40)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jlb_nick,20,20,20)
					.addComponent(jlb_view,20,20,20)
				)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jlb_date,20,20,20)
					.addComponent(jlb_time,20,20,20)
				)
			)
		);
	}
	public void initEvent() {
		jlb_title.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(1==post.getActivation()) {
					JOptionPane.showMessageDialog(page, "삭제된 게시글입니다");
				}
				else {
					post.setKeyword(page.memVO.getNick());
					page.ctrl.Connect("update", "post_view", post);
					new PostReadPage(page, post);
				}
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
	}
}
