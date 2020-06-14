package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import model.hsql_db.SQLJdbcAdaptor;

public class Reply {
	private String replyId;
	private SimpleStringProperty postId;
	private SimpleDoubleProperty value;
	private SimpleStringProperty respId;
	private static int replyNumId = 0;
	
	//Constructor
	public Reply(String postId, double value, String respId) {
		this.postId = new SimpleStringProperty(postId);
		this.value = new SimpleDoubleProperty(value);
		this.respId = new SimpleStringProperty(respId);
		setReplyNumId(++replyNumId);
		generateReplyId();
	}

	public String getReplyId() {
		return this.replyId;
	}

	public static void setReplyNumId(int replyNumId) {
		Reply.replyNumId = replyNumId;
	}

	//This method generates Auto Sale ID
	public void generateReplyId() {
		String s1 = "REP";
		String str = String.format("%03d", replyNumId);
		String s = s1+str;
		setReplyId(s);
	}

	private void setReplyId(String s) {
		this.replyId = s;
	}

	public void saveData() {
		// Save what up there in DB
		SQLJdbcAdaptor sqlJdbcAdaptor = SQLJdbcAdaptor.getInstance();

		sqlJdbcAdaptor.insertValue(String.format(
				"INSERT INTO reply VALUE (%s, %s, %s, %s)",getReplyId(),getPostId(),getRespId(),getValue()
		));
	}
	
	//3 Getter Methods
	public String getPostId() {
		return postId.get();
	}
	
	public double getValue() {
		return value.get();
	}
	
	public String getRespId() {
		return respId.get();
	}
	
	//3 Setter Methods
	public void setPostId(String postId) {
		this.postId = new SimpleStringProperty(postId);
	}
	
	public void setValue(double val) {
		this.value = new SimpleDoubleProperty(val);
	}
	
	public void setRespId(String respId) {
		this.respId = new SimpleStringProperty(respId);
	}
}
