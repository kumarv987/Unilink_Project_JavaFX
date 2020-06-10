package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Reply {
	private SimpleStringProperty postId;
	private SimpleDoubleProperty value;
	private SimpleStringProperty respId;
	
	//Constructor
	public Reply(String postId, double value, String respId) {
		this.postId = new SimpleStringProperty(postId);
		this.value = new SimpleDoubleProperty(value);
		this.respId = new SimpleStringProperty(respId);
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
