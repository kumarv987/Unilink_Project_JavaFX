package model;

import controller.MainPageController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import model.hsql_db.SQLJdbcAdaptor;

import java.io.FileNotFoundException;
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

    public void getData() {
        SQLJdbcAdaptor sqlJdbcAdaptor = SQLJdbcAdaptor.getInstance();
        try {
            List<List<String>> result = sqlJdbcAdaptor.executeQuery(
                String.format("SELECT * FROM posts WHERE userID='%s'", getUserName())
            );

            if(result.size() > 1) {
                for(List<String> val: result) {
                    List<List<String>> eventsResult;
                    Post post = null;

                    if(val.get(1).equals("E")) {
                        eventsResult = sqlJdbcAdaptor.executeQuery(
                            String.format("SELECT * FROM event WHERE postOwnID=%s", val.get(0))
                        );

                        System.out.println(eventsResult);
                        if(eventsResult.size() > 1) {
                            List<String> eventRow = eventsResult.get(1);
                            post = new Event(
                                    val.get(2),
                                    val.get(3),
                                    eventRow.get(1),
                                    eventRow.get(2),
                                    Integer.parseInt(eventRow.get(3)),
                                    getUserName()
                            );
                            post.setId(eventRow.get(5), Integer.parseInt(val.get(0)));
                        }
                    } else if(val.get(1).equals("S")) {
                        eventsResult = sqlJdbcAdaptor.executeQuery(
                                String.format("SELECT * FROM sale WHERE postOwnID=%s", val.get(0))
                        );
                        List<String> eventRow = eventsResult.get(1);
                        if(eventsResult.size() > 1) {
                            post = new Sale(
                                    val.get(2),
                                    val.get(3),
                                    Double.parseDouble(eventRow.get(1)),
                                    Double.parseDouble(eventRow.get(3)),
                                    getUserName()
                            );
                            post.setId(eventRow.get(4), Integer.parseInt(val.get(0)));
                        }
                    } else if(val.get(1).equals("J")) {
                        eventsResult = sqlJdbcAdaptor.executeQuery(
                                String.format("SELECT * FROM job WHERE postOwnID=%s", val.get(0))
                        );
                        if(eventsResult.size() > 1) {
                            List<String> eventRow = eventsResult.get(1);
                            post = new Job(
                                    val.get(2),
                                    val.get(3),
                                    Double.parseDouble(eventRow.get(2)),
                                    getUserName()
                            );
                            post.setId(eventRow.get(1), Integer.parseInt(val.get(0)));
                        }
                    }
                    if(post != null) {
                        post.getData();
                        userPosts.add(post);
                    }
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}