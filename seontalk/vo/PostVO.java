package seontalk.vo;

public class PostVO {
	private int post_num;
	private int view_cnt;
	private int recommend_cnt;
	private int decommend_cnt;
	private int reply_cnt;
	private int activation;
	private String id;
	private String nick;
	private String post_title;
	private String post_content;
	private String post_date;
	private String post_time;
	private String interest;
	
	private int status;
	
	private String column;
	private String keyword;
	public int getPost_num() {
		return post_num;
	}
	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}
	public int getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
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
	public int getReply_cnt() {
		return reply_cnt;
	}
	public void setReply_cnt(int reply_cnt) {
		this.reply_cnt = reply_cnt;
	}
	public int getActivation() {
		return activation;
	}
	public void setActivation(int activation) {
		this.activation = activation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	public String getPost_date() {
		return post_date;
	}
	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}
	public String getPost_time() {
		return post_time;
	}
	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
