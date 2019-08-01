package seontalk.view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class MentorRank extends JPanel{
	MainPage page = null;
	JPanel jp_grid = new JPanel();
	JScrollPane 	jsp_bubble		= new JScrollPane(this
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	Theme theme = new Theme();
	
	List<MentorRankRow> rankList = new ArrayList<>();
	List<Object> mentorList = null;
	
	public MentorRank(MainPage page) {
		this.page = page;
		init();
	}
	public void init() {
		initGrid();
		initGroup();
		initScroll();
		jsp_bubble.setBorder(null);
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	public void initScroll() {
	    jsp_bubble.getVerticalScrollBar().setUnitIncrement(20);
	}
	public void initGrid() {
		MentorRankRow ranker = null;
		int rank = 1;
		mentorList = page.ctrl.ConnectSelect("select", "mentor", page.memVO);
		for(int i=0;i<mentorList.size();i++) {
			MemberVO user = (MemberVO)mentorList.get(i);
			ranker = new MentorRankRow(page,user,rank);
			rankList.add(ranker);
			rank++;
		}
		int size = 5;
		if(rankList.size()>5) {
			size = rankList.size();
		}
		jp_grid.setLayout(new GridLayout(size-1,1));
		for(int i=1;i<rankList.size();i++) {
			jp_grid.add(rankList.get(i));
		}
	}
	public void initGroup() {
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(rankList.get(0))
				.addComponent(jp_grid)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(rankList.get(0),220,220,220)
			.addComponent(jp_grid)
		);
	}
	
}
