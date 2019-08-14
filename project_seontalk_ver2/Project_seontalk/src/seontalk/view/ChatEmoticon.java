package seontalk.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import seontalk.util.Emoticon;

public class ChatEmoticon extends JFrame{
	JTabbedPane jtp_emo = null;
	JPanel		jp_emo1 = null;
	JPanel		jp_emo2 = null;
	JPanel		jp_emo3 = null;
	JPanel		jp_emo4 = null;
	JPanel		jp_emo5 = null;
	
	MainPage page = null;
	ChatRoom room = null;
	
	public ChatEmoticon(MainPage page,ChatRoom room) {
		this.page = page;
		this.room = room;
		init();
	}
	public void init() {
		jp_emo1 = new Emoticon(room,"gude_1.png","gude_2.png","gude_3.png","gude_4.png","gude_5.png");
		jp_emo2 = new Emoticon(room,"ryan_1.png","ryan_2.png","ryan_3.png","ryan_4.png","ryan_5.png");
		jp_emo3 = new Emoticon(room,"assa_1.png","assa_2.png","assa_3.png","assa_4.png","assa_5.png");
		jp_emo4 = new Emoticon(room,"kat1.gif","kat2.gif","kat3.gif","kat4.gif","kat5.gif");
//		jp_emo5 = new Emoticon(room,"","","","","");
		
		jtp_emo = new JTabbedPane();
		jtp_emo.add("구데타마", jp_emo1);
		jtp_emo.add("라이언", jp_emo2);
		jtp_emo.add("아싸!", jp_emo3);
		jtp_emo.add("emo4", jp_emo4);
//		jtp_emo.add("emo5", jp_emo5);
		
		add(jtp_emo);
		setTitle("이모티콘 선택");
		setSize(600,200);
		setResizable(false);
		setVisible(false);
		
	}
}
