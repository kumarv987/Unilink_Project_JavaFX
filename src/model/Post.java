package model;
import javafx.scene.image.Image;

import java.util.*;

public abstract class Post {
	private String postId;
	private String title;
	private String desc;
	//private String studId;
	private String status;
	private ArrayList<Reply> replies;
	private Image photo;
	
	//Constructor
	public Post(String title, String desc) {
		this.title = title;
		this.desc = desc;
		//this.studId = studId;
		this.status = "OPEN";
		this.replies= new ArrayList<Reply>();
		this.photo = new Image("file:images/No_image_available.svg");
	}

	public Post(String title, String desc, Image photo){
		this.title = title;
		this.desc = desc;
		this.status = "OPEN";
		this.replies=new ArrayList<>();
		this.photo = photo;
	}
	
	//5 getter methods
	public String getPostId() {
		return postId;
	}
	
	public ArrayList<Reply> getReplies() {
		return replies;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getStatus() {
		return status;
	}

	public Image getPhoto() {
		return photo;
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

	public void setImage(Image photo){
		this.photo = photo;
	}
	
	public String getPostDetails() { 
		StringBuilder str1 = new StringBuilder("Title:         " +this.title + "\n");
		StringBuilder str2 = new StringBuilder("Description:   " +this.desc + "\n");
		StringBuilder str3 = new StringBuilder("Status:        " +this.status + "\n");
		return str1.append(str2).append(str3).toString();
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