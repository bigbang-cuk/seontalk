package seontalk.vo;

public class ChatVO {
	private int talklist_num;
	private String nick;
	private String open_date;
	private String partner_nick;
	private String my_inout;
	private String partner_inout;
	private int my_reading;
	private int partner_reading;
	
	private int status;
	public int getTalklist_num() {
		return talklist_num;
	}
	public void setTalklist_num(int talklist_num) {
		this.talklist_num = talklist_num;
	}
	public String getOpen_date() {
		return open_date;
	}
	public void setOpen_date(String open_date) {
		this.open_date = open_date;
	}
	public String getMy_inout() {
		return my_inout;
	}
	public void setMy_inout(String my_inout) {
		this.my_inout = my_inout;
	}
	public String getPartner_inout() {
		return partner_inout;
	}
	public void setPartner_inout(String partner_inout) {
		this.partner_inout = partner_inout;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPartner_nick() {
		return partner_nick;
	}
	public void setPartner_nick(String partner_nick) {
		this.partner_nick = partner_nick;
	}
	public int getMy_reading() {
		return my_reading;
	}
	public void setMy_reading(int my_reading) {
		this.my_reading = my_reading;
	}
	public int getPartner_reading() {
		return partner_reading;
	}
	public void setPartner_reading(int partner_reading) {
		this.partner_reading = partner_reading;
	}
	
}
