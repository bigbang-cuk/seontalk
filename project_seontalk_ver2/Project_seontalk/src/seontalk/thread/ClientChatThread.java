package seontalk.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

import seontalk.view.MainPage;

public class ClientChatThread extends Thread {
	MainPage page;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	public boolean isStop = false;
	public ClientChatThread(MainPage page) {
		this.page = page;
		this.oos = page.coos;
		this.ois = page.cois;
	}
	@Override
	public void run() {
		while(!isStop) {
			String response = null;
			StringTokenizer st = null;
			int protocol = 0;
			try {
				response = (String)ois.readObject();
				if(response!=null) {
					st = new StringTokenizer(response, Protocol.seperator); 
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
