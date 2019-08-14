package seontalk.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
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
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.AttachVO;
import seontalk.vo.PostVO;

public class PostWritePage extends JFrame {
	JLabel			jlb_title	   = null;
	JPanel			jp_category	   = null;
	JPanel 			jp_east 	   = null;
	ButtonGroup		bg_category	   = null;
	JToggleButton	jbtn_category1 = null;
	JToggleButton	jbtn_category2 = null;
	JToggleButton	jbtn_category3 = null;
	JToggleButton	jbtn_category4 = null;
	JToggleButton	jbtn_category5 = null;
	JButton 		jbtn_file	   = null;
	JButton			jbtn_photo	   = null;
	JButton			jbtn_save	   = null;
	JTextField		jtf_title	   = null;
	JTextArea		jta_content	   = null;
	JScrollPane 	jsp_textpane   = null;
	
	JTable 			  jtb_file	   = null;
	DefaultTableModel dtm_file	   = null;
	JScrollPane		  jsp_file	   = null;
	String[]		  cols  	   = null;
	String[][]		  data		   = null;
	JButton			  jbtn_all	   = null;
	JButton			  jbtn_none	   = null;
	JButton			  jbtn_delete  = null;
	
	MainPage page;
	PostVO post;
	Theme theme = new Theme();
	Font font = null;
	String interest = null;
	
	List<Object> attachList = new ArrayList<>();
	List<File>     fileList   = new ArrayList<>();
	
	PostReadPage prp = null;
	
	public PostWritePage(MainPage page) {
		this.page = page;
		init();
	}
	public PostWritePage(MainPage page,PostVO post) {
		this.page = page;
		this.post = post;
		init();
		jtf_title.setText(post.getPost_title());
		jta_content.setText(post.getPost_content());
		interest = post.getInterest();
		if(jbtn_category1.getText().equals(post.getInterest())) {
			jbtn_category1.setSelected(true);
		}
		else if(jbtn_category2.getText().equals(post.getInterest())) {
			jbtn_category2.setSelected(true);
		}
		else if(jbtn_category3.getText().equals(post.getInterest())) {
			jbtn_category3.setSelected(true);
		}
		else if(jbtn_category4.getText().equals(post.getInterest())) {
			jbtn_category4.setSelected(true);
		}
		else if(jbtn_category5.getText().equals(post.getInterest())) {
			jbtn_category5.setSelected(true);
		}
	}
	public PostWritePage(MainPage page,String category) {
		this.page = page;
		init();
		interest = category;
		if(jbtn_category1.getText().equals(category)) {
			jbtn_category1.setSelected(true);
		}
		else if(jbtn_category2.getText().equals(category)) {
			jbtn_category2.setSelected(true);
		}
		else if(jbtn_category3.getText().equals(category)) {
			jbtn_category3.setSelected(true);
		}
		else if(jbtn_category4.getText().equals(category)) {
			jbtn_category4.setSelected(true);
		}
		else if(jbtn_category5.getText().equals(category)) {
			jbtn_category5.setSelected(true);
		}
	}
	public PostWritePage(MainPage page,PostVO post,PostReadPage prp) {
		this.page = page;
		this.post = post;
		this.prp = prp;
		init();
		jtf_title.setText(post.getPost_title());
		jta_content.setText(post.getPost_content());
		interest = post.getInterest();
		if(jbtn_category1.getText().equals(interest)) {
			jbtn_category1.setSelected(true);
		}
		else if(jbtn_category2.getText().equals(interest)) {
			jbtn_category2.setSelected(true);
		}
		else if(jbtn_category3.getText().equals(interest)) {
			jbtn_category3.setSelected(true);
		}
		else if(jbtn_category4.getText().equals(interest)) {
			jbtn_category4.setSelected(true);
		}
		else if(jbtn_category5.getText().equals(interest)) {
			jbtn_category5.setSelected(true);
		}
		setFiles();
	}
	public void setFiles() {
		attachList = prp.attachList;
		AttachVO attachVO = null;
		for(int i=0;i<attachList.size();i++) {
			attachVO = (AttachVO)attachList.get(i);
			String[] row = {String.valueOf(i+1),attachVO.getFiles(),attachVO.getBytes()};
			dtm_file.addRow(row);
			File file = new File(FilePath.FilePath+attachVO.getFiles());
			fileList.add(file);
		}
	}
	public void init() {
		setTitle(page.memVO.getNick()+"-게시글 작성");
		setLayout(null);
		setSize(500,700);
		getContentPane().setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		initLabel();
		initInput();
		initScroll();
		initButton();
		initTable();
		initEvent();
		initLocation();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
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
		jtb_file.getColumnModel().getColumn(2).setPreferredWidth(80);
		jsp_file = new JScrollPane(jtb_file
							,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
							,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp_file.setBounds(10, 535, 410, 80);
		add(jsp_file);
		Font font = new Font("HY견고딕", Font.PLAIN, 9);
		jbtn_all = new JButton("전체선택");
		jbtn_none = new JButton("전체해제");
		jbtn_delete = new JButton("선택삭제");
		jbtn_all.setFont(font);
		jbtn_none.setFont(font);
		jbtn_delete.setFont(font);
		jbtn_all.setFocusable(false);
		jbtn_none.setFocusable(false);
		jbtn_delete.setFocusable(false);
		jbtn_all.setRolloverEnabled(false);
		jbtn_none.setRolloverEnabled(false);
		jbtn_delete.setRolloverEnabled(false);
		jbtn_all.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_none.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_delete.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_all.setBounds(425, 535, 60, 25);
		jbtn_none.setBounds(425, 560, 60, 25);
		jbtn_delete.setBounds(425, 585, 60, 25);
		Insets inset = new Insets(5, 0, 5, 0);
		jbtn_all.setMargin(inset);
		jbtn_none.setMargin(inset);
		jbtn_delete.setMargin(inset);
		add(jbtn_all);
		add(jbtn_none);
		add(jbtn_delete);
		
	}
	public void initLabel() {
		jlb_title = new JLabel("질 문"
				, new ImageIcon(FilePath.SrcPath+"question01.png")
				, SwingConstants.LEFT);
		font = new Font(page.memVO.getFont(),Font.PLAIN,20);
		jlb_title.setFont(font);
		jlb_title.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_title.setOpaque(true);
		jlb_title.setBounds(10, 10, 100, 40);
		add(jlb_title);
	}
	public void initInput() {
		font = new Font(page.memVO.getFont(),Font.PLAIN,16);
		jtf_title = new JTextField();
		jtf_title.setFont(font);
		jtf_title.setBounds(110, 15, 380, 30);
		add(jtf_title);
		
		jta_content = new JTextArea();
		jta_content.setLineWrap(true);
		jta_content.setMargin(new Insets(10,5,10,5));
		jsp_textpane = new JScrollPane(jta_content
						,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
						,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp_textpane.setBounds(10, 110, 400, 420);
		add(jsp_textpane);
	}
	public void initButton() {
		font = new Font(page.memVO.getFont(),Font.PLAIN,18);
		jbtn_category1 = new JToggleButton("자바/JSP");
		jbtn_category2 = new JToggleButton("오라클 SQL");
		jbtn_category3 = new JToggleButton("자바스크립트");
		jbtn_category4 = new JToggleButton("HTML");
		jbtn_category5 = new JToggleButton("안드로이드");
		jbtn_category1.setRolloverEnabled(false);
		jbtn_category2.setRolloverEnabled(false);
		jbtn_category3.setRolloverEnabled(false);
		jbtn_category4.setRolloverEnabled(false);
		jbtn_category5.setRolloverEnabled(false);
		jbtn_category1.setFocusable(false);
		jbtn_category2.setFocusable(false);
		jbtn_category3.setFocusable(false);
		jbtn_category4.setFocusable(false);
		jbtn_category5.setFocusable(false);
		jbtn_category1.setMargin(new Insets(0, 0, 0, 0)); 
		jbtn_category2.setMargin(new Insets(0, 0, 0, 0)); 
		jbtn_category3.setMargin(new Insets(0, 0, 0, 0)); 
		jbtn_category4.setMargin(new Insets(0, 0, 0, 0)); 
		jbtn_category5.setMargin(new Insets(0, 0, 0, 0)); 
		jbtn_category1.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_category2.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_category3.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_category4.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_category5.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		bg_category = new ButtonGroup();
		bg_category.add(jbtn_category1);
		bg_category.add(jbtn_category2);
		bg_category.add(jbtn_category3);
		bg_category.add(jbtn_category4);
		bg_category.add(jbtn_category5);
		jp_category = new JPanel();
		jp_category.setLayout(new GridLayout(1,5));
		jp_category.add(jbtn_category1);
		jp_category.add(jbtn_category2);
		jp_category.add(jbtn_category3);
		jp_category.add(jbtn_category4);
		jp_category.add(jbtn_category5);
		jp_category.setBounds(10, 50, 480, 50);
		add(jp_category);
		
		jbtn_file = new JButton(new ImageIcon(FilePath.SrcPath+"folder01.png"));
		jbtn_photo = new JButton(new ImageIcon(FilePath.SrcPath+"picture01.png"));
		jbtn_file.setFocusable(false);
		jbtn_photo.setFocusable(false);
		jbtn_file.setRolloverEnabled(false);
		jbtn_photo.setRolloverEnabled(false);
		jp_east = new JPanel(new GridLayout(2,1));
		jp_east.add(jbtn_file);
		jp_east.add(jbtn_photo);
		jp_east.setBounds(415, 110, 75, 120);
		add(jp_east);
		
		jbtn_save = new JButton("저 장");
		jbtn_save.setFont(font);
		jbtn_save.setBackground(theme.setInnerColor(page.memVO.getTheme()));
		jbtn_save.setFocusable(false);
		jbtn_save.setRolloverEnabled(false);
		jbtn_save.setBounds(150, 630, 200, 30);
		add(jbtn_save);
	}
	public void initScroll() {
	    jsp_textpane.getVerticalScrollBar().setUnitIncrement(20);
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
	public void initEvent() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(PostWritePage.this, "저장 안되는데 끌거임?"
											, "게시글 작성 종료", JOptionPane.YES_NO_OPTION);
				if(result!=JOptionPane.OK_OPTION) {
					return;
				}
				else {
					PostWritePage.this.dispose();
				}
				super.windowClosing(e);
			}
		});
		jbtn_file.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("파일 선택");
				jfc.setMultiSelectionEnabled(false);
				int result = jfc.showSaveDialog(PostWritePage.this);
				if(result==0) {
					File file = jfc.getSelectedFile();
					String fileName = file.getName();
					double size = file.length();
					int cnt = 0;
					while(size>1000) {
						size /= 1000;
						cnt++;
					}
					String fileSize = String.format("%,.2f", size);
					switch (cnt) {
						case 0:{
							fileSize += "Byte";
						} break;
						case 1:{
							fileSize += "KB";
						} break;
						case 2:{
							fileSize += "MB";
						} break;
						case 3:{
							fileSize += "GB";
						} break;
						case 4:{
							fileSize += "TB";
						} break;
					}
					int rowCount = jtb_file.getRowCount();
					String[] input = {String.valueOf(rowCount+1),fileName,fileSize};
					dtm_file.addRow(input);
					AttachVO attachVO = new AttachVO();
					attachVO.setFiles(fileName);
					attachVO.setBytes(fileSize);
					attachList.add(attachVO);
					fileList.add(file);
				}
				
			}
		});
		jbtn_photo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("사진 선택");
				jfc.setFileFilter(new FileNameExtensionFilter("JPG File", "jpg"));
				jfc.setFileFilter(new FileNameExtensionFilter("PNG File", "png"));
				jfc.setMultiSelectionEnabled(false);
				int result = jfc.showSaveDialog(PostWritePage.this);
				if(result==0) {
					File file = jfc.getSelectedFile();
					String imgName = file.getName();
					double size = file.length();
					int cnt = 0;
					while(size>1000) {
						size /= 1000;
						cnt++;
					}
					String fileSize = String.format("%,.2f", size);
					switch (cnt) {
						case 0:{
							fileSize += "Byte";
						} break;
						case 1:{
							fileSize += "KB";
						} break;
						case 2:{
							fileSize += "MB";
						} break;
						case 3:{
							fileSize += "GB";
						} break;
						case 4:{
							fileSize += "TB";
						} break;
					}
					int rowCount = jtb_file.getRowCount();
					String[] input = {String.valueOf(rowCount+1),imgName,fileSize};
					dtm_file.addRow(input);
					AttachVO attachVO = new AttachVO();
					attachVO.setFiles(imgName);
					attachVO.setBytes(fileSize);
					attachList.add(attachVO);
					fileList.add(file);
				}
			}
		});
		jbtn_save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtf_title.getText().length()==0) {
					JOptionPane.showMessageDialog(PostWritePage.this, "제목을 입력하세요");
				}
				else if(interest==null) {
					JOptionPane.showMessageDialog(PostWritePage.this, "카테고리를 선택하세요");
				}
				else {
					int result = JOptionPane.showConfirmDialog(PostWritePage.this, "저장??"
							, "게시글 등록", JOptionPane.YES_NO_OPTION);
					if(result!=JOptionPane.OK_OPTION) {
						return;
					}
					else {
						if(post==null) {
							PostVO postVO = new PostVO();
							postVO.setId(page.memVO.getId());
							postVO.setNick(page.memVO.getNick());
							postVO.setPost_title(jtf_title.getText());
							postVO.setPost_content(jta_content.getText());
							postVO.setInterest(interest);
							PostVO rVO = null;
							rVO = (PostVO)page.ctrl.Connect("insert", "post_ins", postVO);
							if(rVO.getStatus()==1) {
								int post_num = rVO.getPost_num();
								AttachVO pVO = null;
								FileInputStream is = null;
								FileOutputStream os = null;
								for(int i=0;i<attachList.size();i++) {
									pVO = (AttachVO)attachList.get(i);
									pVO.setPost_num(post_num);
									page.ctrl.Connect("insert", "attaching", pVO);
								}
								for(int i=0;i<fileList.size();i++) {
									File file = fileList.get(i);
									try {
										is = new FileInputStream(file);
										os = new FileOutputStream(FilePath.FilePath+file.getName());
										int readBuffer = 0;
										int size = (int)file.length();
										byte[] buffer = new byte[size];
										while((readBuffer = is.read(buffer))!=-1) {
											os.write(buffer, 0, readBuffer);
										}
									} catch (Exception e2) {
										e2.printStackTrace();
									}
								}
								JOptionPane.showMessageDialog(PostWritePage.this, "게시글이 등록되었습니다");
								PostWritePage.this.dispose();
							}
							else {
								JOptionPane.showMessageDialog(PostWritePage.this, "게시글 등록 실패");
							}
						}
						else {
							PostVO postVO = new PostVO();
							postVO.setPost_num(post.getPost_num());
							postVO.setPost_title(jtf_title.getText());
							postVO.setPost_content(jta_content.getText());
							postVO.setInterest(interest);
							PostVO rVO = null;
							rVO = (PostVO)page.ctrl.Connect("update", "post_upd", postVO);
							if(rVO.getStatus()==1) {
								int post_num = post.getPost_num();
								AttachVO pVO = null;
								FileInputStream is = null;
								FileOutputStream os = null;
								for(int i=0;i<attachList.size();i++) {
									pVO = (AttachVO)attachList.get(i);
									pVO.setPost_num(post_num);
									page.ctrl.Connect("insert", "attaching", pVO);
								}
								for(int i=0;i<fileList.size();i++) {
									File file = fileList.get(i);
									try {
										is = new FileInputStream(file);
										os = new FileOutputStream(FilePath.FilePath+file.getName());
										int readBuffer = 0;
										int size = (int)file.length();
										byte[] buffer = new byte[size];
										while((readBuffer = is.read(buffer))!=-1) {
											os.write(buffer, 0, readBuffer);
										}
									} catch (Exception e2) {
										e2.printStackTrace();
									}
								}
								JOptionPane.showMessageDialog(PostWritePage.this, "게시글이 수정되었습니다");
								PostWritePage.this.dispose();
							}
							else {
								JOptionPane.showMessageDialog(PostWritePage.this, "게시글 수정 실패");
							}
						}
					}
				}
			}
		});
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
		jbtn_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] rows = jtb_file.getSelectedRows();
				for(int i=rows.length-1;i>=0;i--) {
					int j = rows[i];
					String fileName = jtb_file.getValueAt(j, 1).toString();
					if(post!=null) {
						AttachVO pVO = new AttachVO();
						pVO.setPost_num(post.getPost_num());
						pVO.setFiles(fileName);
						page.ctrl.Connect("delete", "attaching", pVO);
						fileList.remove(j);
						attachList.remove(j);
					}
					dtm_file.removeRow(j);
					File file = new File(FilePath.FilePath+fileName);
					if(file.exists()) {
						file.delete();
					}
				}
				for(int i=0;i<jtb_file.getRowCount();i++) {
					dtm_file.setValueAt(i+1, i, 0);
				}
			}
		});
		jbtn_category1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interest = jbtn_category1.getText();
			}
		});
		jbtn_category2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interest = jbtn_category2.getText();
			}
		});
		jbtn_category3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interest = jbtn_category3.getText();
			}
		});
		jbtn_category4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interest = jbtn_category4.getText();
			}
		});
		jbtn_category5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interest = jbtn_category5.getText();
			}
		});
	}
}
