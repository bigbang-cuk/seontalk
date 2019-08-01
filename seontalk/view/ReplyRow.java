package seontalk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import seontalk.util.FilePath;
import seontalk.util.Theme;
import seontalk.vo.MemberVO;
import seontalk.vo.ReplyVO;

public class ReplyRow extends JPanel {
	JLabel		jlb_comment	= null;
	JLabel		jlb_nick	= null;
	JLabel		jlb_date	= null;
	JButton		jbtn_up		= null;
	JButton 	jbtn_down	= null;
	JButton 	jbtn_update	= null;
	JButton 	jbtn_delete	= null;
	
	MainPage page;
	Theme theme = new Theme();
	ReplyVO  reply = null;
	MemberVO user = null;
	ProfileImg  jp_profile = null;
	ProfilePage pp = null;
	public ReplyRow(MainPage page,ReplyVO reply) {
		this.page = page;
		this.reply = reply;
		getProfile();
		init();
	}
	public void getProfile() {
		MemberVO pVO = new MemberVO();
		pVO.setNick(reply.getReply_nick());
		List<Object> list = page.ctrl.ConnectSelect("select", "check_nick", pVO);
		user = (MemberVO)list.get(0);
		jp_profile = new ProfileImg(user.getProfile_img(), 0, 0, 40, 40);
	}
	public void init() {
		initLabel();
		initButton();
		initGroup();
		initEvent();
		setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
	}
	public void initLabel() {
		Font font_nick  = new Font(page.memVO.getFont(),Font.BOLD,10);
		Font font_etc = new Font(page.memVO.getFont(),Font.PLAIN,9);
		if(1==reply.getActivation()) {
			jlb_comment	= new JLabel("해당 댓글은 삭제되었습니다");
			jlb_comment.setForeground(Color.GRAY);
		}
		else {
			jlb_comment	= new JLabel(reply.getReply_content());
		}
		jlb_nick	= new JLabel(user.getNick());
		jlb_date	= new JLabel(reply.getReply_date()+" "+reply.getReply_time()
							, new ImageIcon(FilePath.SrcPath+"clock03.png")
							, SwingConstants.LEFT);
		jlb_comment.setFont(font_etc);
		jlb_nick.setFont(font_nick);
		jlb_date.setFont(font_etc);
		jlb_comment.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_nick.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_date.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jlb_comment.setOpaque(true);
		jlb_nick.setOpaque(true);
		jlb_date.setOpaque(true);
		jlb_comment.setVerticalAlignment(SwingConstants.CENTER);
		jlb_nick.setVerticalAlignment(SwingConstants.CENTER);
		jlb_date.setVerticalAlignment(SwingConstants.CENTER);
		Border border = jlb_comment.getBorder();
		Border margin = new EmptyBorder(5,10,5,10);
		jlb_comment.setBorder(new CompoundBorder(border, margin));
				
	}
	public void initButton() {
		jbtn_up		= new JButton(String.valueOf(reply.getRecommend_cnt())
								,new ImageIcon(FilePath.SrcPath+"good03.png"));
		jbtn_down	= new JButton(String.valueOf(reply.getDecommend_cnt())
								,new ImageIcon(FilePath.SrcPath+"bad03.png"));
		jbtn_update	= new JButton(new ImageIcon(FilePath.SrcPath+"update03.png"));
		jbtn_delete	= new JButton(new ImageIcon(FilePath.SrcPath+"delete02.png"));
		jbtn_up.setRolloverEnabled(false);
		jbtn_down.setRolloverEnabled(false);
		jbtn_update.setRolloverEnabled(false);
		jbtn_delete.setRolloverEnabled(false);
		jbtn_up.setFocusable(false);
		jbtn_down.setFocusable(false);
		jbtn_update.setFocusable(false);
		jbtn_delete.setFocusable(false);
		jbtn_up.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_down.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_update.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_delete.setBackground(theme.setBackgroundColor(page.memVO.getTheme()));
		jbtn_up.setOpaque(true);
		jbtn_down.setOpaque(true);
		jbtn_update.setOpaque(true);
		jbtn_delete.setOpaque(true);
		jbtn_up.setBorder(null);
		jbtn_down.setBorder(null);
		jbtn_update.setBorder(null);
		jbtn_delete.setBorder(null);
		Font font_etc = new Font(page.memVO.getFont(),Font.PLAIN,9);
		jbtn_up.setFont(font_etc);
		jbtn_down.setFont(font_etc);
		jbtn_update.setFont(font_etc);
		jbtn_delete.setFont(font_etc);
	}
	public void initGroup() {
		if(reply.getReply_nick().equals(page.memVO.getNick())) {
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addComponent(jp_profile,40,40,40)
						.addComponent(jlb_nick,280,280,280)
						.addComponent(jlb_date,180,180,180)
					)
					.addGroup(layout.createSequentialGroup()
						.addComponent(jlb_comment,300,300,300)
						.addComponent(jbtn_update,40,40,40)
						.addComponent(jbtn_delete,40,40,40)
						.addComponent(jbtn_up,50,50,50)
						.addComponent(jbtn_down,50,50,50)
					)
				)
			);
			layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jp_profile,40,40,40)
					.addComponent(jlb_nick,40,40,40)
					.addComponent(jlb_date,40,40,40)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jlb_comment,30,30,30)
					.addComponent(jbtn_update,30,30,30)
					.addComponent(jbtn_delete,30,30,30)
					.addComponent(jbtn_up,30,30,30)
					.addComponent(jbtn_down,30,30,30)
				)
			);
		}
		else {
			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addComponent(jp_profile,40,40,40)
						.addComponent(jlb_nick,280,280,280)
						.addComponent(jlb_date,180,180,180)
					)
					.addGroup(layout.createSequentialGroup()
						.addComponent(jlb_comment,380,380,380)
						.addComponent(jbtn_up,50,50,50)
						.addComponent(jbtn_down,50,50,50)
					)
				)
			);
			layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jp_profile,40,40,40)
					.addComponent(jlb_nick,40,40,40)
					.addComponent(jlb_date,40,40,40)
				)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(jlb_comment,30,30,30)
					.addComponent(jbtn_up,30,30,30)
					.addComponent(jbtn_down,30,30,30)
				)
			);
		}
	}
	public void initEvent() {
		jlb_nick.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i=0;i<page.ProfileList.size();i++) {
					ProfilePage pp = page.ProfileList.get(i);
					if(pp.user.getNick().equals(user.getNick())) {
						pp.setVisible(false);
						page.ProfileList.remove(i);
					}
				}
				pp = new ProfilePage(page,user);
				page.ProfileList.add(pp);
				super.mouseClicked(e);
			}
		});
		jbtn_update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String comment = JOptionPane.showInputDialog(ReplyRow.this
						, "댓글을 입력하세요."
						, "댓글 수정", JOptionPane.NO_OPTION);
				if(comment!=null) {
					ReplyVO pVO = new ReplyVO();
					pVO.setPost_num(reply.getPost_num());
					pVO.setReply_num(reply.getReply_num());
					pVO.setReply_content(comment);
					ReplyVO rVO = null;
					rVO = (ReplyVO)page.ctrl.Connect("update", "reply_upd", pVO);
					if(rVO.getStatus()==1) {
						JOptionPane.showMessageDialog(ReplyRow.this, "댓글이 수정되었습니다");
					}
					else {
						JOptionPane.showMessageDialog(ReplyRow.this, "댓글 수정 실패");
					}
				}
			}
		});
		jbtn_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(ReplyRow.this, "해당 댓글을 삭제하시겠습니까?"
									, "댓글 삭제", JOptionPane.YES_NO_OPTION);
				if(result==JOptionPane.OK_OPTION) {
					ReplyVO rVO = null;
					rVO = (ReplyVO)page.ctrl.Connect("delete", "reply_del", reply);
					if(rVO.getStatus()==1) {
						JOptionPane.showMessageDialog(ReplyRow.this, "댓글이 삭제되었습니다");
					}
					else {
						JOptionPane.showMessageDialog(ReplyRow.this, "댓글 삭제 실패");
					}
				}
			}
		});
		jbtn_up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reply.setReply_id(user.getId());
				reply.setKeyword(page.memVO.getNick());
				ReplyVO rVO = null;
				rVO = (ReplyVO)page.ctrl.Connect("update", "reply_up", reply);
				if(rVO.getStatus()==1) {
					if("내댓글은 추천할 수 없습니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(ReplyRow.this, "내댓글은 추천할 수 없습니다.");
					}
					else if("해당 댓글이 추천되었습니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(ReplyRow.this, "해당 댓글이 추천되었습니다.");
					}
					else if("이미 추천한 댓글입니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(ReplyRow.this, "이미 추천한 댓글입니다.");
					}
				}
				else {
					JOptionPane.showMessageDialog(ReplyRow.this, "DB Connenction Error");
				}
			}
		});
		jbtn_down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reply.setReply_id(user.getId());
				reply.setKeyword(page.memVO.getNick());
				ReplyVO rVO = null;
				rVO = (ReplyVO)page.ctrl.Connect("update", "reply_down", reply);
				if(rVO.getStatus()==1) {
					if("내댓글은 비추천할 수 없습니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(ReplyRow.this, "내댓글은 비추천할 수 없습니다.");
					}
					else if("해당 댓글이 비추천되었습니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(ReplyRow.this, "해당 댓글이 비추천되었습니다.");
					}
					else if("이미 비추천한 댓글입니다.".equals(rVO.getKeyword())) {
						JOptionPane.showMessageDialog(ReplyRow.this, "이미 비추천한 댓글입니다.");
					}
				}
				else {
					JOptionPane.showMessageDialog(ReplyRow.this, "DB Connenction Error");
				}
			}
		});
	}
}
