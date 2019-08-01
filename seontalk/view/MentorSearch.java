package seontalk.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import seontalk.util.Theme;
import seontalk.vo.MemberVO;

public class MentorSearch extends JPanel{
	JPanel 		jp_bubble 	= null;
	JPanel 		jp_grid 	= null;
	JScrollPane jsp_bubble 	= null;
	JButton 	jbtn_search = null; 
	JTextField  jtf_search  = null;
	JComboBox 	jcb_search 	= null;
	String[] 	category 	= {"ID","Nick"};
	
	MainPage page = null;	
	Theme theme = new Theme();
	
	List<ProfileRow> searchPanel = null;
	List<Object> searchList = new ArrayList<>();
	
	public MentorSearch(MainPage page,String name) {
		this.page = page;
		init();
	}
	public void init() {
		setLayout(null);
		initButton();
		initCombo();
		initTextField();
		initGrid();
		initGroup();
		initEvent();
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		
	}
	public void initGrid() {
		jp_grid = new JPanel();
		ProfileRow profile = null;
		searchPanel = new ArrayList<>();
		for(int i=0;i<searchList.size();i++) {
			MemberVO user = (MemberVO)searchList.get(i);
			profile = new ProfileRow(page, user);
			searchPanel.add(profile);
		}
		int size = 5;
		if(searchPanel.size()>5) {
			size = searchPanel.size();
		}
		jp_grid.setLayout(new GridLayout(size,1));
		for(int i=0;i<searchPanel.size();i++) {
			jp_grid.add(searchPanel.get(i));
		}
		jp_grid.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jsp_bubble 	= new JScrollPane(jp_grid
				,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		initScroll();
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
			.addComponent(this,30,30,30)
			.addComponent(jsp_bubble)
		);
		jsp_bubble.setBorder(null);
	}
	public void initScroll() {
	    jsp_bubble.getVerticalScrollBar().setUnitIncrement(20);
	}
	public void initCombo() {
		jcb_search = new JComboBox(category);
		jcb_search.setBounds(35, 5, 65, 20);
		add(jcb_search);
	}
	public void initTextField() {
		jtf_search = new JTextField();
		jtf_search.setBounds(110, 5, 180, 20);
		add(jtf_search);
	}
	public void initButton() {
		jbtn_search = new JButton("검색");
		jbtn_search.setBounds(300, 5, 75, 20);
		jbtn_search.setFocusable(false);
		jbtn_search.setRolloverEnabled(false);
		add(jbtn_search);
	}
	public void initEvent() {
		jbtn_search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MemberVO pVO = new MemberVO();
				pVO.setColumn(jcb_search.getSelectedItem().toString());
				pVO.setKeyword(jtf_search.getText());
				searchList = page.ctrl.ConnectSelect("select", "search", pVO);
				initGrid();
				initGroup();
				page.remove(page.jp_page);
				page.jp_mentor.initGroup(jp_bubble);
				page.jp_page = page.jp_mentor.jp_bubble;
				page.add(page.jp_page);
				page.revalidate();
				page.repaint();
			}
		});
		jtf_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(10==e.getKeyCode()) {
					MemberVO pVO = new MemberVO();
					pVO.setColumn(jcb_search.getSelectedItem().toString());
					pVO.setKeyword(jtf_search.getText());
					searchList = page.ctrl.ConnectSelect("select", "search", pVO);
					initGrid();
					initGroup();
					page.remove(page.jp_page);
					page.jp_mentor.initGroup(jp_bubble);
					page.jp_page = page.jp_mentor.jp_bubble;
					page.add(page.jp_page);
					page.revalidate();
					page.repaint();
				}
				super.keyReleased(e);
			}
		});
		
	}
}
