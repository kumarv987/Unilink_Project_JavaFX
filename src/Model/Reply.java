package Model;

public class Reply {
	private String postId;
	private double value;
	private String respId;
	
	//Constructor
	public Reply(String postId, double value, String respId) {
		this.postId = postId;
		this.value = value;
		this.respId = respId;
	}
	
	//3 Getter Methods
	public String getPostId() {
		return postId;
	}
	
	public double getValue() {
		return value;
	}
	
	public String getRespId() {
		return respId;
	}
	
	//3 Setter Methods
	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	public void setValue(double val) {
		value = val;
	}
	
	public void setRespId(String respId) {
		this.respId = respId;
	}
}
