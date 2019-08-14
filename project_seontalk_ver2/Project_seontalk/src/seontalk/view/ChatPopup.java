package seontalk.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import seontalk.util.Theme;
import seontalk.vo.ChatVO;

public class ChatPopup extends JDialog {
	JLabel 	jlb_msg = null;
	JButton jbtn_confirm = null;
	
	MainPage page;
	Theme theme = new Theme();
	ChatRoom room = null;
	
	public ChatPopup(MainPage page,ChatRoom room) {
		this.page = page;
		this.room = room;
		init();
	}
	public void init() {
		setTitle("선톡 - "+room.user.getNick());
		setLayout(null);
		setSize(300,120);
		initLabel();
		initButton();
		initEvent();
		initLocation();
		setResizable(false);
		if("on".equals(page.memVO.getAlarm())) {
			setVisible(true);
		}
		else {
			setVisible(false);
		}
	}
	public void initLabel() {
		Font font = new Font(page.memVO.getFont(),Font.PLAIN,12);
		jlb_msg = new JLabel("새로운 메세지가 도착했습니다.");
		jlb_msg.setFont(font);
		jlb_msg.setHorizontalAlignment(SwingConstants.CENTER);
		jlb_msg.setVerticalAlignment(SwingConstants.CENTER);
		jlb_msg.setBounds(50, 20, 200, 20);
		add(jlb_msg);
	}
	public void initButton(){
		Font font = new Font(page.memVO.getFont(),Font.PLAIN,10);
		jbtn_confirm = new JButton("확 인");
		jbtn_confirm.setFont(font);
		jbtn_confirm.setHorizontalAlignment(SwingConstants.CENTER);
		jbtn_confirm.setVerticalAlignment(SwingConstants.CENTER);
		jbtn_confirm.setFocusable(false);
		jbtn_confirm.setBounds(120,50,80,20);
		add(jbtn_confirm);
	}
	public void initLocation() {
		Dimension monitor = Toolkit.getDefaultToolkit().getScreenSize();
		int x = monitor.width - this.getWidth();
		int y = monitor.height - this.getHeight() - 45;
		setLocation(x, y);
	}
	public void initEvent() {
		jbtn_confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatVO pVO = new ChatVO();
				pVO.setTalklist_num(room.chat.getTalklist_num());
				pVO.setNick(page.memVO.getNick());
				ChatVO rVO = (ChatVO)page.ctrl.Connect("update", "reading", pVO);
				if(1==rVO.getStatus()) {
					room.initLocation();
					room.setVisible(true);
					room.jsp_chat.getVerticalScrollBar().setValue(room.jsp_chat.getVerticalScrollBar().getMaximum());
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "DB Connection Error");
					dispose();
				}
			}
		});
	}
}
