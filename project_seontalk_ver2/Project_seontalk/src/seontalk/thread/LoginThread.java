package seontalk.thread;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import seontalk.control.ConnectionCtrl;
import seontalk.vo.MemberVO;

public class LoginThread extends Thread{
	Server server;
	Socket client;
	ObjectOutputStream oos;
	ObjectInputStream  ois;
	BufferedOutputStream bos;
	ConnectionCtrl	   ctrl;
	boolean sThreadStart = false;
	
	public boolean isStop = false;
	
	public LoginThread (Server server) {
		this.server = server;
		client = server.client;
		ctrl = new ConnectionCtrl();
		try {
			oos = server.oos;
			ois = server.ois;
		} catch (Exception e) {
			e.printStackTrace();
		}
		server.jta_log.append("Creating Login Thread is successed\n");
		server.jta_log.append("---------------------------\n\n");
	}
	
	public void send(MemberVO rVO) {
		try {
			oos.writeObject(rVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void run() {
		server.jta_log.append("Running Login Thread\n");
		try {
			while(!isStop) {
				String request = (String)ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				String input_id = null;
				String input_pw = null;
				String result = null;
				int status = 0;
				if(request!=null) {
					server.jta_log.append("---------------------------\n");
					server.jta_log.append("Time : "+new Date().toString()+"\n");
					st = new StringTokenizer(request, Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
					case Protocol.LOGIN:{
						input_id= st.nextToken();
						input_pw = st.nextToken();
						server.jta_log.append("[REQUEST] "+input_id+"(LOGIN)\n");
						MemberVO pVO = new MemberVO();
						pVO.setId(input_id);
						pVO.setPw(input_pw);
						MemberVO rVO = (MemberVO)ctrl.Connect("update", "login", pVO);
						if(rVO!=null) {
							status = rVO.getStatus();
						}
						rVO.setProtocol(Protocol.LOGIN);
						//DB연동 성공
						if(status==1) {
							result = rVO.getKeyword();
							//로그아웃 상태 + 아이디/비번 일치 => 로그인 성공
							if("success".equals(result)) {
								List<Object> userInfo = ctrl.ConnectSelect("select", "check_id", pVO);
								rVO = (MemberVO)userInfo.get(0);
								rVO.setProtocol(Protocol.LOGIN);
								rVO.setStatus(status);
								rVO.setKeyword(result);
								send(rVO);
								server.jta_log.append("[RESPONSE] Server->Client : Success LogIn "+rVO.getNick()+"(LOGIN)\n");
								isStop = true;
								sThreadStart = true;
								break;
							}
							//로그온 상태 or 아이디/비번 불일치 => 로그인 실패
							else {
								server.jta_log.append("[RESPONSE] Server->Client : "+result+"(LOGIN)\n");
								send(rVO);
							}
						}
						//DB연동 실패
						else {
							server.jta_log.append("[RESPONSE] Server->Client : DB Connection Fail(LOGIN)\n");
							send(rVO);
						}
						server.jta_log.append("---------------------------\n\n");
						server.jta_log.append("Server Waiting.........\n\n");
						server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
					} break;
					case Protocol.EXIT:{
						server.jta_log.append("[REQUEST] Client->Server : "+client+" (EXIT)\n");
						MemberVO pVO = new MemberVO();
						pVO.setProtocol(Protocol.EXIT);
						send(pVO);
						server.jta_log.append("[RESPONSE] Server->Client : "+client+" (EXIT)\n");
						isStop = true;
					} break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				/*
				if(sThreadStart) {
					ServerThread sThread = new ServerThread(client);
					server.serverThreadList.add(sThread);
				}
				*/
				server.jta_log.append("Closing LogIn Thread is successed\n\n");
				if(sThreadStart) {
					server.sThread.start();
				}
				server.jta_log.append("---------------------------\n\n");
				server.jta_log.append("Server Waiting.........\n\n");
				server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
