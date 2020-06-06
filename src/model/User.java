package model;

import java.util.ArrayList;

public class User {
    private String userName;
    private ArrayList<Post> userPosts;

    //Constructor
    public User(String userName){
        this.userName = userName;
    }

    //Getter Method for userName
    public String getUserName() {
        return userName;
    }

    //Getter and setter for userPosts
    public ArrayList<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(ArrayList<Post> userPosts) {
        this.userPosts = userPosts;
    }
}