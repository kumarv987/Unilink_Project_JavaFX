package model;

import controller.MainPageController;
import javafx.beans.property.SimpleStringProperty;
import model.hsql_db.SQLJdbcAdaptor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private SimpleStringProperty userName;
    private ArrayList<Post> userPosts = new ArrayList<>();

    //Constructor
    public User(String userName){
        this.userName = new SimpleStringProperty(userName);
    }

    //Getter Method for userName
    public String getUserName() {
        return userName.get();
    }

    //Getter and setter for userPosts
    public ArrayList<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(ArrayList<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public void saveData() throws SQLException, ClassNotFoundException {
        SQLJdbcAdaptor sqlJdbcAdaptor = SQLJdbcAdaptor.getInstance();

        List<List<String>> postExist = sqlJdbcAdaptor.executeQuery(
                String.format("SELECT * from user WHERE userID='%s'", getUserName()));
        if(postExist.size() == 1) {
            String query = String.format("INSERT INTO user VALUES('%s')", getUserName());
            sqlJdbcAdaptor.insertQuery(query);
        }

        if(!(userPosts.isEmpty())){
            for(Post post : userPosts) {
                try {
                    post.saveData();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveReplies() {
        if (!(userPosts.isEmpty())) {
            for (Post post : userPosts) {
                post.saveReplies();
            }
        }
    }

    //This method adds a new post to the userPosts
    public void addPostToUserPosts(Post post) {
        this.userPosts.add(post);
    }
}