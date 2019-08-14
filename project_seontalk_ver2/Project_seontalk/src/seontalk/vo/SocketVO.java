package seontalk.vo;

import java.io.Serializable;
import java.util.List;

public class SocketVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Object> memberList;
	private List<Object> chatList;
	private List<Object> postList;
	private List<Object> replyList;
	private List<Object> chatLogList;
	private List<Object> attachList;
	private int protocol;
	private String command;
	private String work;
	private MemberVO memVO;
	private ChatVO chatVO;
	private PostVO postVO;
	private ReplyVO replyVO;
	private ChatLogVO logVO;
	private AttachVO attachVO;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public MemberVO getMemVO() {
		return memVO;
	}
	public void setMemVO(MemberVO memVO) {
		this.memVO = memVO;
	}
	public ChatVO getChatVO() {
		return chatVO;
	}
	public void setChatVO(ChatVO chatVO) {
		this.chatVO = chatVO;
	}
	public PostVO getPostVO() {
		return postVO;
	}
	public void setPostVO(PostVO postVO) {
		this.postVO = postVO;
	}
	public ReplyVO getReplyVO() {
		return replyVO;
	}
	public void setReplyVO(ReplyVO replyVO) {
		this.replyVO = replyVO;
	}
	public ChatLogVO getLogVO() {
		return logVO;
	}
	public void setLogVO(ChatLogVO logVO) {
		this.logVO = logVO;
	}
	public AttachVO getAttachVO() {
		return attachVO;
	}
	public void setAttachVO(AttachVO attachVO) {
		this.attachVO = attachVO;
	}
	public List<Object> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<Object> memberList) {
		this.memberList = memberList;
	}
	public List<Object> getChatList() {
		return chatList;
	}
	public void setChatList(List<Object> chatList) {
		this.chatList = chatList;
	}
	public List<Object> getPostList() {
		return postList;
	}
	public void setPostList(List<Object> postList) {
		this.postList = postList;
	}
	public List<Object> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Object> replyList) {
		this.replyList = replyList;
	}
	public List<Object> getChatLogList() {
		return chatLogList;
	}
	public void setChatLogList(List<Object> chatLogList) {
		this.chatLogList = chatLogList;
	}
	public List<Object> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<Object> attachList) {
		this.attachList = attachList;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

}
