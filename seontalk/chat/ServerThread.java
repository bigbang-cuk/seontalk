package seontalk.chat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import seontalk.control.ConnectionCtrl;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;


public class ServerThread extends Thread{
	Server server = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	ConnectionCtrl ctrl = new ConnectionCtrl();
	String user = "";
	public ServerThread(Server server) {
		this.server = server;
		try {
			oos = new ObjectOutputStream(server.client.getOutputStream());
			ois = new ObjectInputStream(server.client.getInputStream());
			String msg = ois.readObject().toString();
			StringTokenizer st = null;
			if(msg!=null) {
				st = new StringTokenizer(msg,Protocol.seperator);
			}
			if(st.hasMoreTokens()) {
				st.nextToken();
				user = st.nextToken();
			}
			server.jta_log.append(user+" 로그인\n");
			server.serverThreadList.add(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public synchronized void run() {
		boolean isStop = false;
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
		
	}

}
