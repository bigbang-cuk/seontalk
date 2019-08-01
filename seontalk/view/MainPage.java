package seontalk.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import seontalk.chat.ClientThread;
import seontalk.chat.Protocol;
import seontalk.control.ConnectionCtrl;
import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;

public class MainPage extends JFrame{
	//////////////////////////////////상단 화면///////////////////////////////////
	MyPage 		jp_myPage 		= null;
	MyPost 		jp_myPost 		= null;
	MyBest 		jp_myBest 		= null;
	Post 		jp_post 		= null;
	public Chat jp_chat 		= null;
	/////////////////////////멘토페이지 세부화면/////////////////////////////
	//세부페이지는 멘토 내부 이벤트로 화면전환이 일어남
	MentorRank		jp_rank			= null;
	MentorList		jp_list			= null;
	MentorSearch	jp_search		= null;
	/////////////////////////멘토언어별 랭크/////////////////////////////
	String 			name			= null;
	MentorListCategory jp_mls		= null;
	////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////
	Mentor 			jp_mentor 		= null;
	Setting 		jp_setting 		= null;
	public JPanel 	jp_page 		= null;
	//////////////////////////////////상단 화면////////////////////////////////////
	//////////////////////////////////하단 버튼////////////////////////////////////
	JToggleButton 	jbtn_myPage  	= null;
	JToggleButton 	jbtn_chat    	= null;
	JToggleButton 	jbtn_post    	= null;
	JToggleButton 	jbtn_mentor  	= null;
	JToggleButton 	jbtn_setting 	= null;
	ButtonGroup 	bg 				= null;
	JPanel 			jp_south 		= null;
	//////////////////////////////////하단 버튼////////////////////////////////////
	public MemberVO memVO = new MemberVO();
	Theme 	 theme = new Theme();
	public List<ChatRoom> ChatRoomList = new ArrayList<>();
	List<ProfilePage> ProfileList = new ArrayList<>();
	
	public ConnectionCtrl ctrl = new ConnectionCtrl();
	
	public Socket mySocket = null;
	public ClientThread cThread = null;
	public ObjectOutputStream oos = null;
	public ObjectInputStream ois = null;
	String ip = "192.168.0.189";
	int port = 7777;
	
	public MainPage(MemberVO pVO) {
		memVO = pVO;
		getChatList();
		connect_process();
		init();
	}
	
	public void connect_process() {
		try {
			mySocket = new Socket(ip,port);
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());
			oos.writeObject(Protocol.WAIT
					+Protocol.seperator+memVO.getNick());
			cThread = new ClientThread(this);
			cThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getChatList() {
		ChatRoomList = new ArrayList<ChatRoom>();
		ChatVO pVO = new ChatVO();
		pVO.setNick(memVO.getNick());
		List<Object> list = ctrl.ConnectSelect("select", "all", pVO);
		if(list!=null) {
			for(int i=0;i<list.size();i++) {
				ChatVO rVO = (ChatVO)list.get(i);
				MemberVO pVO2 = new MemberVO();
				if(memVO.getNick().equals(rVO.getNick())) {
					pVO2.setNick(rVO.getPartner_nick());
				}
				else {
					pVO2.setNick(rVO.getNick());
				}
				List<Object> list2 = ctrl.ConnectSelect("select", "check_nick", pVO2);
				MemberVO user = (MemberVO)list2.get(0);
				List<Object> chatLog = getChatLog(rVO.getTalklist_num());
				ChatRoom room = null;
				if(chatLog.size()!=0) {
					room = new ChatRoom(MainPage.this, user, rVO, chatLog);
				}
				else {
					room = new ChatRoom(MainPage.this, user, rVO);
				}
				ChatRoomList.add(room);
			}
		}
	}
	public List<Object> getChatLog(int roomNum){
		List<Object> chatLog = null;
		ChatLogVO pVO = new ChatLogVO();
		pVO.setTalklist_num(roomNum);
		chatLog = ctrl.ConnectSelect("select", "all", pVO);
		return chatLog;
	}
	
	public void init() {	//화면 초기화 메소드
		jp_myPage 		= new MyPage(this,"마이페이지");
		jp_myPost 		= new MyPost(this,"내 게시글");
		jp_myBest 		= new MyBest(this,"내 추천글");
		jp_chat 		= new Chat(this,"채팅");
		jp_post 		= new Post(this, "게시글");
		jp_rank			= new MentorRank(this);
		jp_list			= new MentorList(this);
		jp_search		= new MentorSearch(this, "멘토 랭킹 검색");
		jp_mentor 		= new Mentor(this, "멘토");
		jp_setting 		= new Setting(this, "환경설정");
		jp_page 		= new JPanel(); // 페이지가 교체되는 panel
		jbtn_myPage  	= new JToggleButton(new ImageIcon(FilePath.SrcPath+"person01.png"));
		jbtn_chat    	= new JToggleButton(new ImageIcon(FilePath.SrcPath+"chat02.png"));
		jbtn_post    	= new JToggleButton(new ImageIcon(FilePath.SrcPath+"list02.png"));
		jbtn_mentor  	= new JToggleButton(new ImageIcon(FilePath.SrcPath+"people01.png"));
		jbtn_setting 	= new JToggleButton(new ImageIcon(FilePath.SrcPath+"setting01.png"));
		bg 				= new ButtonGroup(); //토글버튼 그룹화
		jp_south 		= new JPanel(); //버튼이 붙는 panel
		//교체 패널에 초기화면을 마이페이지를 저장
		jp_page = jp_myPage.jp_bubble;
		add(jp_page);
		//버튼 붙이기
		initButton();
		initScroll();
		initEvent();
		jp_south.setLayout(new GridLayout(1,5));
		jp_south.add(jbtn_myPage);
		jp_south.add(jbtn_chat);
		jp_south.add(jbtn_post);
		jp_south.add(jbtn_mentor);
		jp_south.add(jbtn_setting);
		add("South",jp_south);
		//전체화면 설정
		setTitle(memVO.getNick()+"-선톡");
		setSize(410,545);
		initLocation();
		setResizable(false);
		setVisible(true);
	}
	public void initScroll() {
		UIDefaults defaults = UIManager.getDefaults();
	    defaults.put("ScrollBar.width", new Integer("8"));
	}
	public void initLocation() {
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((monitor.width-getSize().width)/2, (monitor.height-getSize().height)/2);
	}
	public void Logout() {
		ctrl.Connect("update", "logout", memVO);
		try {
			oos.writeObject(Protocol.LOGOUT+Protocol.seperator+memVO.getNick());
			cThread.join();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	public void initEvent() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Logout();
				System.exit(0);
				super.windowClosing(e);
			}
		});
		//버튼 이벤트 추가
		jbtn_myPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//모든 페이지(panel)은 jp_page에 대입된 후 화면에 add하기 떄문에
				//화면전환이벤트의 대부분은 아래와 같이 이루어짐.
				remove(jp_page);//우선 원래화면 지우고
				jp_myPage = new MyPage(MainPage.this, "마이페이지");
				jp_page = jp_myPage.jp_bubble;//교체될 화면을 대입하고
				add(jp_page);//다시 붙이고
				revalidate();
				repaint();//화면 갱신
			}
		});
		jbtn_chat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getChatList();
				remove(jp_page);
				jp_chat = new Chat(MainPage.this, "채팅");
				jp_page = jp_chat.jp_bubble;
				add(jp_page);
				revalidate();
				repaint();
			}
		});
		jbtn_post.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(jp_page);
				jp_post = new Post(MainPage.this, "게시글");
				jp_page = jp_post.jp_bubble;
				add(jp_page);
				revalidate();
				repaint();
			}
		});
		jbtn_mentor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(jp_page);
				jp_rank 	= new MentorRank(MainPage.this);
				jp_list 	= new MentorList(MainPage.this);
				jp_search 	= new MentorSearch(MainPage.this, "멘토 랭킹 검색");
				jp_mentor 	= new Mentor(MainPage.this, "멘토");
				jp_page = jp_mentor.jp_bubble;
				add(jp_page);
				revalidate();
				repaint();
			}
		});
		jbtn_setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(jp_page);
				jp_page = jp_setting;
				add(jp_page);
				revalidate();
				repaint();
			}
		});
	}
	public void initButton() {	//버튼초기화 메소드
		//토글버튼 그룹추가
		bg.add(jbtn_myPage);
		bg.add(jbtn_chat);
		bg.add(jbtn_post);
		bg.add(jbtn_mentor);
		bg.add(jbtn_setting);
		jbtn_myPage.setBackground(theme.setInnerColor(memVO.getTheme()));
		jbtn_chat.setBackground(theme.setInnerColor(memVO.getTheme()));
		jbtn_post.setBackground(theme.setInnerColor(memVO.getTheme()));
		jbtn_mentor.setBackground(theme.setInnerColor(memVO.getTheme()));
		jbtn_setting.setBackground(theme.setInnerColor(memVO.getTheme()));
		jbtn_myPage.setRolloverEnabled(false);
		jbtn_chat.setRolloverEnabled(false);
		jbtn_post.setRolloverEnabled(false);
		jbtn_mentor.setRolloverEnabled(false);
		jbtn_setting.setRolloverEnabled(false);
		jbtn_myPage.setFocusable(false);
		jbtn_chat.setFocusable(false);
		jbtn_post.setFocusable(false);
		jbtn_mentor.setFocusable(false);
		jbtn_setting.setFocusable(false);
		jbtn_myPage.setSelected(true);
	}
}


