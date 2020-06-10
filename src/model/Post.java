package model;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.util.*;

public abstract class Post {
	private SimpleStringProperty postId;
	private SimpleStringProperty title;
	private SimpleStringProperty description;
	private SimpleStringProperty status;
	private ArrayList<Reply> replies;
	private Image photo;
	private SimpleStringProperty creatorID;
	
	//Constructor
	public Post(String title, String description, String creatorID) {
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.creatorID = new SimpleStringProperty(creatorID);
		this.status = new SimpleStringProperty("OPEN");
		this.replies= new ArrayList<Reply>();
		this.photo = new Image("file:images/No_image.png");
	}

	public Post(String title, String desc, Image photo, String creatorID){
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(desc);
		this.creatorID = new SimpleStringProperty(creatorID);
		this.status = new SimpleStringProperty("OPEN");
		this.replies=new ArrayList<>();
		this.photo = photo;
	}
	
	//5 getter methods
	public String getPostId() {
		return postId.get();
	}
	
	public ArrayList<Reply> getReplies() {
		return replies;
	}
	
	public String getTitle() {
		return title.get();
	}
	
	public String getStatus() {
		return status.get();
	}

	public Image getPhoto() {
		return photo;
	}

	public String getDescription() {
		return description.get();
	}

	public String getCreatorID(){
		return creatorID.get();
	}
	
	//5 setter methods
	public void setPostId(String postId) {
		this.postId = new SimpleStringProperty(postId);
	}
	
	public void setReplies(Reply reply) {
		this.replies.add(reply);
	}
	
	public void setStatus(String s) {
		this.status = new SimpleStringProperty(s);
	}

	public void setPhoto(Image photo){
		this.photo = photo;
	}

	public void setTitle(String name) {
		this.title = new SimpleStringProperty(name);
	}

	public void setCreatorID(String creatorID){
		this.creatorID = new SimpleStringProperty(creatorID);
	}

	public void setDescription(String description){
		this.description = new SimpleStringProperty(description);
	}

	public String getPostDetails() { 
		StringBuilder str1 = new StringBuilder("Title:         " +getTitle() + "\n\n");
		StringBuilder str2 = new StringBuilder("Description:   " +getDescription() + "\n\n");
		//StringBuilder str3 = new StringBuilder("Status:        " +getStatus() + "\n\n");
		return str1.append(str2).toString();
	}
	
	//This is a dummy method so that it can be overridden by subclass method
	public String getPostDetails(String vewVal) {
		String s = "right";
		return s;
	}
	
	public abstract int handleReply(Reply reply);
	public abstract String getReplyDetails();
	public abstract void printType();
	public abstract boolean responseChecker(String resp);
}