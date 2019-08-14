package seontalk.thread;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Server extends JFrame implements Runnable {
	
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log);
	
	ServerSocket server;
	Socket client;
	LoginThread lThread;
	ServerThread sThread;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	List<ServerThread> serverThreadList = null;
	boolean isStop = false;
	
	public Server() {
		init();
	}
	public void init() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					if(server !=null) isStop = true;
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Font font = new Font("새굴림",Font.PLAIN,16);
		jta_log.setFont(font);
		jta_log.setEditable(false);
		jta_log.setBackground(Color.BLACK);
		jta_log.setForeground(Color.WHITE);
		add(jsp_log);
		setTitle("선톡 - 서버로그");
		setSize(600, 700);
		setVisible(true);
	}

	public static void main(String[] args) {
		Server ts = new Server();
		new Thread(ts).start();
	}

	@Override
	public void run() {
		serverThreadList = new Vector<ServerThread>();
		try {
			server = new ServerSocket(9999);
			while(!isStop) {
				jta_log.append("Server Waiting.........\n\n");
				client = server.accept();
				jta_log.append("------------[Client Access]------------\n");
				jta_log.append("Time:"+new Date().toString()+"\n");
				jta_log.append(client.toString()+"\n");
				oos = new ObjectOutputStream(client.getOutputStream());
				ois = new ObjectInputStream(client.getInputStream());
				sThread = new ServerThread(this);
				lThread = new LoginThread(this);
				lThread.start();
				jta_log.setCaretPosition(jta_log.getDocument().getLength());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
}