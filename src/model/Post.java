package model;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import model.hsql_db.SQLJdbcAdaptor;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public abstract class Post {
	private SimpleStringProperty postId;
	private SimpleStringProperty title;
	private SimpleStringProperty description;
	private SimpleStringProperty status;
	private ArrayList<Reply> replies;
	private Image photo;
	private SimpleStringProperty creatorID;
	private int postOwnId;
	private static int postSpecificID = 1000;

	//Constructor
	public Post(String title, String description, String creatorID) {
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.creatorID = new SimpleStringProperty(creatorID);
		this.status = new SimpleStringProperty("OPEN");
		this.replies= new ArrayList<Reply>();
		this.photo = new Image("file:images/No_image.png");
		postOwnId = ++postSpecificID;
		System.out.println("PostOwnID: " + postOwnId);
	}

	public Post(String title, String desc, Image photo, String creatorID){
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(desc);
		this.creatorID = new SimpleStringProperty(creatorID);
		this.status = new SimpleStringProperty("OPEN");
		this.replies=new ArrayList<>();
		this.photo = photo;
		postOwnId = ++postSpecificID;
		System.out.println("PostOwnID: " + postOwnId);
	}

	public static void setPostSpecificID(int postSpecificID) {
		Post.postSpecificID = postSpecificID;
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

	public int getPostOwnId(){return this.postOwnId;}

	//5 setter methods
	public void setPostOwnId(int postOwnId) {
		this.postOwnId = postOwnId;
	}

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
		StringBuilder str1 = new StringBuilder("Title:         " +getTitle() + "\n");
		StringBuilder str2 = new StringBuilder("Description:   " +getDescription() + "\n\n");
		//StringBuilder str3 = new StringBuilder("Status:        " +getStatus() + "\n\n");
		return str1.append(str2).toString();
	}

	//This is a dummy method so that it can be overridden by subclass method
	public String getPostDetails(String vewVal) {
		String s = "right";
		return s;
	}

	public void saveData() throws SQLException, ClassNotFoundException {
		SQLJdbcAdaptor sqlJdbcAdaptor = SQLJdbcAdaptor.getInstance();

		List<List<String>> postExist = sqlJdbcAdaptor.executeQuery(
				String.format("SELECT * from posts WHERE postOwnID=%d", postOwnId)
		);

		if(postExist.size() == 1) {
			String query = String.format(
					"INSERT INTO posts VALUES(%d, '%s', '%s', '%s', '%s', '%s', '%s')",
					postOwnId,
					getPostId().charAt(0),
					getTitle(),
					getDescription(),
					getStatus(),
					getPhoto(),
					getCreatorID()
			);
			System.out.println("Inside Post");
			sqlJdbcAdaptor.insertQuery(query);
		}

	}

	public void saveReplies() {
		if (!replies.isEmpty()) {
			for (Reply reply: replies) {
				try {
					reply.saveData();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//This method gets the data from the memory and saves it into the export file.
	public void writeDataToFile(FileWriter writer) throws IOException {
		writer.write("; "+getPostId()
					+", "+getTitle()
					+", "+getDescription()
					+", "+getStatus()
					+", "+getPhoto().toString());
	}

	//This method writes replies to the file
	public void writeRepliesToFile(FileWriter writer) throws IOException{
		if(!replies.isEmpty()){
			for(Reply reply: replies){
				reply.writeDataToFile(writer);
			}
		}
	}

	public abstract int handleReply(Reply reply);
	public abstract String getReplyDetails();
	public abstract void setId(String eId, int pId);

	public void getData() throws SQLException, ClassNotFoundException {
		SQLJdbcAdaptor sqlJdbcAdaptor = SQLJdbcAdaptor.getInstance();
		List<List<String>> result = sqlJdbcAdaptor.executeQuery(
				String.format("SELECT * FROM reply WHERE postOwnID=%s", getPostOwnId())
		);

		if(result.size() > 1) {
			for(int i = 1; i < result.size(); i++) {
				List<String> res = result.get(i);
				Reply reply = new Reply(
					Integer.parseInt(res.get(1)),
					Double.parseDouble(res.get(3)),
					res.get(2),
					res.get(0)
				);
				replies.add(reply);
			}
		}
	}
}