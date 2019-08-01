package seontalk.vo;

public class ReplyVO {
	private int reply_num;
	private int post_num;
	private int recommend_cnt;
	private int decommend_cnt;
	private int activation;
	private String reply_nick;
	private String reply_content;
	private String reply_date;
	private String reply_time;
	
	private int status;
	
	private String reply_id;
	private String keyword;
	public int getReply_num() {
		return reply_num;
	}
	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}
	public int getPost_num() {
		return post_num;
	}
	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}
	public int getRecommend_cnt() {
		return recommend_cnt;
	}
	public void setRecommend_cnt(int recommend_cnt) {
		this.recommend_cnt = recommend_cnt;
	}
	public int getDecommend_cnt() {
		return decommend_cnt;
	}
	public void setDecommend_cnt(int decommend_cnt) {
		this.decommend_cnt = decommend_cnt;
	}
	public int getActivation() {
		return activation;
	}
	public void setActivation(int activation) {
		this.activation = activation;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public String getReply_date() {
		return reply_date;
	}
	public void setReply_date(String reply_date) {
		this.reply_date = reply_date;
	}
	public String getReply_time() {
		return reply_time;
	}
	public void setReply_time(String reply_time) {
		this.reply_time = reply_time;
	}
	public String getReply_nick() {
		return reply_nick;
	}
	public void setReply_nick(String reply_nick) {
		this.reply_nick = reply_nick;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getReply_id() {
		return reply_id;
	}
	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
