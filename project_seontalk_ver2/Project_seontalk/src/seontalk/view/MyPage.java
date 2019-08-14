package seontalk.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import seontalk.util.FilePath;
import seontalk.util.Theme;

public class MyPage extends JPanel {	//우선 자기자신도 panel(상단 제목이 그려짐.)
	MainPage 		page 			= null;	//전체화면 객체주입되는 변수
	JPanel 			jp_bubble 		= new JPanel();	//GroupLayout을 이용하기 위한 panel(화면전환시 jp_page에는 얘를 대입함.)
	JLabel 			jlb 			= new JLabel(); //상단 제목 라벨
	JButton			jbtn_logout		= null;
	ButtonPanel jp_bt_myPage 	= null;
	ButtonPanel jp_bt_myPost 	= null;
	ButtonPanel jp_bt_myFollow  = null;
	ButtonPanel jp_bt_myBest 	= null;
	
	Theme theme = new Theme();
	String name = null;
	
	MyProfile mp = null;
	MyFollow  mf = null;
	
	public MyPage(MainPage page,String name) {
		this.page = page;
		this.name = name;
		init();
	}
	
	public void init() {
		setLayout(null);
		initLabel();
		initButton();
		initGroup();
		initEvent();
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	
	public void initLabel() {
		if(Theme.BLACK.equals(page.memVO.getTheme())) {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"person01_rev.png"));
		}
		else {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"person01.png"));
		}
		jlb.setBounds(15, 15, 200, 50);
		jlb.setText(name);
		jlb.setFont(new Font(page.memVO.getFont(),Font.PLAIN,26));
		jlb.setForeground(theme.setFontColor(page.memVO.getTheme()));
		add(jlb);
	}
	
	public void initButton() {
		jp_bt_myPage   = new ButtonPanel("내 정보", 20, 20, 160, 160, 30, 30, page.memVO,"mypage01.png");
		jp_bt_myPost   = new ButtonPanel("내 게시글", 20, 20, 160, 160, 30, 30, page.memVO,"mypost01.png");
		jp_bt_myFollow = new ButtonPanel("팔로우", 20, 20, 160, 160, 30, 30, page.memVO,"follow01.png");
		jp_bt_myBest   = new ButtonPanel("내 추천글", 20, 20, 160, 160, 30, 30, page.memVO,"mybest01.png");
		jp_bt_myPage.init(20, 130, 160, 40);
		jp_bt_myPost.init(20, 130, 160, 40);
		jp_bt_myFollow.init(20, 130, 160, 40);
		jp_bt_myBest.init(20, 130, 160, 40);
		
		jbtn_logout = new JButton(new ImageIcon(FilePath.SrcPath+"exit02.png"));
		jbtn_logout.setFocusable(false);
		jbtn_logout.setRolloverEnabled(false);
		jbtn_logout.setBorder(null);
		jbtn_logout.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_logout.setBounds(350, 18, 40, 40);
		add(jbtn_logout);
	}
	
	public void initGroup() {	//상단제목,버튼panel(4개)를 GroupLayout으로 처리
		GroupLayout pageLayout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(pageLayout);
		pageLayout.setHorizontalGroup(pageLayout.createSequentialGroup()
			.addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this)
				.addGroup(pageLayout.createSequentialGroup()
					.addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jp_bt_myPage)
						.addComponent(jp_bt_myFollow)
					)
					.addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jp_bt_myPost)
						.addComponent(jp_bt_myBest)
					)
				)
			)
		);
		
		pageLayout.setVerticalGroup(pageLayout.createSequentialGroup()
			.addComponent(this,75,75,75)
			.addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(pageLayout.createSequentialGroup()
					.addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jp_bt_myPage,200,200,200)
						.addComponent(jp_bt_myPost,200,200,200)
					)
					.addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(jp_bt_myFollow,200,200,200)
						.addComponent(jp_bt_myBest,200,200,200)
					)
				)
			)
		);
	}
	
	public void initEvent() {
		jp_bt_myPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//둥근사각형 내부에서의 클릭만 적용되도록 좌표범위를 if문으로 처리
				if(e.getX()>=20&&e.getX()<=180&&e.getY()>=20&&e.getY()<=180) {
					if(mp!=null) {
						mp.setVisible(false);
					}
					mp = new MyProfile(page);
				}
				super.mouseClicked(e);
			}
		});
		jp_bt_myPost.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX()>=20&&e.getX()<=180&&e.getY()>=20&&e.getY()<=180) {
					page.remove(page.jp_page);
					page.jp_myPost = new MyPost(page, "내 게시글");
					page.jp_page = page.jp_myPost.jp_bubble;
					page.add(page.jp_page);
					page.revalidate();
					page.repaint();
				}
				super.mouseClicked(e);
			}
		});
		jp_bt_myFollow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX()>=20&&e.getX()<=180&&e.getY()>=20&&e.getY()<=180) {
					if(mf!=null) {
						mf.setVisible(false);
					}
					mf= new MyFollow(page);
				}
				super.mouseClicked(e);
			}
		});
		jp_bt_myBest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX()>=20&&e.getX()<=180&&e.getY()>=20&&e.getY()<=180) {
					page.remove(page.jp_page);
					page.jp_myBest = new MyBest(page, "내 추천글");
					page.jp_page = page.jp_myBest.jp_bubble;
					page.add(page.jp_page);
					page.revalidate();
					page.repaint();
				}
				super.mouseClicked(e);
			}
		});
		jbtn_logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃"
													, JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					new Login();
					page.Logout();
					page.dispose();
				}
			}
		});
	}
}
