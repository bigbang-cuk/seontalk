package seontalk.view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.AttachVO;
import seontalk.vo.PostVO;
import seontalk.vo.ReplyVO;

public class PostReadPage extends JFrame {
	JLabel		jlb_category	= null;
	JLabel 		jlb_title		= null;
	JLabel		jlb_nick		= null;
	JLabel		jlb_time		= null;
	JLabel		jlb_view		= null;
	JLabel		jlb_reco_cnt	= null;
	JLabel		jlb_deco_cnt	= null;
	JTextArea	jta_content 	= null;
	JPanel		jp_north		= null;
	
	JLabel		jlb_comment		= null;
	JLabel		jlb_reply_cnt	= null;
	JButton		jbtn_reply		= null;
	JButton		jbtn_up			= null;
	JButton		jbtn_down		= null;
	JButton 	jbtn_update		= null;
	JButton		jbtn_delete 	= null;
	JPanel 		jp_reply		= null;
	JPanel 		jp_grid			= null;
	
	JPanel		jp_bubble		= null;
	JScrollPane jsp_bubble		= null;
	
	JTable 			  jtb_file	    = null;
	DefaultTableModel dtm_file	    = null;
	JScrollPane		  jsp_file	    = null;
	JPanel			  jp_file	    = null;
	String[]		  cols  	    = null;
	String[][]		  data		    = null;
	JButton			  jbtn_all	    = null;
	JButton			  jbtn_none	    = null;
	JButton			  jbtn_download = null;

	MainPage page;
	Theme theme = new Theme();
	PostVO post = null;
	
	List<ReplyRow> replyRowList = new ArrayList<>();
	List<Object> replyList = new ArrayList<>();
	List<Object> attachList = new ArrayList<>();
	
	public PostReadPage(MainPage page,PostVO post) {
		this.page = page;
		this.post = post;
		getFiles();
		init();
	}
	public void getFiles() {
		AttachVO pVO = new AttachVO();
		pVO.setPost_num(post.getPost_num());
		attachList = page.ctrl.ConnectSelect("select", "all", pVO);
	}
	public void init() {
		setTitle(page.memVO.getNick()+"-게시글");
		setSize(500,700);
		initPanel();
		initLabel();
		initButton();
		initTextArea();
		initGrid();
		if(attachList.size()>0) {
			initTable();
			initGroup2();
		}
		else {
			initGroup();
		}
		initScroll();
		initEvent();
		initLocation();
		setResizable(false);
		setVisible(true);
	}
	public void initTable() {
		cols = new String[] {"순번","파일명","용량"};
		data = new String[0][];
		dtm_file = new DefaultTableModel(data, cols) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jtb_file = new JTable(dtm_file);
		jtb_file.setRowSelectionAllowed(true);
		jtb_file.setColumnSelectionAllowed(false);
		jtb_file.getTableHeader().setReorderingAllowed(false);
		jtb_file.getTableHeader().setResizingAllowed(false);
		jtb_file.getColumnModel().getColumn(0).setPreferredWidth(30);
		jtb_file.getColumnModel().getColumn(1).setPreferredWidth(300);
		jtb_file.getColumnModel().getColumn(2).setPreferredWidth(90);
		jsp_file = new JScrollPane(jtb_file
							,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
							,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp_file.setBounds(5, 5, 420, 80);
		jp_file = new JPanel();
		jp_file.setLayout(null);
		jp_file.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jp_file.add(jsp_file);
		for(int i=0;i<attachList.size();i++) {
			AttachVO attachVO = (AttachVO)attachList.get(i);
			String[] row = {String.valueOf(i+1),attachVO.getFiles(),attachVO.getBytes()};
			dtm_file.addRow(row);
		}
		Font font = new Font("HY견고딕", Font.PLAIN, 9);
		jbtn_all = new JButton("전체선택");
		jbtn_none = new JButton("전체해제");
		jbtn_download = new JButton("다운로드");
		jbtn_all.setFont(font);
		jbtn_none.setFont(font);
		jbtn_download.setFont(font);
		jbtn_all.setFocusable(false);
		jbtn_none.setFocusable(false);
		jbtn_download.setFocusable(false);
		jbtn_all.setRolloverEnabled(false);
		jbtn_none.setRolloverEnabled(false);
		jbtn_download.setRolloverEnabled(false);
		jbtn_all.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_none.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_download.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_all.setBounds(425, 5, 60, 25);
		jbtn_none.setBounds(425, 30, 60, 25);
		jbtn_download.setBounds(425, 55, 60, 25);
		Insets inset = new Insets(5, 0, 5, 0);
		jbtn_all.setMargin(inset);
		jbtn_none.setMargin(inset);
		jbtn_download.setMargin(inset);
		jp_file.add(jbtn_all);
		jp_file.add(jbtn_none);
		jp_file.add(jbtn_download);
	}
	public void initButton() {
		jbtn_reply	= new JButton(new ImageIcon(FilePath.SrcPath+"comment04.png"));
		jbtn_up		= new JButton(new ImageIcon(FilePath.SrcPath+"good03.png"));
		jbtn_down	= new JButton(new ImageIcon(FilePath.SrcPath+"bad03.png"));
		jbtn_update	= new JButton(new ImageIcon(FilePath.SrcPath+"update03.png"));
		jbtn_delete	= new JButton(new ImageIcon(FilePath.SrcPath+"delete02.png"));
		jbtn_reply.setRolloverEnabled(false);
		jbtn_up.setRolloverEnabled(false);
		jbtn_down.setRolloverEnabled(false);
		jbtn_update.setRolloverEnabled(false);
		jbtn_delete.setRolloverEnabled(false);
		jbtn_reply.setFocusable(false);
		jbtn_up.setFocusable(false);
		jbtn_down.setFocusable(false);
		jbtn_update.setFocusable(false);
		jbtn_delete.setFocusable(false);
		jbtn_reply.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_up.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_down.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_update.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_delete.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_reply.setBounds(380, 0, 35, 35);
		jbtn_up.setBounds(415, 0, 35, 35);
		jbtn_down.setBounds(450, 0, 35, 35);
		jbtn_update.setBounds(310, 0, 35, 35);
		jbtn_delete.setBounds(345, 0, 35, 35);
		jp_reply.add(jbtn_reply);
		jp_reply.add(jbtn_up);
		jp_reply.add(jbtn_down);
		if(post.getNick().equals(page.memVO.getNick())) {
			jp_reply.add(jbtn_update);
			jp_reply.add(jbtn_delete);
		}
	}
	public void initPanel() {
		jp_north = new JPanel();
		jp_north.setLayout(null);
		jp_north.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jp_reply = new JPanel();
		jp_reply.setLayout(null);
		jp_reply.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	public void initScroll() {
		jsp_bubble = new JScrollPane(jp_bubble
				,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    jsp_bubble.getVerticalScrollBar().setUnitIncrement(20);
	    add(jsp_bubble);
	}
	public void initLocation() {
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension mainPageSize = page.getSize();
		Point point = page.getLocation();
		if((monitor.width-(point.x+mainPageSize.width))<getSize().width) {
			if(monitor.height-(point.y+getSize().height)<0) {
				setLocation(point.x-(getSize().width+5), monitor.height-getSize().height+10);
			}
			else {
				setLocation(point.x-(getSize().width+5), point.y);
			}
		}
		else {
			if(monitor.height-(point.y+getSize().height)<0) {
				setLocation(point.x+mainPageSize.width+5, monitor.height-getSize().height+10);
			}
			else {
				setLocation(point.x+mainPageSize.width+5, point.y);
			}
		}
	}
	public void initLabel() {
		Font font_category  = new Font(page.memVO.getFont(),Font.PLAIN,24);
		Font font_title  = new Font(page.memVO.getFont(),Font.PLAIN,16);
		Font font_etc = new Font(page.memVO.getFont(),Font.PLAIN,10);
		jlb_category = new JLabel(post.getInterest());
		jlb_title	 = new JLabel(post.getPost_title());
		jlb_nick	 = new JLabel(post.getNick());
		jlb_time	 = new JLabel(post.getPost_date()+" "+post.getPost_time()
								, new ImageIcon(FilePath.SrcPath+"clock02.png")
								, SwingConstants.LEFT);
		jlb_view	 = new JLabel(String.valueOf(post.getView_cnt())
								, new ImageIcon(FilePath.SrcPath+"view02.png")
								, SwingConstants.LEFT);
		jlb_reco_cnt = new JLabel(String.valueOf(post.getRecommend_cnt())
								, new ImageIcon(FilePath.SrcPath+"good03.png")
								, SwingConstants.LEFT);
		jlb_deco_cnt = new JLabel(String.valueOf(post.getDecommend_cnt())
								, new ImageIcon(FilePath.SrcPath+"bad03.png")
								, SwingConstants.LEFT);
		jlb_category.setFont(font_category);
		jlb_title.setFont(font_title);
		jlb_nick.setFont(font_etc);
		jlb_view.setFont(font_etc);
		jlb_time.setFont(font_etc);
		jlb_reco_cnt.setFont(font_etc);
		jlb_deco_cnt.setFont(font_etc);
		jlb_category.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_title.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_view.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_time.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_reco_cnt.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_deco_cnt.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_category.setOpaque(true);
		jlb_title.setOpaque(true);
		jlb_nick.setOpaque(true);
		jlb_view.setOpaque(true);
		jlb_time.setOpaque(true);
		jlb_reco_cnt.setOpaque(true);
		jlb_deco_cnt.setOpaque(true);
		jlb_category.setBounds(10, 10, 300, 30);
		jlb_title.setBounds(10, 55, 250, 20);
		jlb_nick.setBounds(260, 55, 70, 20);
		jlb_time.setBounds(330, 45, 200, 20);
		jlb_view.setBounds(330, 70, 50, 20);
		jlb_reco_cnt.setBounds(380, 70, 50, 20);
		jlb_deco_cnt.setBounds(430, 70, 50, 20);
		jp_north.add(jlb_category);
		jp_north.add(jlb_title);
		jp_north.add(jlb_nick);
		jp_north.add(jlb_view);
		jp_north.add(jlb_time);
		jp_north.add(jlb_reco_cnt);
		jp_north.add(jlb_deco_cnt);
		
		jlb_comment = new JLabel("COMMENT");
		jlb_reply_cnt = new JLabel(String.valueOf(post.getReply_cnt()));
		jlb_comment.setFont(new Font(page.memVO.getFont(),Font.PLAIN,16));
		jlb_reply_cnt.setFont(new Font(page.memVO.getFont(),Font.PLAIN,16));
		jlb_comment.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_reply_cnt.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_comment.setOpaque(true);
		jlb_reply_cnt.setOpaque(true);
		jlb_comment.setBounds(10, 5, 160, 25);
		jlb_reply_cnt.setBounds(170, 5, 50, 25);
		jp_reply.add(jlb_comment);
		jp_reply.add(jlb_reply_cnt);
	}
	public void initTextArea() {
		jta_content = new JTextArea();
		jta_content.setText(post.getPost_content());
		jta_content.setEditable(false);
		jta_content.setMargin(new Insets(10,5,10,5));
	}
	public void initGrid() {
		ReplyVO pVO = new ReplyVO();
		pVO.setPost_num(post.getPost_num());
		replyList = page.ctrl.ConnectSelect("select", "all", pVO);
		ReplyRow reply = null;
		for(int i=0;i<replyList.size();i++) {
			ReplyVO comment = (ReplyVO)replyList.get(i);
			reply = new ReplyRow(page,comment);
			replyRowList.add(reply);
		}
		int size = 6;
		if(replyRowList.size()>6) {
			size = replyRowList.size();
		}
		jp_grid = new JPanel();
		jp_grid.setLayout(new GridLayout(size,1));
		for(int i=0;i<replyRowList.size();i++) {
			jp_grid.add(replyRowList.get(i));
		}
		jp_grid.setBorder(null);
		jp_grid.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	public void initGroup() {
		jp_bubble = new JPanel();
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jp_north)
				.addComponent(jta_content)
				.addComponent(jp_reply)
				.addComponent(jp_grid)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(jp_north,100,100,100)
			.addComponent(jta_content)
			.addComponent(jp_reply,35,35,35)
			.addComponent(jp_grid)
		);
	}
	public void initGroup2() {
		jp_bubble = new JPanel();
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jp_north)
						.addComponent(jta_content)
						.addComponent(jp_file)
						.addComponent(jp_reply)
						.addComponent(jp_grid)
						)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(jp_north,100,100,100)
				.addComponent(jta_content)
				.addComponent(jp_file,85,85,85)
				.addComponent(jp_reply,35,35,35)
				.addComponent(jp_grid)
				);
	}
	public void initEvent() {
		jbtn_update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PostWritePage(page,post,PostReadPage.this);
			}
		});
		jbtn_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(PostReadPage.this, "해당 글을 삭제하시겠습니까?"
									, "게시글 삭제", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.OK_OPTION) {
					PostVO rVO = null;
					rVO = (PostVO)page.ctrl.Connect("delete", "post_del", post);
					if(rVO.getStatus()==1) {
						JOptionPane.showMessageDialog(PostReadPage.this, "게시글이 삭제되었습니다");
						PostReadPage.this.dispose();
					}
					else {
						JOptionPane.showMessageDialog(PostReadPage.this, "게시글 삭제 실패");
					}
				}
			}
		});
		jbtn_reply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String comment = JOptionPane.showInputDialog(PostReadPage.this
									, "댓글을 입력하세요."
									, "댓글 입력", JOptionPane.NO_OPTION);
				if(comment!=null) {
					ReplyVO pVO = new ReplyVO();
					pVO.setPost_num(post.getPost_num());
					pVO.setReply_nick(page.memVO.getNick());
					pVO.setReply_content(comment);
					ReplyVO rVO = null;
					rVO = (ReplyVO)page.ctrl.Connect("insert", "reply_ins", pVO);
					if(rVO.getStatus()==1) {
						JOptionPane.showMessageDialog(PostReadPage.this, "댓글이 작성되었습니다");
					}
					else {
						JOptionPane.showMessageDialog(PostReadPage.this, "댓글 작성 실패");
					}
				}
			}
		});
		jbtn_up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				post.setKeyword(page.memVO.getNick());
				PostVO rVO = null;
				rVO = (PostVO)page.ctrl.Connect("update", "post_up", post);
				if(rVO.getStatus()==1) {
					if("내글은 추천할 수 없습니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(PostReadPage.this, "내글은 추천할 수 없습니다.");
					}
					else if("해당 게시글이 추천되었습니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(PostReadPage.this, "해당 게시글이 추천되었습니다.");
					}
					else if("이미 추천한 글입니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(PostReadPage.this, "이미 추천한 글입니다.");
					}
				}
				else {
					JOptionPane.showMessageDialog(PostReadPage.this, "DB Connection Error");
				}
			}
		});
		jbtn_down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				post.setKeyword(page.memVO.getNick());
				PostVO rVO = null;
				rVO = (PostVO)page.ctrl.Connect("update", "post_down", post);
				if(rVO.getStatus()==1) {
					if("내글은 비추천할 수 없습니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(PostReadPage.this, "내글은 비추천할 수 없습니다.");
					}
					else if("해당 게시글이 비추천되었습니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(PostReadPage.this, "해당 게시글이 비추천되었습니다.");
					}
					else if("이미 비추천한 글입니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(PostReadPage.this, "이미 비추천한 글입니다.");
					}
				}
				else {
					JOptionPane.showMessageDialog(PostReadPage.this, "DB Connection Error");
				}
			}
		});
		if(attachList.size()>0) {
			jbtn_all.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					jtb_file.addRowSelectionInterval(0, jtb_file.getRowCount()-1);
				}
			});
			jbtn_none.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					jtb_file.removeRowSelectionInterval(0, jtb_file.getRowCount()-1);
				}
			});
			jbtn_download.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(0!=jtb_file.getSelectedRowCount()) {
						FileInputStream is = null;
						FileOutputStream os = null;
						String root = null;
						JFileChooser jfc = new JFileChooser();
						jfc.setDialogTitle("경로 선택");
						jfc.setMultiSelectionEnabled(false);
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int result = jfc.showSaveDialog(null);
						if(result==0) {
							root = jfc.getSelectedFile().getPath();
							int[] rows = jtb_file.getSelectedRows();
							String fileName = null;
							for(int i : rows) {
								fileName = dtm_file.getValueAt(i, 1).toString();
								try {
									File file = new File(FilePath.FilePath+fileName);
									is = new FileInputStream(file);
									os = new FileOutputStream(root+"\\"+fileName);
									int readBuffer = 0;
									int size = (int)file.length();
									byte[] buffer = new byte[size];
									while((readBuffer = is.read(buffer)) != -1) {
										os.write(buffer,0,readBuffer);
									}
								} catch (Exception e2) {
									e2.printStackTrace();
								} finally {
									try {
										is.close();
										os.close();
									} catch (Exception e3) {
										e3.printStackTrace();
									}
								}
							}
							int result2 = JOptionPane.showConfirmDialog(null
									, "다운로드가 완료되었습니다\n폴더를 여시겠습니까?", "다운로드 성공"
									, JOptionPane.YES_NO_OPTION);
							if(result2==JOptionPane.YES_OPTION) {
								try {
									Desktop desktop = Desktop.getDesktop();
									File folder = new File(root);
									desktop.open(folder);
								} catch (Exception e2) {
									e2.printStackTrace();
								}
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(PostReadPage.this, "파일을 선택하세요");
					}
				}
			});
		}
	}
	
}
