package seontalk.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class MyFollow extends JFrame {
	JLabel 		jl_following 		= null;
	JLabel 		jl_following_num 	= null;
	JLabel 		jl_follower 		= null;
	JLabel 		jl_follower_num  	= null;
	JTextField  jtf_search 			= null;
	
	JPanel 		jp_bubble 			= null;
	JPanel 		jp_north 			= new JPanel();
	JPanel		jp_grid_following 	= null;
	JPanel		jp_grid_follower 	= null;
	JScrollPane jsp_bubble1			= null;
	JScrollPane jsp_bubble2			= null;

	MainPage page = null;
	Theme theme = new Theme();
	
	List<Object> followingList = new Vector<>();
	List<Object> followerList  = new Vector<>();
	String follow = null;

	public MyFollow(MainPage page) {
		this.page = page;
		getList();
		init();
	}
	
	public void getList() {
		followingList = page.ctrl.ConnectSelect("select", "following", page.memVO);
		followerList = page.ctrl.ConnectSelect("select", "follower", page.memVO);
	}
	
	public void init() {
		setSize(410, 620);
		initPanel();
		initTextField();
		initLabel();
		initGrid();
		initGroup(jsp_bubble1);
		follow = "following";
		initEvent();
		initLocation();
		initScroll();
		setResizable(false);
		setVisible(true);
	}
	
	public void initScroll() {
	    jsp_bubble1.getVerticalScrollBar().setUnitIncrement(20);
	    if("mentor".equals(page.memVO.getMentoring())) {
	    	jsp_bubble2.getVerticalScrollBar().setUnitIncrement(20);
	    }
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
		jp_north.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp_north.setPreferredSize(new Dimension(410, 100)); // 패널사이즈 설정하는 법
		jp_north.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	
	public void initTextField() {
		jtf_search 	= new JTextField("닉네임을 입력하세요");
		jtf_search.setPreferredSize(new Dimension(200,20));
	}
	
	public void initLabel() {
		Font font = new Font(page.memVO.getFont(),Font.BOLD,page.memVO.getFont_size());
		Font font2 = new Font(page.memVO.getFont(),Font.BOLD,page.memVO.getFont_size()-4);
		jl_following 		= new JLabel("팔로잉", JLabel.CENTER);  
		jl_follower 		= new JLabel("팔로워", JLabel.CENTER);  
		jl_following_num 	= new JLabel(String.valueOf(followingList.size()), JLabel.CENTER);   
		jl_follower_num  	= new JLabel(String.valueOf(followerList.size()), JLabel.CENTER);  
		jl_following.setFont(font);
		jl_follower.setFont(font);
		jl_following_num.setFont(font2);
		jl_follower_num.setFont(font2);
		jl_following.setPreferredSize(new Dimension(150, 20)); 
		jl_follower.setPreferredSize(new Dimension(150, 20)); 
		jl_following_num.setPreferredSize(new Dimension(150, 60)); 
		jl_follower_num.setPreferredSize(new Dimension(150, 60)); 
		jp_north.add(jl_following);
		if("mentor".equals(page.memVO.getMentoring())) {
			jp_north.add(jl_follower);
		}
		jp_north.add(jl_following_num);
		if("mentor".equals(page.memVO.getMentoring())) {
			jp_north.add(jl_follower_num);
		}
	}
	
	public void initGrid() {
		jp_grid_following = new JPanel();
		List<FollowRow> following = new ArrayList<>();
		FollowRow follow = null;
		for (int i = 0; i < followingList.size(); i++) {
			MemberVO followVO = (MemberVO)followingList.get(i);
			follow = new FollowRow(page,followVO);
			following.add(follow);
		}
		int size = 6;
		if(following.size()>6) {
			size = following.size();
		}
		jp_grid_following.setLayout(new GridLayout(size, 1));
		jp_grid_following.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		for (int i = 0; i < following.size(); i++) {
			jp_grid_following.add(following.get(i));
		}
		jsp_bubble1	= new JScrollPane(jp_grid_following   
				,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED 
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		
		if("mentor".equals(page.memVO.getMentoring())) {
			jp_grid_follower = new JPanel();
			List<FollowRow> follower = new ArrayList<>();
			follow = null;
			for (int i = 0; i < followerList.size(); i++) {
				MemberVO followVO = (MemberVO)followerList.get(i);
				follow = new FollowRow(page,followVO);
				follower.add(follow);
			}
			int size2 = 6;
			if(follower.size()>6) {
				size2 = follower.size();
			}
			jp_grid_follower.setLayout(new GridLayout(size, 1));
			jp_grid_follower.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
			for (int i = 0; i < follower.size(); i++) {
				jp_grid_follower.add(follower.get(i));
			}
			jsp_bubble2	= new JScrollPane(jp_grid_follower    
					,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED 
					,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		}
	}
	
	public void initGroup(JScrollPane jsp) {
		jp_bubble = new JPanel();
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jp_north)
				.addComponent(jtf_search)
				.addComponent(jsp)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(jp_north,80,80,80)
			.addComponent(jtf_search,40,40,40)
			.addComponent(jsp)
		);
		jsp.setBorder(null);
		jsp.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jp_bubble.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		add(jp_bubble);
	}
	
	public void initEvent() {
		jtf_search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				jtf_search.setText("");
			}
		});
		jtf_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if("닉네임을 입력하세요".equals(jtf_search.getText())) {
					jtf_search.setText("");
				}
				super.keyPressed(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(10==e.getKeyCode()) {
					page.memVO.setColumn("nick");
					page.memVO.setKeyword(jtf_search.getText());
					remove(jp_bubble);
					if("following".equals(follow)) {
						followingList = page.ctrl.ConnectSelect("select", "following_search", page.memVO);
						initGrid();
						initGroup(jsp_bubble1);
					}
					else {
						followerList = page.ctrl.ConnectSelect("select", "follower_search", page.memVO);
						initGrid();
						initGroup(jsp_bubble2);
					}
					add(jp_bubble);
					revalidate();
					repaint();
				}
				super.keyReleased(e);
			}
		});
		jl_following.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				remove(jp_bubble);
				jp_bubble = new JPanel();
				initGroup(jsp_bubble1);
				add(jp_bubble);
				revalidate();
				repaint();
				follow = "following";
				super.mouseClicked(e);
			}
		});
		jl_follower.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				remove(jp_bubble);
				jp_bubble = new JPanel();
				initGroup(jsp_bubble2);
				add(jp_bubble);
				revalidate();
				repaint();
				follow = "follower";
				super.mouseClicked(e);
			}
		});
	}

}
