package seontalk.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

public class ServerChatThread extends Thread {
	Server server;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	public boolean isStop = false;
	public ServerChatThread(Server server,ObjectOutputStream oos,ObjectInputStream ois) {
		this.server = server;
		this.oos = oos;
		this.ois = ois;
	}
	public void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		String request = null;
		StringTokenizer st = null;
		int protocol = 0;
		while(!isStop) {
			try {
				request = (String)ois.readObject();
				if(request!=null) {
					st = new StringTokenizer(request, Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
					case Protocol.MESSAGE:{
						
					} break;
					case Protocol.FILE:{
						
					} break;
					case Protocol.IMAGE:{
						
					} break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
