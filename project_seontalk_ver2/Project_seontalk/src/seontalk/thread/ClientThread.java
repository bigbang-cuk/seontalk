package seontalk.thread;

import java.util.List;

import javax.swing.JOptionPane;

import seontalk.control.ConnectionCtrl;
import seontalk.view.Login;
import seontalk.view.MainPage;
import seontalk.vo.SocketVO;
import seontalk.vo.MemberVO;

public class ClientThread extends Thread {
	public Login login;
	public MainPage page;
	ConnectionCtrl ctrl;
	public boolean isStop_Login = false;
	public boolean isStop = true;
	MemberVO rVO;
	SocketVO rDaoVO;
	
	public ClientThread(Login login) {
		this.login = login;
	}
	@Override
	public synchronized void run() {
		String response = null;
		ctrl = new ConnectionCtrl();
		//로그인 처리부분
		while(!isStop_Login) {
			try {
				rVO = (MemberVO)login.ois.readObject();
				int protocol = 0;
				if(rVO!=null) {
					protocol = rVO.getProtocol();
				}
				switch (protocol) {
					case Protocol.LOGIN:{
						int status = 0;
						status = rVO.getStatus();
						if(status==1) {
							response = rVO.getKeyword();
							if("success".equals(response)) {
								login.user = rVO;
								login.isLogin = true;
								login.setVisible(false);
							}
							else {
								JOptionPane.showMessageDialog(null, response);
								break;
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "DB Connection Error");
						}
					} break;
					case Protocol.EXIT:{
						isStop_Login = true;
					} break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//로그인 이외 처리부분
		while(!isStop){
			try {
				rDaoVO = (SocketVO)page.ois.readObject();
				int protocol = 0;
				if(rDaoVO!=null) {
					protocol = rDaoVO.getProtocol();
				}
				switch (protocol) {
					case Protocol.DB_SELECT:{
						
					} break;
					case Protocol.DB_DML:{
						
					} break;
					case Protocol.ROOM_CREATE:{
						
					} break;
					case Protocol.ROOM_IN:{
						
					} break;
					case Protocol.ROOM_OUT:{
						
					} break;
					case Protocol.MESSAGE:{
						
					} break;
					case Protocol.FILE:{
						
					} break;
					case Protocol.IMAGE:{
						
					} break;
					case Protocol.LOGOUT:{
						
					} break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			try {
				response = page.ois.readObject().toString();
				StringTokenizer st = null;
				int protocol = 0;
				if(response!=null) {
					st = new StringTokenizer(response,Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
					case Protocol.ROOM_CREATE:{
						String userNick = null;
						String partnerNick = null;
						String nick1 = st.nextToken();
						String nick2 = st.nextToken();
						if(nick2.equals(page.memVO.getNick())) { //초대받는입장(팝업띄우고 채팅창 열기)
							userNick = nick2;
							partnerNick = nick1;
							MemberVO pVO = new MemberVO();
							pVO.setNick(partnerNick);
							List<Object> list = ctrl.ConnectSelect("select", "check_nick", pVO);
							MemberVO rVO = (MemberVO)list.get(0);
							ChatVO pVO2 = new ChatVO();
							pVO2.setNick(partnerNick);
							pVO2.setPartner_nick(userNick);
							list = ctrl.ConnectSelect("select", "room_create", pVO2);
							ChatVO rVO2 = (ChatVO)list.get(0);
							ChatRoom room = new ChatRoom(page, rVO, rVO2);
							page.ChatRoomList.add(room);
							new ChatPopup(page, room);
							
						}
						else {	//초대한 입장(상대방 정보로 채팅창 생성)
							userNick = nick1;
							partnerNick = nick2;
							MemberVO pVO = new MemberVO();
							pVO.setNick(partnerNick);
							List<Object> list = ctrl.ConnectSelect("select", "check_nick", pVO);
							MemberVO rVO = (MemberVO)list.get(0);
							ChatVO pVO2 = new ChatVO();
							pVO2.setNick(userNick);
							pVO2.setPartner_nick(partnerNick);
							list = ctrl.ConnectSelect("select", "room_create", pVO2);
							ChatVO rVO2 = (ChatVO)list.get(0);
							ChatRoom room = new ChatRoom(page, rVO, rVO2);
							room.setVisible(true);
							page.ChatRoomList.add(room);
						}
					}break;
					case Protocol.MESSAGE:{
						String userNick = null;
						String partnerNick = null;
						String nick1 = st.nextToken();
						String nick2 = st.nextToken();
						String msg = st.nextToken();
						String emo = st.nextToken();
						if(nick2.equals(page.memVO.getNick())) { // 메세지 받는 입장
							userNick = nick2;	//내 닉네임은 서버에서 partner로 보낸 닉네임
							partnerNick = nick1;//파트너 닉네임은 서버에서 user로 보낸 닉네임
						}
						else { //메세지 보낸 입장
							userNick = nick1;
							partnerNick = nick2;
						}
						//내 채팅방 목록중 메세지를 보낸(받는) 사람과의 채팅방 조회
						ChatRoom room = null;
						for(int i=0;i<page.ChatRoomList.size();i++) {
							room = page.ChatRoomList.get(i);
							if(partnerNick.equals(room.user.getNick())) {
								break;
							}
						}
						if(nick2.equals(page.memVO.getNick())) { // 메세지 받는 입장
							ChatPartner cp = null;
							if("none".equals(emo)) {
								cp = new ChatPartner(page,msg,room.user);
							}
							else {
								ImageIcon emoticon = new ImageIcon(FilePath.SrcPath+emo);
								cp = new ChatPartner(page,emoticon,room.user);
							}
							try {
								room.jtp_chat.insertComponent(cp);
								room.sd_display.insertString(room.sd_display.getLength(), "\n", null);
								room.jtp_chat.setCaretPosition(room.sd_display.getLength());
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							//해당 채팅방의 상태정보를 새롭게 조회(DB상 최신정보가 필요하므로)
							List<Object> list = ctrl.ConnectSelect("select", "select", room.chat);
							ChatVO rVO = (ChatVO)list.get(0);
							if(userNick.equals(rVO.getNick())) {//받는 내가 채팅방을 만든사람?
								if(1==rVO.getMy_reading()) { //내가 채팅방을 읽고 있지않으면
									new ChatPopup(page, room);
								}
							}
							else if(userNick.equals(rVO.getPartner_nick())) {//받는 내가 초대된 사람?
								if(1==rVO.getPartner_reading()) { //내가 채팅방을 읽고 있지않으면
									new ChatPopup(page, room);
								}
							}
						}
						else { //메세지 보낸 입장
							ChatUser cu = null;
							if("none".equals(emo)) {
								cu = new ChatUser(page,msg);
							}
							else {
								ImageIcon emoticon = new ImageIcon(FilePath.SrcPath+emo);
								cu = new ChatUser(page,emoticon);
							}
							try {
								room.jtp_chat.insertComponent(cu);
								room.sd_display.insertString(room.sd_display.getLength(), "\n", null);
								room.jtp_chat.setCaretPosition(room.sd_display.getLength());
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							room.jtp_write.setText("");
						}
						
					}break;
					case Protocol.FILE:{
						String userNick = null;
						String partnerNick = null;
						String nick1 = st.nextToken();
						String nick2 = st.nextToken();
						String fileName = st.nextToken();
						if(nick2.equals(page.memVO.getNick())) { // 메세지 받는 입장
							userNick = nick2;	//내 닉네임은 서버에서 partner로 보낸 닉네임
							partnerNick = nick1;//파트너 닉네임은 서버에서 user로 보낸 닉네임
						}
						else { //메세지 보낸 입장
							userNick = nick1;
							partnerNick = nick2;
						}
						//내 채팅방 목록중 메세지를 보낸 사람과의 채팅방 조회
						ChatRoom room = null;
						for(int i=0;i<page.ChatRoomList.size();i++) {
							room = page.ChatRoomList.get(i);
							if(partnerNick.equals(room.user.getNick())) {
								break;
							}
						}
						if(nick2.equals(page.memVO.getNick())) { // 메세지 받는 입장
							ChatPartner cp = null;
							ChatDownload file = new ChatDownload(fileName);
							cp = new ChatPartner(page,file,room.user);
							try {
								room.jtp_chat.insertComponent(cp);
								room.sd_display.insertString(room.sd_display.getLength(), "\n", null);
								room.jtp_chat.setCaretPosition(room.sd_display.getLength());
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						else { //메세지 보낸 입장
							ChatUser cu = null;
							ChatDownload file = new ChatDownload(fileName);
							cu = new ChatUser(page,file);
							try {
								room.jtp_chat.insertComponent(cu);
								room.sd_display.insertString(room.sd_display.getLength(), "\n", null);
								room.jtp_chat.setCaretPosition(room.sd_display.getLength());
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						room.jtp_write.setText("");
						//해당 채팅방의 상태정보를 새롭게 조회(DB상 최신정보가 필요하므로)
						List<Object> list = ctrl.ConnectSelect("select", "select", room.chat);
						ChatVO rVO = (ChatVO)list.get(0);
						if(userNick.equals(rVO.getNick())) {//메세지를 받는 내가 채팅방을 만든사람?
							if(1==rVO.getMy_reading()) { //내가 채팅방을 읽고 있지않으면
								new ChatPopup(page, room);
							}
						}
						else if(userNick.equals(rVO.getPartner_nick())) {//메세지를 받는 내가 채팅방에 초대된 사람?
							if(1==rVO.getPartner_reading()) { //내가 채팅방을 읽고 있지않으면
								new ChatPopup(page, room);
							}
						}
					} break;
					case Protocol.IMAGE:{
						String userNick = null;
						String partnerNick = null;
						String nick1 = st.nextToken();
						String nick2 = st.nextToken();
						String imgName = st.nextToken();
						if(nick2.equals(page.memVO.getNick())) { // 메세지 받는 입장
							userNick = nick2;	//내 닉네임은 서버에서 partner로 보낸 닉네임
							partnerNick = nick1;//파트너 닉네임은 서버에서 user로 보낸 닉네임
						}
						else { //메세지 보낸 입장
							userNick = nick1;
							partnerNick = nick2;
						}
						//내 채팅방 목록중 메세지를 보낸 사람과의 채팅방 조회
						ChatRoom room = null;
						for(int i=0;i<page.ChatRoomList.size();i++) {
							room = page.ChatRoomList.get(i);
							if(partnerNick.equals(room.user.getNick())) {
								break;
							}
						}
						if(nick2.equals(page.memVO.getNick())) { // 메세지 받는 입장
							ChatPartner cp = null;
							ChatImg ci = new ChatImg(imgName);
							cp = new ChatPartner(page,ci.jlb_img,room.user);
							try {
								room.jtp_chat.insertComponent(cp);
								room.sd_display.insertString(room.sd_display.getLength(), "\n", null);
								room.jtp_chat.setCaretPosition(room.sd_display.getLength());
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						else { //메세지 보낸 입장
							ChatUser cu = null;
							ChatImg ci = new ChatImg(imgName);
							cu = new ChatUser(page,ci.jlb_img);
							try {
								room.jtp_chat.insertComponent(cu);
								room.sd_display.insertString(room.sd_display.getLength(), "\n", null);
								room.jtp_chat.setCaretPosition(room.sd_display.getLength());
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						room.jtp_write.setText("");
						//해당 채팅방의 상태정보를 새롭게 조회(DB상 최신정보가 필요하므로)
						List<Object> list = ctrl.ConnectSelect("select", "select", room.chat);
						ChatVO rVO = (ChatVO)list.get(0);
						if(userNick.equals(rVO.getNick())) {//메세지를 받는 내가 채팅방을 만든사람?
							if(1==rVO.getMy_reading()) { //내가 채팅방을 읽고 있지않으면
								new ChatPopup(page, room);
							}
						}
						else if(userNick.equals(rVO.getPartner_nick())) {//메세지를 받는 내가 채팅방에 초대된 사람?
							if(1==rVO.getPartner_reading()) { //내가 채팅방을 읽고 있지않으면
								new ChatPopup(page, room);
							}
						}
					} break;
					case Protocol.ROOM_OUT:{
						String partnerNick = st.nextToken();
						String msg = st.nextToken();
						ChatRoom room = null;
						for(int i=0;i<page.ChatRoomList.size();i++) {
							room = page.ChatRoomList.get(i);
							if(partnerNick.equals(room.user.getNick())) {
								break;
							}
						}
						try {
							room.sd_display.insertString(room.sd_display.getLength(), msg+"\n", null);
							room.jtp_chat.setCaretPosition(room.sd_display.getLength());
						} catch (Exception e) {
							e.printStackTrace();
						}
					} break;
					case Protocol.LOGOUT:{
						isStop = true;
					} break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}*/ 
		}
		try {
			if(isStop&&isStop_Login) {
				login.ois.close();
				login.oos.close();
				login.mySocket.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
