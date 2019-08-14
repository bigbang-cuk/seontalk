package seontalk.util;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import seontalk.view.ChatRoom;

public class Emoticon extends JPanel {
	String emo[] = null;
	JButton jbtn_emo[] = new JButton[5];
	JButton jbtn_emo1 = new JButton();
	JButton jbtn_emo2 = new JButton();
	JButton jbtn_emo3 = new JButton();
	JButton jbtn_emo4 = new JButton();
	JButton jbtn_emo5 = new JButton();
	ImageIcon img[] = new ImageIcon[5];
	ChatRoom room = null;
	
	public Emoticon(ChatRoom room,String emo1,String emo2,String emo3,String emo4,String emo5) {
		this.room = room;
		this.emo = new String[]{emo1,emo2,emo3,emo4,emo5};
		this.jbtn_emo = new JButton[] {jbtn_emo1,jbtn_emo2,jbtn_emo3,jbtn_emo4,jbtn_emo5};
		init();
	}
	public void init() {
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new GridLayout(1,5));
		for(int i=0;i<jbtn_emo.length;i++) {
			img[i] = new ImageIcon(FilePath.SrcPath+emo[i]);
			jbtn_emo[i].setIcon(img[i]);
			jbtn_emo[i].setContentAreaFilled(false);
			add(jbtn_emo[i]);
		}
		initEvent();
	}
	public void initEvent() {
		jbtn_emo1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				room.emoticon = emo[0];
				try {
					room.sendMessage();
					room.jf_emo.setVisible(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		jbtn_emo2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				room.emoticon = emo[1];
				try {
					room.sendMessage();
					room.jf_emo.setVisible(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		jbtn_emo3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				room.emoticon = emo[2];
				try {
					room.sendMessage();
					room.jf_emo.setVisible(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		jbtn_emo4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				room.emoticon = emo[3];
				try {
					room.sendMessage();
					room.jf_emo.setVisible(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		jbtn_emo5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				room.emoticon = emo[4];
				try {
					room.sendMessage();
					room.jf_emo.setVisible(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
	}
}
