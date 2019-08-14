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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import seontalk.util.FilePath;
import seontalk.util.Theme;

public class Post extends JPanel{
	MainPage 		page 			 = null;	
	JLabel 			jlb 			 = null; 
	JPanel 			jp_bubble2 		 = null;	
	JScrollPane 	jsp_bubble 		 = null;	
	JPanel 			jp_bubble 		 = null;
	JPanel			jp_grid			 = null;
	JButton 		jbtn_create		 = null;
	ButtonPanel     jp_bt_java 		 = null;
	ButtonPanel     jp_bt_oracle 	 = null;
	ButtonPanel     jp_bt_javascript = null;
	ButtonPanel     jp_bt_html 		 = null;
	ButtonPanel     jp_bt_android    = null;
	Theme theme = new Theme();
	String name = null;
	public Post(MainPage page,String name) {
		this.page = page;
		this.name = name;
		init();
	}
	public void init() {
		setLayout(null);
		initLabel();
		initButton();
		initGroup();
		initScroll();
		initEvent();
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	public void initLabel() {
		jlb = new JLabel();
		if(Theme.BLACK.equals(page.memVO.getTheme())) {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"list02_rev.png"));
		}
		else {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"list02.png"));
		}
		jlb.setBounds(15, 15, 200, 50);
		jlb.setText(name);
		jlb.setFont(new Font(page.memVO.getFont(),Font.PLAIN,26));
		jlb.setForeground(theme.setFontColor(page.memVO.getTheme()));
		add(jlb);
	}
	public void initScroll() {
		UIDefaults defaults = UIManager.getDefaults();
	    defaults.put("ScrollBar.width", new Integer("8"));
	    jsp_bubble.getVerticalScrollBar().setUnitIncrement(20);
	}
	public void initButton() {
		jbtn_create			= new JButton(new ImageIcon(FilePath.SrcPath+"postadd02.png"));
		jbtn_create.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_create.setBorder(null);
		jbtn_create.setFocusable(false);
		jbtn_create.setRolloverEnabled(false);
		jbtn_create.setBounds(330,20,50,50);
		add(jbtn_create);
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
	public void initGroup() {
		jp_bubble2 = new JPanel();
		GroupLayout pageLayout = new GroupLayout(jp_bubble2);	//메뉴목록 GroupLayout 처리
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
		jsp_bubble = new JScrollPane(jp_bubble2
				,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED	
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jp_bubble = new JPanel();
		GroupLayout pageLayout2 = new GroupLayout(jp_bubble);	//상단제목,메뉴panel을 GroupLayout 처리
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
		jsp_bubble.setBorder(null);
	}
	public void initEvent() {
		jbtn_create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PostWritePage(page);
			}
		});
		jp_bt_java.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getX()>=20&&e.getX()<=380&&e.getY()>=10&&e.getY()<=90) {
					PostCategory jp_list = new PostCategory(page, "자바/JSP");
					page.remove(page.jp_page);
					page.jp_page = jp_list.jp_bubble;
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
					PostCategory jp_list = new PostCategory(page, "오라클 SQL");
					page.remove(page.jp_page);
					page.jp_page = jp_list.jp_bubble;
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
					PostCategory jp_list = new PostCategory(page, "자바스크립트");
					page.remove(page.jp_page);
					page.jp_page = jp_list.jp_bubble;
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
					PostCategory jp_list = new PostCategory(page, "HTML");
					page.remove(page.jp_page);
					page.jp_page = jp_list.jp_bubble;
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
					PostCategory jp_list = new PostCategory(page, "안드로이드");
					page.remove(page.jp_page);
					page.jp_page = jp_list.jp_bubble;
					page.add(page.jp_page);
					page.revalidate();
					page.repaint();
				}
				super.mouseClicked(e);
			}
		});
	}
}
