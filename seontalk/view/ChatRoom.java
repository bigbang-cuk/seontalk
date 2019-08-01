package seontalk.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import seontalk.chat.Protocol;
import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;

public class ChatRoom extends JFrame {
	public JPanel			jp_bubble  	= new JPanel();
	public StyledDocument   sd_display 	= new DefaultStyledDocument(new StyleContext());
	public JTextPane 		jtp_chat   	= new JTextPane(sd_display);
	public JScrollPane 		jsp_chat   	= new JScrollPane(jtp_chat
							 	 	,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
							 	 	,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public StyledDocument   sd_write 	= new DefaultStyledDocument(new StyleContext());
	public JTextPane 		jtp_write   = new JTextPane(sd_write);
	public JScrollPane 		jsp_write   = new JScrollPane(jtp_write
									,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
									,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	JPanel 		jp_south   = null;
	JButton 	jbtn_emo   = null;
	JButton		jbtn_file  = null;
	JButton		jbtn_img   = null;
	JButton		jbtn_exit  = null;
	
	JButton 	jbtn_send  = new JButton("보내기");
	ProfileRow	jp_prof	   = null;
	MainPage 	page;
	Theme		theme 	   = new Theme();
	
	public ChatEmoticon jf_emo = null;
	public String emoticon	= "none";
	
	public MemberVO user;
	public ChatVO chat;
	public List<Object> chatLog;
	
	public ChatRoom(MainPage page, MemberVO user) {
		this.page = page;
		this.user = user;
		init();
	}
	public ChatRoom(MainPage page, MemberVO user,ChatVO chat) {
		this.page = page;
		this.user = user;
		this.chat = chat;
		init();
	}
	public ChatRoom(MainPage page, MemberVO user,ChatVO chat,List<Object> chatLog) {
		this.page = page;
		this.user = user;
		this.chat = chat;
		this.chatLog = chatLog;
		init();
	}
	
	public void init() {
		jp_prof = new ProfileRow(page,user);
		jf_emo = new ChatEmoticon(page, this);
		setTitle(page.memVO.getNick()+"-채팅방");
		setSize(405, 680);
		initLocation();
		initScroll();
		initSouth();
		initGroup();
		initEvent();
		jtp_chat.setEditable(false);
		jtp_chat.setEnabled(false);
		jtp_chat.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jsp_chat.setBorder(null);
		add(jp_bubble);
		setResizable(false);
		setVisible(false);
		if(chatLog!=null) {
			writeChatLog();
		}
	}
	public void writeChatLog() {
		for(int i=0;i<chatLog.size();i++) {
			ChatLogVO log = (ChatLogVO)chatLog.get(i);
			String writer = log.getWrite_nick();
			String temp = log.getContent();
			StringTokenizer st = new StringTokenizer(temp, "|");
			String msg = st.nextToken();
			String gubun = null;
			while(st.hasMoreTokens()) {
				gubun = st.nextToken();
			}
			if(page.memVO.getNick().equals(writer)) {
				ChatUser cu = null;
				if("EMOTICON".equals(gubun)) {
					ImageIcon emoticon = new ImageIcon(FilePath.SrcPath+msg);
					cu = new ChatUser(page,emoticon);
				}
				else if("FILE".equals(gubun)) {
					ChatDownload file = new ChatDownload(msg);
					cu = new ChatUser(page,file);
				}
				else if("IMAGE".equals(gubun)) {
					ChatImg ci = new ChatImg(msg);
					cu = new ChatUser(page,ci.jlb_img);
				}
				else {
					cu = new ChatUser(page,msg);
				}
				try {
					jtp_chat.insertComponent(cu);
					sd_display.insertString(sd_display.getLength(), "\n", null);
					jtp_chat.setCaretPosition(sd_display.getLength());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				jtp_write.setText("");
			}
			else if(user.getNick().equals(writer)) {
				ChatPartner cp =null;
				if("EMOTICON".equals(gubun)) {
					ImageIcon emoticon = new ImageIcon(FilePath.SrcPath+msg);
					cp = new ChatPartner(page,emoticon,user);
				}
				else if("FILE".equals(gubun)) {
					ChatDownload file = new ChatDownload(msg);
					cp = new ChatPartner(page,file,user);
				}
				else if("IMAGE".equals(gubun)) {
					ChatImg ci = new ChatImg(msg);
					cp = new ChatPartner(page,ci.jlb_img,user);
				}
				else {
					cp = new ChatPartner(page,msg,user);
				}
				try {
					jtp_chat.insertComponent(cp);
					sd_display.insertString(sd_display.getLength(), "\n", null);
					jtp_chat.setCaretPosition(sd_display.getLength());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				jtp_write.setText("");
			}
			
		}
	}
	public void sendMessage() throws IOException{
		String msg = null;
		if("none".equals(emoticon)) {
			msg = jtp_write.getText();
		}
		else {
			msg = "emo";
		}
		page.oos.writeObject(Protocol.MESSAGE
				+Protocol.seperator+chat.getTalklist_num()
				+Protocol.seperator+user.getNick()
				+Protocol.seperator+msg
				+Protocol.seperator+emoticon);
	}
	public void sendFile(String fileName) throws IOException{
		page.oos.writeObject(Protocol.FILE
				+Protocol.seperator+chat.getTalklist_num()
				+Protocol.seperator+user.getNick()
				+Protocol.seperator+fileName);
	}
	public void sendImage(String ImgName) throws IOException{
		page.oos.writeObject(Protocol.IMAGE
				+Protocol.seperator+chat.getTalklist_num()
				+Protocol.seperator+user.getNick()
				+Protocol.seperator+ImgName);
	}
	public void initSouth() {
		jbtn_emo = new JButton(new ImageIcon(FilePath.SrcPath+"emoticon02.png"));
		jbtn_file = new JButton(new ImageIcon(FilePath.SrcPath+"folder02.png"));
		jbtn_img = new JButton(new ImageIcon(FilePath.SrcPath+"picture02.png"));
		jbtn_exit = new JButton(new ImageIcon(FilePath.SrcPath+"exit01.png"));
		jbtn_emo.setFocusable(false);
		jbtn_file.setFocusable(false);
		jbtn_img.setFocusable(false);
		jbtn_exit.setFocusable(false);
		jbtn_emo.setRolloverEnabled(false);
		jbtn_file.setRolloverEnabled(false);
		jbtn_img.setRolloverEnabled(false);
		jbtn_exit.setRolloverEnabled(false);
		jp_south = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		jp_south.add(jbtn_emo);
		jp_south.add(jbtn_file);
		jp_south.add(jbtn_img);
		jp_south.add(jbtn_exit);
	}
	public void initEvent() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ChatVO pVO = new ChatVO();
				pVO.setTalklist_num(chat.getTalklist_num());
				pVO.setNick(page.memVO.getNick());
				page.ctrl.Connect("update", "unreading", pVO);
				super.windowClosing(e);
			}
		});
		jbtn_send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					emoticon = "none";
					sendMessage();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		jtp_write.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.isShiftDown()) {
					if(10==e.getKeyCode()) {
						try {
							sd_write.insertString(sd_write.getLength(), "\n", null);
							jtp_write.setCaretPosition(sd_write.getLength());
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
				else {
					if(10==e.getKeyCode()) {
						try {
							emoticon = "none";
							sendMessage();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
				super.keyReleased(e);
			}
		});
		jbtn_emo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jf_emo.setVisible(true);
			}
		});
		jbtn_file.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileInputStream is = null;
				FileOutputStream os = null;
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("파일 선택");
				jfc.setMultiSelectionEnabled(false);
				int result = jfc.showSaveDialog(ChatRoom.this);
				if(result==0) {
					File file = jfc.getSelectedFile();
					String fileName = file.getName();
					try {
						is = new FileInputStream(file);
						os = new FileOutputStream(FilePath.FilePath+fileName);
						int readBuffer = 0;
						int size = (int)file.length();
						byte[] buffer = new byte[size];
						while((readBuffer = is.read(buffer))!=-1) {
							os.write(buffer, 0, readBuffer);
						}
						sendFile(fileName);
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
				
			}
		});
		jbtn_img.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("사진 선택");
				jfc.setFileFilter(new FileNameExtensionFilter("JPG File", "jpg"));
				jfc.setFileFilter(new FileNameExtensionFilter("PNG File", "png"));
				jfc.setMultiSelectionEnabled(false);
				int result = jfc.showSaveDialog(ChatRoom.this);
				if(result==0) {
					File file = jfc.getSelectedFile();
					StringTokenizer st = new StringTokenizer(file.getName(), ".");
					String imgName = file.getName();
					String imgForm = "";
					while(st.hasMoreTokens()) {
						imgForm = st.nextToken();
					}
					try {
						BufferedImage bi = ImageIO.read(file);
						ImageIO.write(bi, imgForm, new File(FilePath.ImgPath+imgName));
						sendImage(imgName);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		jbtn_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(ChatRoom.this, "채팅방을 나가겠습니까?", "나가기"
														,JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.YES_OPTION) {
					ChatVO pVO = new ChatVO();
					pVO.setTalklist_num(chat.getTalklist_num());
					pVO.setNick(page.memVO.getNick());
					ChatVO rVO = (ChatVO)page.ctrl.Connect("delete", "room_out", pVO);
					if(1==rVO.getStatus()) {
						try {
							page.oos.writeObject(Protocol.ROOM_OUT
											+Protocol.seperator+chat.getTalklist_num()
											+Protocol.seperator+user.getNick()
											+Protocol.seperator+page.memVO.getNick()
											+"님이 채팅방을 나갔습니다.");
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						page.ChatRoomList.remove(ChatRoom.this);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(ChatRoom.this, "DB Connection Error");
					}
				}
				
			}
		});
	}
	public void initScroll() {
	    jsp_chat.getVerticalScrollBar().setUnitIncrement(20);
	}
	public void initGroup() {
		GroupLayout layout = new GroupLayout(jp_bubble);
		jp_bubble.setLayout(layout);
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jp_prof,400,400,400)	
				.addComponent(jsp_chat,400,400,400)
				.addGroup(layout.createSequentialGroup()
					.addComponent(jsp_write,310,310,310)
					.addComponent(jbtn_send,90,90,90)
				)
				.addComponent(jp_south,400,400,400)
			)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addComponent(jp_prof,80,80,80)
			.addComponent(jsp_chat,490,490,490)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(jsp_write,50,50,50)
				.addComponent(jbtn_send,50,50,50)
			)
			.addComponent(jp_south,40,40,40)
		);
	}
	public void initLocation() {
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension mainPageSize = page.getSize();
		Point point = page.getLocation();
		if((monitor.width-(point.x+mainPageSize.width))<getSize().width) {
			if(monitor.height-(point.y+getSize().height)<0) {
				setLocation(point.x-(getSize().width+5), monitor.height-getSize().height);
			}
			else {
				setLocation(point.x-(getSize().width+5), point.y);
			}
		}
		else {
			if(monitor.height-(point.y+getSize().height)<0) {
				setLocation(point.x+mainPageSize.width+5, monitor.height-getSize().height);
			}
			else {
				setLocation(point.x+mainPageSize.width+5, point.y);
			}
		}
	}
}
