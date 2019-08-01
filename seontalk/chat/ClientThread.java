package seontalk.chat;

import java.util.List;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import seontalk.control.ConnectionCtrl;
import seontalk.util.FilePath;
import seontalk.view.ChatDownload;
import seontalk.view.ChatImg;
import seontalk.view.ChatPartner;
import seontalk.view.ChatPopup;
import seontalk.view.ChatRoom;
import seontalk.view.ChatUser;
import seontalk.view.MainPage;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;

public class ClientThread extends Thread {

	MainPage page = null;
	ConnectionCtrl ctrl = null;
	public boolean isStop = false;
	
	public ClientThread(MainPage page) {
		this.page = page;
	}
	
	@Override
	public void run() {
		String response = null;
		ctrl = new ConnectionCtrl();
		while(!isStop){
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
			} 
			
		}
		try {
			page.ois.close();
			page.oos.close();
			page.mySocket.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
