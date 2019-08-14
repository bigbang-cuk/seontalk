package seontalk.view;

import java.awt.Font;
import java.awt.GridLayout;
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

public class Chat extends JPanel{
	MainPage 		page 			= null;	//전체화면 객체주입되는 변수
	String			name			= null;
	JLabel 			jlb 			= null; //상단 제목 라벨
	JPanel			jp_bubble		= null;
	JPanel			jp_grid			= null;
	JScrollPane 	jsp_bubble		= null;
	Theme theme = new Theme();
	public Chat(MainPage page,String name) {
		this.page = page;
		this.name = name;
		init();
	}
	public void init() {
		setLayout(null);
		initLabel();
		initGrid();
		initGroup();
		initScroll();
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		
	}
	public void initLabel() {
		jlb = new JLabel();
		if(Theme.BLACK.equals(page.memVO.getTheme())) {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"chat02_rev.png"));
		}
		else {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"chat02.png"));
		}
		jlb.setBounds(15, 15, 200, 50);
		jlb.setText(name);
		jlb.setFont(new Font(page.memVO.getFont(),Font.PLAIN,26));
		jlb.setForeground(theme.setFontColor(page.memVO.getTheme()));
		add(jlb);
	}
	public void initGrid() {
		List<ChatRow> chatRowList = new ArrayList<>();
		ChatRow chatRow = null;
		for(int i=0;i<page.ChatRoomList.size();i++) {
			ChatRoom room = page.ChatRoomList.get(i);
			MemberVO pVO = room.user;
			chatRow = new ChatRow(page,pVO,room);
			chatRowList.add(chatRow);
		}
		int size = 6;
		if(chatRowList.size()>6){
			size = chatRowList.size();
		}
		jp_grid = new JPanel();
		jp_grid.setLayout(new GridLayout(size,1));
		for(int i=0;i<chatRowList.size();i++) {
			jp_grid.add(chatRowList.get(i));
		}
		jp_grid.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jsp_bubble = new JScrollPane(jp_grid
						,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
						,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
	public void initScroll() {
	    jsp_bubble.getVerticalScrollBar().setUnitIncrement(20);
	}
}

