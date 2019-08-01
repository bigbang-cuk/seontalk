package seontalk.view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.PostVO;

public class MyBest extends JPanel {
	JLabel 			jlb 			= null; 
	JPanel			jp_grid 		= null;
	JPanel 			jp_bubble		= null;
	JScrollPane		jsp_bubble 		= null;
	
	MainPage 		page 			= null;	
	Theme theme = new Theme();
	String name = null;
	
	List<PostRow> postRowList = new ArrayList<>();
	List<Object> postList = new ArrayList<>();
	
	public MyBest(MainPage page,String name) {
		this.page = page;
		this.name = name;
		getList();
		init();
	}
	public void getList() {
		PostVO pVO = new PostVO();
		pVO.setInterest(page.memVO.getInterest1());
		pVO.setKeyword(page.memVO.getInterest2());
		postList = page.ctrl.ConnectSelect("select", "post_best", pVO);
	}
	public void init() {
		setLayout(null);
		initLabel();
		initGrid();
		initGroup();
		initScroll();
		initEvent();
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	public void initLabel() {
		jlb = new JLabel();
		if(Theme.BLACK.equals(page.memVO.getTheme())) {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"preview01_rev.png"));
		}
		else {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"preview01.png"));
		}
		jlb.setBounds(15, 15, 200, 50);
		jlb.setText(name);
		jlb.setFont(new Font(page.memVO.getFont(),Font.PLAIN,26));
		jlb.setForeground(theme.setFontColor(page.memVO.getTheme()));
		add(jlb);
	}
	public void initGrid() {
		PostRow postRow = null;
		for(int i=0;i<postList.size();i++) {
			PostVO post = (PostVO)postList.get(i);
			postRow = new PostRow(page, post);
			postRowList.add(postRow);
		}
		int size = 10;
		if(postRowList.size()>10) {
			size = postRowList.size();
		}
		jp_grid = new JPanel();
		jp_grid.setLayout(new GridLayout(size,1));
		for(int i=0;i<postRowList.size();i++) {
			jp_grid.add(postRowList.get(i));
		}
		jp_grid.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jsp_bubble = new JScrollPane(jp_grid
					,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
					,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}
	public void initScroll() {
	    jsp_bubble.getVerticalScrollBar().setUnitIncrement(20);
	}
	public void initGroup() {
		jp_bubble = new JPanel();
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this)
				.addComponent(jsp_bubble)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(this,75,75,75)
			.addComponent(jsp_bubble)
		);
		jsp_bubble.setBorder(null);
	}
	public void initEvent() {
		jlb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX()>=5&&e.getX()<=35&&e.getY()>=0&&e.getY()<=40) {
					super.mouseClicked(e);
					page.remove(page.jp_page);
					page.jp_page = page.jp_myPage.jp_bubble;
					page.add(page.jp_page);
					page.revalidate();
					page.repaint();
				}
			}
		});
	}
}
