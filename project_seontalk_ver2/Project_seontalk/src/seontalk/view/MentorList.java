package seontalk.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import seontalk.util.FilePath;
import seontalk.util.Theme;

public class MentorList extends JPanel{
	//전체화면 객체주입되는 변수
		MainPage 		page 			= null;
		Mentor			mentor			= null;

		//폰트 설정
		Font 			myfont 			= new Font("HY견고딕",Font.PLAIN,26);
		//버튼panel을 GroupLayout하는 panel
		JPanel 			jp_bubble2 		= new JPanel();	
		//스크롤 페이지
		JScrollPane 	jsp_bubble 		= new JScrollPane(jp_bubble2
											//메뉴목록에 스크롤 적용
											,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED		
											//스크롤바 안보이게 설정(드래그,휠 이벤트 적용)
											,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// // 넣어주는 페이지
		JPanel 		jp_bubble 			= new JPanel();
		//버튼처럼 사용될 panel(코드 최하단 중복클래스)
		ButtonPanel jp_bt_java 			= null;
		ButtonPanel jp_bt_oracle 		= null;
		ButtonPanel jp_bt_javascript    = null;
		ButtonPanel jp_bt_html 			= null;
		ButtonPanel jp_bt_android 		= null;
		//선택한 언어게시판 이름
		String name						= null;
		//언어별 선택 게시판
		MentorListCategory	jp_mls		= null;
		
		Theme theme = new Theme();
		
		public MentorList(MainPage page) {
			this.page = page;
			jp_mls = new MentorListCategory(page, name);
			setLayout(null);
			initButton();
			initScroll();
			initGroup();
			initEvent();
			//화면에 붙일때 미세한 라인이 잡혀서 border제거
			jsp_bubble.setBorder(null);	
			jp_bubble.add(jsp_bubble);
			setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
			
		}
		public void initScroll() {
		    jsp_bubble.getVerticalScrollBar().setUnitIncrement(20);
		}
		public void initButton() {
			jp_bt_java 			= new ButtonPanel("자바,JSP",20,10,360,80,30,30,page.memVO
					,new ImageIcon(FilePath.SrcPath+"java01.png"));
			jp_bt_oracle 		= new ButtonPanel("오라클 SQL",20,10,360,80,30,30,page.memVO
								,new ImageIcon(FilePath.SrcPath+"oracle01.png"));
			jp_bt_javascript    = new ButtonPanel("자바스크립트",20,10,360,80,30,30,page.memVO
								,new ImageIcon(FilePath.SrcPath+"javascript01.png"));
			jp_bt_html 			= new ButtonPanel("HTML",20,10,360,80,30,30,page.memVO
								,new ImageIcon(FilePath.SrcPath+"html01.png"));  
			jp_bt_android 		= new ButtonPanel("안드로이드",20,10,360,80,30,30,page.memVO
								,new ImageIcon(FilePath.SrcPath+"android01.png"));
			jp_bt_java.init(40, 30, 300, 50);
			jp_bt_oracle.init(40, 30, 300, 50);
			jp_bt_javascript.init(50, 30, 300, 50);
			jp_bt_html.init(40, 30, 300, 50);
			jp_bt_android.init(40, 30, 300, 50);
		}
		
		//페이지 전환 이벤트
		public void initGroup() {
			//메뉴목록 GroupLayout 처리
			GroupLayout pageLayout = new GroupLayout(jp_bubble2);	
			jp_bubble2.setLayout(pageLayout);
			pageLayout.setHorizontalGroup(pageLayout.createSequentialGroup()
				.addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jp_bt_java)
					.addComponent(jp_bt_oracle)
					.addComponent(jp_bt_javascript)
					.addComponent(jp_bt_html)
					.addComponent(jp_bt_android)
				)
			);
			pageLayout.setVerticalGroup(pageLayout.createSequentialGroup()
				.addComponent(jp_bt_java,100,100,100)
				.addComponent(jp_bt_oracle,100,100,100)
				.addComponent(jp_bt_javascript,100,100,100)
				.addComponent(jp_bt_html,100,100,100)
				.addComponent(jp_bt_android,100,100,100)
			);
			//상단제목,메뉴panel을 GroupLayout 처리
			GroupLayout pageLayout2 = new GroupLayout(jp_bubble);	
			jp_bubble.setLayout(pageLayout2);
			pageLayout2.setHorizontalGroup(pageLayout2.createSequentialGroup()
				.addGroup(pageLayout2.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(this)
					.addComponent(jsp_bubble)
				)
			);
			pageLayout2.setVerticalGroup(pageLayout2.createSequentialGroup()
				.addComponent(this,75,75,75)
				.addComponent(jsp_bubble)
			);
		}
		
		//화면 선택시 하단 화면 전환  > 지금은 전체화면 전환
		public void initGroup(JScrollPane jsp_bubble) {
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
		}
		
		//언어별 게시판 선택 이벤트 > 게시판 열기 : 게시판 이름 받이오기
		public void initEvent() {
			jp_bt_java.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getX()>=20&&e.getX()<=380&&e.getY()>=10&&e.getY()<=90) {
						jp_mls = new MentorListCategory(page, "자바,JSP");
						page.remove(page.jp_page);
						page.jp_page = jp_mls.jp_bubble;
						page.add(page.jp_page);
						page.revalidate();
						page.repaint();
					}
					super.mouseClicked(e);
				}
			});
			jp_bt_oracle.addMouseListener(new MouseAdapter() { 
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getX()>=20&&e.getX()<=380&&e.getY()>=10&&e.getY()<=90) {
						jp_mls = new MentorListCategory(page, "오라클 SQL");
						page.remove(page.jp_page);
						page.jp_page = jp_mls.jp_bubble;
						page.add(page.jp_page);
						page.revalidate();
						page.repaint();
					}
					super.mouseClicked(e);
				}
			});
			jp_bt_javascript.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getX()>=20&&e.getX()<=380&&e.getY()>=10&&e.getY()<=90) {
						jp_mls = new MentorListCategory(page, "자바스크립트");
						page.remove(page.jp_page);
						page.jp_page = jp_mls.jp_bubble;
						page.add(page.jp_page);
						page.revalidate();
						page.repaint();
					}
					super.mouseClicked(e);
				}
			});
			jp_bt_html.addMouseListener(new MouseAdapter() { 	
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getX()>=20&&e.getX()<=380&&e.getY()>=10&&e.getY()<=90) {
						jp_mls = new MentorListCategory(page, "HTML");
						page.remove(page.jp_page);
						page.jp_page = jp_mls.jp_bubble;
						page.add(page.jp_page);
						page.revalidate();
						page.repaint();
					}
					super.mouseClicked(e);
				}
			});
			jp_bt_android.addMouseListener(new MouseAdapter() { 	
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getX()>=20&&e.getX()<=380&&e.getY()>=10&&e.getY()<=90) {
						jp_mls = new MentorListCategory(page, "안드로이드");
						page.remove(page.jp_page);
						page.jp_page = jp_mls.jp_bubble;
						page.add(page.jp_page);
						page.revalidate();
						page.repaint();
					}
					super.mouseClicked(e);
				}
			});
		}
}
