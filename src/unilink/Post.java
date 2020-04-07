package unilink;
import java.util.*;

public abstract class Post {
	private String postId;
	private String title;
	private String desc;
	private String studId;
	private String status;
	private ArrayList<Reply> replies;
	
	//Constructor
	public Post(String title, String desc, String studId) {
		this.title = title;
		this.desc = desc;
		this.studId = studId;
		this.status = "OPEN";
		this.replies= new ArrayList<Reply>();
	}
	
	//5 getter methods
	public String getPostId() {
		return postId;
	}
	
	public ArrayList<Reply> getReplies() {
		return replies;
	}
	
	public String getStudId() {
		return studId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getStatus() {
		return status;
	}
	
	//3 setter methods
	public void setPostId(String postId) {
		this.postId = postId;
	}
	
	public void setReplies(Reply reply) {
		this.replies.add(reply);
	}
	
	public void setStatus(String s) {
		this.status = s;
	}
	
	public String getPostDetails() { 
		StringBuilder str1 = new StringBuilder("Title:         " +this.title + "\n");
		StringBuilder str2 = new StringBuilder("Description:   " +this.desc + "\n");
		StringBuilder str3 = new StringBuilder("Creator ID:    " +this.studId + "\n");
		StringBuilder str4 = new StringBuilder("Status:        " +this.status + "\n");
		return str1.append(str2).append(str3).append(str4).toString();
	}
	
	//This is a dummy method so that it can be overridden by subclass method
	public String getPostDetails(String vewVal) {
		String s = "right";
		return s;
	}
	
	public abstract boolean handleReply(Reply reply);
	public abstract String getReplyDetails();
	public abstract void printType();
	public abstract boolean responseChecker(String resp);
}