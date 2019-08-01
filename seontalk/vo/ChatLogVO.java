package seontalk.vo;

public class ChatLogVO {
	private int talklist_num;
	private int log_num;   
	private String log_date;    
	private String log_time;    
	private String content;    
	private int confirm;    
	private String write_nick;
	
	private int status;
	public int getTalklist_num() {
		return talklist_num;
	}
	public void setTalklist_num(int talklist_num) {
		this.talklist_num = talklist_num;
	}
	public int getLog_num() {
		return log_num;
	}
	public void setLog_num(int log_num) {
		this.log_num = log_num;
	}
	public String getLog_date() {
		return log_date;
	}
	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}
	public String getLog_time() {
		return log_time;
	}
	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getConfirm() {
		return confirm;
	}
	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getWrite_nick() {
		return write_nick;
	}
	public void setWrite_nick(String write_nick) {
		this.write_nick = write_nick;
	}
}
