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
import seontalk.vo.MemberVO;

public class MentorListCategory extends JPanel {
	//전체화면 객체주입되는 변수
	MainPage 		page 			= null;	
	//상단 제목 라벨
	JLabel 			jlb 			= new JLabel();
	String 			name 			= null;
	//랭킹게시판 랭킹 구성
	JPanel 			jp_grid 		= new JPanel();
	//화면 전환 패널
	JPanel			jp_bubble		= new JPanel();
	JScrollPane 	jsp_bubble 		= new JScrollPane(jp_grid
										//메뉴목록에 스크롤 적용
										,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED		
										//스크롤바 안보이게 설정(드래그,휠 이벤트 적용)
										,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	Theme theme = new Theme();
	List<MentorRow> mentorList = new ArrayList<>();
	List<Object> mentorSelect = null;
	
	public MentorListCategory(MainPage page,String name) {
		this.page = page;
		this.name = name;
		getList();
		init();
	}
	public void getList() {
		MemberVO pVO = new MemberVO();
		pVO.setKeyword(name);
		mentorSelect = page.ctrl.ConnectSelect("select", "mentor_sel", pVO);
	}
	public void init() {
		setLayout(null);
		initLabel();
		initGrid();
		initGroup();
		initEvent();
		initScroll();
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	public void initLabel() {
		Font font = new Font(page.memVO.getFont(),Font.PLAIN,26);
		jlb.setIcon(new ImageIcon(FilePath.SrcPath+"preview01.png"));
		jlb.setBounds(15, 15, 300, 50);
		jlb.setText(name);
		jlb.setFont(font);
		add(jlb);
	}
	
	public void initScroll() {
	    jsp_bubble.getVerticalScrollBar().setUnitIncrement(20);
	}
	
	public void initGrid() {
		MentorRow mentor = null;
		for(int i=0;i<mentorSelect.size();i++) {
			MemberVO user = (MemberVO)mentorSelect.get(i);
			mentor = new MentorRow(page,user);
			mentorList.add(mentor);
		}
		int size = 5;
		if(mentorList.size()>5) {
			size = mentorList.size();
		}
		jp_grid.setLayout(new GridLayout(size,1));
		for(int i=0;i<mentorList.size();i++) {
			jp_grid.add(mentorList.get(i));
		}
		jp_grid.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	
	public void initGroup() {
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
		//이전 페이지 이벤트 
		jlb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX()>=5&&e.getX()<=35&&e.getY()>=0&&e.getY()<=40) {
					super.mouseClicked(e);
					page.remove(page.jp_page);
					page.jp_page = page.jp_mentor.jp_bubble;
					page.add(page.jp_page);
					page.revalidate();
					page.repaint();
				}
			}
		});
	}
	
}
