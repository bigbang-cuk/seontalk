package seontalk.vo;

public class AttachVO {
	private int attach_num;
	private int post_num;
	private String files;
	private String bytes;
	
	private int status;
	private String keyword;

	public int getAttach_num() {
		return attach_num;
	}

	public void setAttach_num(int attach_num) {
		this.attach_num = attach_num;
	}

	public int getPost_num() {
		return post_num;
	}

	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getBytes() {
		return bytes;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
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
