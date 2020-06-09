package controller;

import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Event;
import model.Post;
import model.User;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    //Variables for accessing model package info
    public static final ArrayList<User> listOfUsers = new ArrayList<>();
    public static String currentUserName;

    //Variables for FXML file
    @FXML private Button moreDetailsButton;
    @FXML private Button replyButton;
    @FXML private Button newEventPostButton;
    @FXML private Button newSalePostButton;
    @FXML private Button newJobPostButton;
    @FXML private Button logOutButton;
    @FXML private Label currentUserLabel;
    @FXML private Label postSpecificLabel;
    @FXML private TableView<Post> tableView;
    @FXML private TableColumn<Post,String> postIDColumn;
    @FXML private TableColumn<Post,String> titleColumn;
    @FXML private TableColumn<Post,String> descriptionColumn;
    @FXML private TableColumn<Post,String> statusColumn;
    @FXML private TableColumn<Post,String> creatorIDColumn;
    @FXML private ImageView postImage;
    //@FXML private TableColumn<Post, Image> imageColumn;


    /*******************************************************************************************************************
     * This method Creates a new event post.
     ******************************************************************************************************************/
    public void eventPostButtonPushed(ActionEvent event) throws IOException {
        //Now loading the new stage
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/view/NewEventPost.fxml"));
        Scene mainWindowScene = new Scene(mainWindowParent);

        //This line get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("New Event Post");
        window.setScene(mainWindowScene);
        window.show();
    }

    /*******************************************************************************************************************
     * This method Creates a new event post.
     ******************************************************************************************************************/
    public void salePostButtonPushed(ActionEvent event) throws IOException {
        //Now loading the new stage
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/view/NewSalePost.fxml"));
        Scene mainWindowScene = new Scene(mainWindowParent);

        //This line get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("New Sale Post");
        window.setScene(mainWindowScene);
        window.show();
    }

    /*******************************************************************************************************************
     * This method Creates a new event post.
     ******************************************************************************************************************/
    public void jobPostButtonPushed(ActionEvent event) throws IOException {
        //Now loading the new stage
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/view/NewJobPost.fxml"));
        Scene mainWindowScene = new Scene(mainWindowParent);

        //This line get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("New Job Post");
        window.setScene(mainWindowScene);
        window.show();
    }

    /*******************************************************************************************************************
     * This method logouts the user and goes back to Login page
     ******************************************************************************************************************/
    public void logOutButtonPushed(ActionEvent event) throws IOException {
        //Now loading the new stage
        Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        Scene scene = new Scene(parent);

        //This line get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Login");
        window.setScene(scene);
        window.show();
    }

    /*******************************************************************************************************************
     * This method takes the user to the Post detail window and shows more details of the post they created.
     ******************************************************************************************************************/
    public void moreDetailsButtonPushed(ActionEvent event) throws IOException {
        //Now loading the new stage
        Parent parent = FXMLLoader.load(getClass().getResource("/view/PostDetail.fxml"));
        Scene scene = new Scene(parent);

        //This line get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Post Window");
        window.setScene(scene);
        window.show();
    }

    /*******************************************************************************************************************
     * This method checks the users who created the posts and returns their position.
     ******************************************************************************************************************/
    public ArrayList<Integer> usersWhoCreatedPosts(){
        ArrayList<Integer> userIndex = new ArrayList<>();
        for(int i=0; i<MainPageController.listOfUsers.size(); i++){
            if(!(MainPageController.listOfUsers.get(i).getUserPosts().isEmpty())){
                userIndex.add(i);
            }
        }
        return userIndex;
    }

    /*******************************************************************************************************************
     * This method displays the additional details of the post that is selected from the tableView.
     ******************************************************************************************************************/
    public void getSelectedPostDetailsWithKeyboard(KeyEvent keyEvent) {
        if (!tableView.getSelectionModel().isEmpty()) {
            if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'E') {
                postSpecificLabel.setStyle("-fx-background-color:  #66CDAA;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
            } else if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'S') {
                postSpecificLabel.setStyle("-fx-background-color:   #F4A460;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
            } else if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'J') {
                postSpecificLabel.setStyle("-fx-background-color:   Add8E6;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
            }
            if (tableView.getSelectionModel().getSelectedItem().getCreatorID().equalsIgnoreCase(MainPageController.currentUserName)) {
                moreDetailsButton.setDisable(false);
            } else{
                moreDetailsButton.setDisable(true);
            }
        }
        return;
    }

    /*******************************************************************************************************************
     * This method displays the additional details of the post that is selected from the tableView.
     ******************************************************************************************************************/
    public void getSelectedPostDetailsWithMouse(MouseEvent mouseEvent) {
        if (!tableView.getSelectionModel().isEmpty()) {
            if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'E') {
                postSpecificLabel.setStyle("-fx-background-color:  #66CDAA;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
            } else if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'S') {
                postSpecificLabel.setStyle("-fx-background-color:   #F4A460;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
            } else if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'J') {
                postSpecificLabel.setStyle("-fx-background-color:   Add8E6;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
            }
            if (tableView.getSelectionModel().getSelectedItem().getCreatorID().equalsIgnoreCase(MainPageController.currentUserName)) {
                moreDetailsButton.setDisable(false);
            } else{
                moreDetailsButton.setDisable(true);
            }
        }
        return;
    }

    /*******************************************************************************************************************
     * This method will return an observable list of post objects.
     ******************************************************************************************************************/
    public ObservableList<Post> getPosts(){
        //Check the total number of posts
        ArrayList<Integer> indexOfUsersWithPosts = usersWhoCreatedPosts();
        ObservableList<Post> posts = FXCollections.observableArrayList();

        for(int i=0; i<indexOfUsersWithPosts.size(); i++){
            ArrayList<Post> p = MainPageController.listOfUsers.get(indexOfUsersWithPosts.get(i)).getUserPosts();
            for(int j=0; j<p.size(); j++){
                posts.add(p.get(j));
            }
        }
        return posts;
    }

    /*******************************************************************************************************************
     * This is an initializer.
     ******************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        moreDetailsButton.setDisable(true);
        replyButton.setDisable(true);

        //Updating the current user ID on the screen
        this.currentUserLabel.setText(MainPageController.currentUserName);

        //Set up the columns in the table view
        postIDColumn.setCellValueFactory(new PropertyValueFactory<Post,String>("postId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Post,String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Post,String>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Post,String>("status"));
        creatorIDColumn.setCellValueFactory(new PropertyValueFactory<Post,String>("creatorID"));
        //imageColumn.setCellValueFactory(new PropertyValueFactory<Post,Image>("photo"));

        //Load the data in table View
        tableView.setItems(getPosts());

        if(tableView.getSelectionModel().getSelectedItem() != null){
            System.out.println("Item is selected");
        }
    }
}