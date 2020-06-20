package model;

import controller.MainPageController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.hsql_db.SQLJdbcAdaptor;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Reply {
	private String replyId;
	private SimpleIntegerProperty postId;
	private SimpleDoubleProperty value;
	private SimpleStringProperty respId;
	private static int replyNumId = 0;

	//Constructor
	public Reply(double value) {
		this.postId = new SimpleIntegerProperty(MainPageController.postIdReply);
		this.value = new SimpleDoubleProperty(value);
		this.respId = new SimpleStringProperty(MainPageController.currentUserName);
		setReplyNumId(++replyNumId);
		generateReplyId();
	}

	//Constructor2
	public Reply(int postIdReply, double value, String userName, String replyId) {
		this.postId = new SimpleIntegerProperty(postIdReply);
		this.value = new SimpleDoubleProperty(value);
		this.respId = new SimpleStringProperty(userName);
		setReplyNumId(++replyNumId);
		setReplyId(replyId);
	}

	public String getReplyId() {
		return this.replyId;
	}
	private void setReplyId(String s) {
		this.replyId = s;
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

	public void saveData() throws SQLException, ClassNotFoundException {
		SQLJdbcAdaptor sqlJdbcAdaptor = SQLJdbcAdaptor.getInstance();
		List<List<String>> postExist = sqlJdbcAdaptor.executeQuery(
				String.format("SELECT * from reply WHERE replyId='%s'", getReplyId())
		);

		if(postExist.size() == 1) {
			String query = String.format(
					"INSERT INTO reply VALUES('%s', %d, '%s', %s)",
					getReplyId(),
					getPostId(),
					getRespId(),
					getValue()
			);
			sqlJdbcAdaptor.insertQuery(query);
		}
	}

	//This method gets the data from the memory and saves it into the export file.
	public void writeDataToFile(FileWriter writer) throws IOException {
		writer.write("; "+getRespId()
				+", "+getValue()
				+", "+getReplyId());
	}

	//3 Getter Methods
	public int getPostId() {
		return postId.get();
	}

	public double getValue() {
		return value.get();
	}

	public String getRespId() {
		return respId.get();
	}

	//3 Setter Methods
	public void setPostId(int postId) {
		this.postId = new SimpleIntegerProperty(postId);
	}

	public void setValue(double val) {
		this.value = new SimpleDoubleProperty(val);
	}

	public void setRespId(String respId) {
		this.respId = new SimpleStringProperty(respId);
	}
}
