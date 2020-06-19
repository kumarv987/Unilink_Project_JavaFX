package model;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class User {
    private SimpleStringProperty userName;
    private ArrayList<Post> userPosts = new ArrayList<>();
    private static int postSpecificID = 1000;

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


    //This method adds a new post to the userPosts
    public void addPostToUserPosts(Post post){
        post.setPostOwnId(postSpecificID);
        this.userPosts.add(post);
        postSpecificID++;
    }
}