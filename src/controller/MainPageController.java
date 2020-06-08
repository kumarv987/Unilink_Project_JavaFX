package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    //Variables for accessing model package info
    public static final ArrayList<User> listOfUsers = new ArrayList<>();
    public static String currentUserName;

    //Variables for FXML file
    @FXML private Button newEventPostButton;
    @FXML private Button newSalePostButton;
    @FXML private Button newJobPostButton;
    @FXML private Button logOutButton;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}