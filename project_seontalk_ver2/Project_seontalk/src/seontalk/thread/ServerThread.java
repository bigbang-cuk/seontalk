package seontalk.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import seontalk.control.ConnectionCtrl;
import seontalk.vo.AttachVO;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.SocketVO;
import seontalk.vo.MemberVO;
import seontalk.vo.PostVO;
import seontalk.vo.ReplyVO;


public class ServerThread extends Thread{
	Server server;
	Socket client;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	ConnectionCtrl ctrl = new ConnectionCtrl();
	String user = "";
	
	ServerSocket chatServer;
	Socket client2;
	ServerChatThread scThread;
	ObjectOutputStream coos;
	ObjectInputStream cois;
	public ServerThread(Server server) {
		this.server = server;
		server.jta_log.append("Creating Server Thread is successed\n");
		server.jta_log.append("---------------------------\n\n");
	}
	public void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void send(SocketVO rsVO) {
		try {
			oos.writeObject(rsVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public synchronized void run() {
		server.jta_log.append("Running Server Thread\n");
		try {
			oos = server.oos;
			ois = server.ois;
			chatServer = new ServerSocket(10000);
			client2 = chatServer.accept();
			coos = new ObjectOutputStream(client2.getOutputStream());
			cois = new ObjectInputStream(client2.getInputStream());
			scThread = new ServerChatThread(server,coos,cois);
			scThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isStop = false;
		SocketVO psVO;
		try {
			while(!isStop) {
				psVO = (SocketVO)ois.readObject();
				int protocol = 0;
				if(psVO!=null) {
					protocol = psVO.getProtocol();
				}
				switch (protocol) {
					case Protocol.DB_SELECT:{
						String command = psVO.getCommand();
						String work = psVO.getWork();
						SocketVO rsVO = new SocketVO();
						List<Object> VOList = new ArrayList<>();
						if(null!=psVO.getMemVO()) {
							MemberVO pVO = psVO.getMemVO();
							VOList = ctrl.ConnectSelect(command, work, pVO);
							rsVO.setMemberList(VOList);
						}
						if(null!=psVO.getChatVO()) {
							ChatVO pVO = psVO.getChatVO();
							VOList = ctrl.ConnectSelect(command, work, pVO);
							rsVO.setChatList(VOList);
						}
						if(null!=psVO.getPostVO()) {
							PostVO pVO = psVO.getPostVO();
							VOList = ctrl.ConnectSelect(command, work, pVO);
							rsVO.setPostList(VOList);
						}
						if(null!=psVO.getReplyVO()) {
							ReplyVO pVO = psVO.getReplyVO();
							VOList = ctrl.ConnectSelect(command, work, pVO);
							rsVO.setReplyList(VOList);
						}
						if(null!=psVO.getLogVO()) {
							ChatLogVO pVO = psVO.getLogVO();
							VOList = ctrl.ConnectSelect(command, work, pVO);
							rsVO.setChatLogList(VOList);
						}
						if(null!=psVO.getAttachVO()) {
							AttachVO pVO = psVO.getAttachVO();
							VOList = ctrl.ConnectSelect(command, work, pVO);
							rsVO.setAttachList(VOList);
						}
						send(rsVO);
					} break;
					case Protocol.DB_DML:{
						String command = psVO.getCommand();
						String work = psVO.getWork();
						SocketVO rsVO = new SocketVO();
						if(null!=psVO.getMemVO()) {
							MemberVO pVO = psVO.getMemVO();
							MemberVO rVO = (MemberVO)ctrl.Connect(command, work, pVO);
							rsVO.setMemVO(rVO);
						}
						if(null!=psVO.getChatVO()) {
							ChatVO pVO = psVO.getChatVO();
							ChatVO rVO = (ChatVO)ctrl.Connect(command, work, pVO);
							rsVO.setChatVO(rVO);
						}
						if(null!=psVO.getPostVO()) {
							PostVO pVO = psVO.getPostVO();
							PostVO rVO = (PostVO)ctrl.Connect(command, work, pVO);
							rsVO.setPostVO(rVO);
						}
						if(null!=psVO.getReplyVO()) {
							ReplyVO pVO = psVO.getReplyVO();
							ReplyVO rVO = (ReplyVO)ctrl.Connect(command, work, pVO);
							rsVO.setReplyVO(rVO);
						}
						if(null!=psVO.getLogVO()) {
							ChatLogVO pVO = psVO.getLogVO();
							ChatLogVO rVO = (ChatLogVO)ctrl.Connect(command, work, pVO);
							rsVO.setLogVO(rVO);
						}
						if(null!=psVO.getAttachVO()) {
							AttachVO pVO = psVO.getAttachVO();
							AttachVO rVO = (AttachVO)ctrl.Connect(command, work, pVO);
							rsVO.setAttachVO(rVO);
						}
						send(rsVO);
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		try {
			while(!isStop) {
				String request = (String)ois.readObject();
				int protocol = 0;
				StringTokenizer st = null;
				if(request!=null) {
					st = new StringTokenizer(request,Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				server.jta_log.append("---------------------------\n");
				server.jta_log.append("[REQUEST] "+user+"->Server : "+request+"\n");
				server.jta_log.append("Time : "+new Date().toString()+"\n");
				switch(protocol) {
					case Protocol.ROOM_CREATE:{
						String userNick = st.nextToken();
						String partnerNick = st.nextToken();
						//DB상 방 생성
						ChatVO pVO = new ChatVO();
						pVO.setNick(userNick);
						pVO.setPartner_nick(partnerNick);
						ChatVO rVO = (ChatVO)ctrl.Connect("insert", "room_create", pVO);
						if(rVO.getStatus()==1) {
							server.jta_log.append("Server : DB Connection Success(ROOM_CREATE)\n");
							ServerThread sThread = null;
							boolean isThread = false;	// 상대방 스레드를 찾았는지 여부
							String response = Protocol.ROOM_CREATE
											 +Protocol.seperator+userNick
											 +Protocol.seperator+partnerNick;
							for(int i=0;i<server.serverThreadList.size();i++) {
								sThread = server.serverThreadList.get(i);
								String nick = sThread.user;
								if(nick.equals(partnerNick)) {
									send(response);
									sThread.send(response);
									server.jta_log.append("[RESPONSE] "+"Server->"+user+" : "+response+"\n");
									server.jta_log.append("[RESPONSE] "+"Server->"+partnerNick+" : "+response+"\n");
									isThread = true; // 존재하면 true 아래 if문을 무시함.
									break;
								}
							}
							if(!isThread) {	//로그아웃이면 쓰레드가 존재X -> 나만 send
								send(response);
								server.jta_log.append("[RESPONSE] "+"Server->"+user+" : "+response+"\n");
							}
						}
						else {
							server.jta_log.append("Server : DB Connection Fail(ROOM_CREATE)\n");
						}
						server.jta_log.append("---------------------------\n\n");
						server.jta_log.append("서버대기중.........\n\n");
						server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
					} break;
					case Protocol.MESSAGE:{
						int talklist_num = Integer.parseInt(st.nextToken());
						String userNick = user;
						String partnerNick = st.nextToken();
						String msg = st.nextToken();
						String emoticon = st.nextToken();
						//DB에 대화기록 insert
						ChatLogVO pVO = new ChatLogVO();
						pVO.setTalklist_num(talklist_num);
						if("none".equals(emoticon)) {
							pVO.setContent(msg);
						}
						else {
							pVO.setContent(emoticon+"|EMOTICON");
						}
						pVO.setWrite_nick(userNick);
						ChatLogVO rVO = (ChatLogVO)ctrl.Connect("insert", "talklog_ins", pVO);
						if(rVO.getStatus()==1) {
							server.jta_log.append("Server : DB Connection Success(MESSAGE)\n");
							ServerThread sThread = null;
							boolean isThread = false;
							String response = Protocol.MESSAGE
											 +Protocol.seperator+userNick
											 +Protocol.seperator+partnerNick
											 +Protocol.seperator+msg
											 +Protocol.seperator+emoticon;
							for(int i=0;i<server.serverThreadList.size();i++) {
								sThread = server.serverThreadList.get(i);
								String nick = sThread.user;
								if(nick.equals(partnerNick)) {
									send(response);
									sThread.send(response);
									server.jta_log.append("[RESPONSE] "+"Server->"+user+" : "+response+"\n");
									server.jta_log.append("[RESPONSE] "+"Server->"+partnerNick+" : "+response+"\n");
									isThread = true;
									break;
								}
							}
							if(!isThread) {	//로그아웃이면 쓰레드가 존재X -> 나만 send
								send(response);
								server.jta_log.append("[RESPONSE] "+"Server->"+user+" : "+response+"\n");
							}
						}
						else {
							server.jta_log.append("Server : DB Connection Fail(MESSAGE)\n");
						}
						server.jta_log.append("---------------------------\n\n");
						server.jta_log.append("서버대기중.........\n\n");
						server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
					} break;
					case Protocol.FILE:{
						int talklist_num = Integer.parseInt(st.nextToken());
						String userNick = user;
						String partnerNick = st.nextToken();
						String fileName = st.nextToken();
						//DB에 대화기록 insert
						ChatLogVO pVO = new ChatLogVO();
						pVO.setTalklist_num(talklist_num);
						pVO.setContent(fileName+"|FILE");
						pVO.setWrite_nick(userNick);
						ChatLogVO rVO = (ChatLogVO)ctrl.Connect("insert", "talklog_ins", pVO);
						if(rVO.getStatus()==1) {
							server.jta_log.append("Server : DB Connection Success(FILE)\n");
							ServerThread sThread = null;
							boolean isThread = false;
							String response = Protocol.FILE
											 +Protocol.seperator+userNick
											 +Protocol.seperator+partnerNick
											 +Protocol.seperator+fileName;
							for(int i=0;i<server.serverThreadList.size();i++) {
								sThread = server.serverThreadList.get(i);
								String nick = sThread.user;
								if(nick.equals(partnerNick)) {
									send(response);
									sThread.send(response);
									server.jta_log.append("[RESPONSE] "+"Server->"+user+" : "+response+"\n");
									server.jta_log.append("[RESPONSE] "+"Server->"+partnerNick+" : "+response+"\n");
									isThread = true;
									break;
								}
							}
							if(!isThread) {	//로그아웃이면 쓰레드가 존재X -> 나만 send
								send(response);
								server.jta_log.append("[RESPONSE] "+"Server->"+user+" : "+response+"\n");
							}
						}
						else {
							server.jta_log.append("Server : DB Connection Fail(FILE)\n");
						}
						server.jta_log.append("---------------------------\n\n");
						server.jta_log.append("서버대기중.........\n\n");
						server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
					} break;
					case Protocol.IMAGE:{
						int talklist_num = Integer.parseInt(st.nextToken());
						String userNick = user;
						String partnerNick = st.nextToken();
						String imgName = st.nextToken();
						//DB에 대화기록 insert
						ChatLogVO pVO = new ChatLogVO();
						pVO.setTalklist_num(talklist_num);
						pVO.setContent(imgName+"|IMAGE");
						pVO.setWrite_nick(userNick);
						ChatLogVO rVO = (ChatLogVO)ctrl.Connect("insert", "talklog_ins", pVO);
						if(rVO.getStatus()==1) {
							server.jta_log.append("Server : DB Connection Success(IMAGE)\n");
							ServerThread sThread = null;
							boolean isThread = false;
							String response = Protocol.IMAGE
									+Protocol.seperator+userNick
									+Protocol.seperator+partnerNick
									+Protocol.seperator+imgName;
							for(int i=0;i<server.serverThreadList.size();i++) {
								sThread = server.serverThreadList.get(i);
								String nick = sThread.user;
								if(nick.equals(partnerNick)) {
									send(response);
									sThread.send(response);
									server.jta_log.append("[RESPONSE] "+"Server->"+user+" : "+response+"\n");
									server.jta_log.append("[RESPONSE] "+"Server->"+partnerNick+" : "+response+"\n");
									isThread = true;
									break;
								}
							}
							if(!isThread) {	//로그아웃이면 쓰레드가 존재X -> 나만 send
								send(response);
								server.jta_log.append("[RESPONSE] "+"Server->"+user+" : "+response+"\n");
							}
						}
						else {
							server.jta_log.append("Server : DB Connection Fail(IMAGE)\n");
						}
						server.jta_log.append("---------------------------\n\n");
						server.jta_log.append("서버대기중.........\n\n");
						server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
					} break;
					case Protocol.ROOM_OUT:{
						int talklist_num = Integer.parseInt(st.nextToken());
						String userNick = user;
						String partnerNick = st.nextToken();
						String msg = st.nextToken();
						//DB에 대화기록 insert
						ChatLogVO pVO = new ChatLogVO();
						pVO.setTalklist_num(talklist_num);
						pVO.setContent(msg);
						pVO.setWrite_nick(userNick);
						ChatLogVO rVO = (ChatLogVO)ctrl.Connect("insert", "talklog_ins", pVO);
						if(rVO.getStatus()==1) {
							server.jta_log.append("Server : DB Connection Success(ROOM_OUT)\n");
							String response = Protocol.ROOM_OUT
											 +Protocol.seperator+userNick
											 +Protocol.seperator+msg;
							for(int i=0;i<server.serverThreadList.size();i++) {
								ServerThread sThread = server.serverThreadList.get(i);
								String nick = sThread.user;
								if(nick.equals(partnerNick)) {
									sThread.send(response);
									server.jta_log.append("[RESPONSE] "+"Server->"+partnerNick+" : "+response+"\n");
									break;
								}
							}
						}
						else {
							server.jta_log.append("Server : DB Connection Fail(ROOM_OUT)\n");
						}
						server.jta_log.append("---------------------------\n");
						server.jta_log.append("서버대기중.........\n");
						server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
					} break;
					case Protocol.LOGOUT:{
						send(Protocol.LOGOUT+Protocol.seperator+user);
						server.jta_log.append(user+" 로그아웃\n");
						server.jta_log.append("---------------------------\n\n");
						server.jta_log.append("서버대기중.........\n\n");
						server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
						isStop = true;
					} break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				server.serverThreadList.remove(this);
				oos.close();
				ois.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		*/
	}
}
