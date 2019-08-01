package seontalk.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import seontalk.util.FilePath;
import seontalk.util.Theme;

public class Mentor extends JPanel{
	MainPage 		page 			= null;	//전체화면 객체주입되는 변수
	JLabel 			jlb 			= new JLabel(); //상단 제목 라벨
	JPanel			jp_bubble		= new JPanel();
	JButton 		jbtn_rank 		= new JButton();
	JButton 		jbtn_list 		= new JButton();
	JButton 		jbtn_search 	= new JButton();
	Theme theme = new Theme();
	String name = null;
	public Mentor(MainPage page,String name) {
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
		add(jlb);
		add(jbtn_rank);
		add(jbtn_list);
		add(jbtn_search);
	}
	public void initLabel() {
		if(Theme.BLACK.equals(page.memVO.getTheme())) {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"people01_rev.png"));
		}
		else {
			jlb.setIcon(new ImageIcon(FilePath.SrcPath+"people01.png"));
		}
		jlb.setBounds(15, 15, 200, 50);
		jlb.setText(name);
		jlb.setFont(new Font(page.memVO.getFont(),Font.PLAIN,26));
		jlb.setForeground(theme.setFontColor(page.memVO.getTheme()));
	}
	public void initGroup() {
		jp_bubble = new JPanel();
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this)
				.addComponent(page.jp_rank.jsp_bubble)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(this,75,75,75)
			.addComponent(page.jp_rank.jsp_bubble)
		);
	}
	public void initGroup(JScrollPane jsp) {
		jp_bubble = new JPanel();
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this)
						.addComponent(jsp)
						)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(this,75,75,75)
				.addComponent(jsp)
				);
	}
	public void initGroup(JPanel jp) {
		jp_bubble = new JPanel();
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this)
						.addComponent(jp)
						)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(this,75,75,75)
				.addComponent(jp)
				);
	}
	public void initButton() {
		if(Theme.BLACK.equals(page.memVO.getTheme())) {
			jbtn_rank.setIcon(new ImageIcon(FilePath.SrcPath+"medal01_rev.png"));
			jbtn_list.setIcon(new ImageIcon(FilePath.SrcPath+"list01_rev.png"));
			jbtn_search.setIcon(new ImageIcon(FilePath.SrcPath+"search02_rev.png"));
		}
		else {
			jbtn_rank.setIcon(new ImageIcon(FilePath.SrcPath+"medal01.png"));
			jbtn_list.setIcon(new ImageIcon(FilePath.SrcPath+"list01.png"));
			jbtn_search.setIcon(new ImageIcon(FilePath.SrcPath+"search02.png"));
		}
		jbtn_rank.setBounds(230, 20, 50, 40);
		jbtn_rank.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_rank.setRolloverEnabled(false);
		jbtn_rank.setBorder(null);
		jbtn_list.setBounds(280, 20, 50, 40);
		jbtn_list.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_list.setRolloverEnabled(false);
		jbtn_list.setBorder(null);
		jbtn_search.setBounds(330, 20, 50, 40);
		jbtn_search.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_search.setRolloverEnabled(false);
		jbtn_search.setBorder(null);
		jbtn_rank.setFocusable(false);
		jbtn_list.setFocusable(false);
		jbtn_search.setFocusable(false);
	}
	
	public void initEvent() {
		jbtn_rank.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page.remove(page.jp_page);
				initGroup(page.jp_rank.jsp_bubble);
				page.jp_page = jp_bubble;
				page.add(page.jp_page);
				page.revalidate();
				page.repaint();
			}
		});
		jbtn_list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page.remove(page.jp_page);
				initGroup(page.jp_list.jsp_bubble);
				page.jp_page = jp_bubble;
				page.add(page.jp_page);
				page.revalidate();
				page.repaint();
			}
		});
		jbtn_search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page.remove(page.jp_page);
				initGroup(page.jp_search.jp_bubble);
				page.jp_page = jp_bubble;
				page.add(page.jp_page);
				page.revalidate();
				page.repaint();
			}
		});
	}
}
