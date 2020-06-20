package controller;

import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.*;
import javafx.scene.input.MouseEvent;
import model.exceptions.FormNotFilledException;
import model.exceptions.InvalidOfferPriceException;

import javax.sound.midi.Soundbank;
import javax.xml.transform.sax.SAXSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainPageController implements Initializable {
    //Variables for accessing model package info
    public static final ArrayList<User> listOfUsers = new ArrayList<>();
    public static String currentUserName;
    public static String postIdForReply;
    public static int postIdReply;

    //Variables for FXML file
    @FXML private AnchorPane anchorID;
    @FXML private Button joinEventButton;
    @FXML private Button moreDetailsButton;
    @FXML private Button replyButton;
    @FXML private Button newEventPostButton;
    @FXML private Button newSalePostButton;
    @FXML private Button newJobPostButton;
    @FXML private Button logOutButton;
    @FXML private ChoiceBox typeChoiceBox;
    @FXML private ChoiceBox statusChoiceBox;
    @FXML private ChoiceBox creatorChoiceBox;
    @FXML private Label replyStatusLabel;
    @FXML private Label currentUserLabel;
    @FXML private Label postSpecificLabel;
    @FXML private TextField offerValueTextField;
    @FXML private TableView<Post> tableView;
    @FXML private TableColumn<Post,String> postIDColumn;
    @FXML private TableColumn<Post,String> titleColumn;
    @FXML private TableColumn<Post,String> descriptionColumn;
    @FXML private TableColumn<Post,String> statusColumn;
    @FXML private TableColumn<Post,String> creatorIDColumn;
    @FXML private ImageView postImage;
    @FXML private MenuItem exportOption;
    @FXML private MenuItem importOption;
    @FXML private MenuItem developerInformationOption;
    //@FXML private TableColumn<Post, Image> imageColumn;

    /*******************************************************************************************************************
     * This method exports the data into a file when a export option is selected from the menu bar
     ******************************************************************************************************************/
    public void developerOptionSelected(ActionEvent event) {

    }

    /*******************************************************************************************************************
     * This method exports the data into a file when a export option is selected from the menu bar
     ******************************************************************************************************************/
    public void importOptionSelected(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file","output_data.txt"));
        File file = fc.showOpenDialog(null);
        if(file != null){
            try (Scanner input = new Scanner(file)){
                while(input.hasNextLine()){
                    String[] data = input.nextLine().split(";");
                    for(String s: data){

                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /*******************************************************************************************************************
     * This method exports the data into a file when a export option is selected from the menu bar
     ******************************************************************************************************************/
    public void exportOptionSelected(ActionEvent event){
        final DirectoryChooser dirChooser = new DirectoryChooser();
        Stage stage = (Stage) anchorID.getScene().getWindow();
        File file = dirChooser.showDialog(stage);

        if(file != null){
            FileWriter writer = null;
            try{
                writer = new FileWriter(file+"/output_data.txt");
                for(User user:MainPageController.listOfUsers){
                    user.writeDataToFile(writer);
                    writer.write("\n\n");
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
     * This method Creates a new sale post.
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
     * This method Creates a new job post.
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
     * This method checks the exception once the reply button is pushed
     ******************************************************************************************************************/
    public void replyButtonExceptionChecker(int a) throws FormNotFilledException,InvalidOfferPriceException {
        if(replyStatusLabel.getText().equalsIgnoreCase("")){
            throw new FormNotFilledException("You must enter a offer vale first");
        }
        if(a == 0){
            throw new InvalidOfferPriceException("Offer Not accepted");
        }
    }

    /*******************************************************************************************************************
     * This method allows the user able to Reply to Sale or Job Post.
     ******************************************************************************************************************/
    public void replyButtonPushed(ActionEvent event) {
        try{
            int a= 5;
            replyButtonExceptionChecker(5);
            Double.parseDouble(offerValueTextField.getText());
            Reply reply = new Reply(Double.parseDouble(offerValueTextField.getText()));
            for (int i = 0; i < MainPageController.listOfUsers.size(); i++) {
                for (int j = 0; j < MainPageController.listOfUsers.get(i).getUserPosts().size(); j++) {
                    String tempPostID = MainPageController.listOfUsers.get(i).getUserPosts().get(j).getPostId();
                    Post tempPost = MainPageController.listOfUsers.get(i).getUserPosts().get(j);
                    if (tempPostID.equalsIgnoreCase(MainPageController.postIdForReply)) {
                        int replyAddedOrNot = MainPageController.listOfUsers.get(i).getUserPosts().get(j).handleReply(reply);
                        if (MainPageController.postIdForReply.charAt(0) == 'J') {
                            replyButtonExceptionChecker(replyAddedOrNot);
                            if (replyAddedOrNot == 1) {
                                replyStatusLabel.setText("Offer accepted!");
                            }
                        } else {
                            replyButtonExceptionChecker(replyAddedOrNot);
                            if (replyAddedOrNot == 1) {
                                StringBuilder str1 = new StringBuilder("Congratulations! The ");
                                StringBuilder str2 = new StringBuilder(tempPost.getTitle());
                                StringBuilder str3 = new StringBuilder(" has been sold to you.\n");
                                StringBuilder str4 = new StringBuilder("Please contact the owner ");
                                StringBuilder str5 = new StringBuilder(tempPost.getCreatorID());
                                StringBuilder str6 = new StringBuilder(" for more details.\n");
                                String s = str1.append(str2).append(str3).append(str4).append(str5).append(str6).toString();
                                replyStatusLabel.setText(s);
                            } else if (replyAddedOrNot == 2) {
                                replyStatusLabel.setText("Your offer has been submitted! \n" +
                                        "However, your offer is below the asking price. " +
                                        "\nThe item is still on sale");
                            }
                        }
                    }
                }
            }
        }catch(FormNotFilledException e){
            replyStatusLabel.setText(e.getMessage());
        }catch (InvalidOfferPriceException e) {
            replyStatusLabel.setText(e.getMessage());
        }catch (NumberFormatException e){
            replyStatusLabel.setText("You must enter a number here!!");
        }
    }

    /*******************************************************************************************************************
     * This method allows the user able to attend the Event or not.
     ******************************************************************************************************************/
    public void joinEventButtonPushed(ActionEvent event) throws IOException{
        Reply reply = new Reply(1);
        for(int i=0; i<MainPageController.listOfUsers.size(); i++){
            for(int j=0; j<MainPageController.listOfUsers.get(i).getUserPosts().size(); j++){
                String tempPostID = MainPageController.listOfUsers.get(i).getUserPosts().get(j).getPostId();
                if(tempPostID.equalsIgnoreCase(MainPageController.postIdForReply)){
                    int replyAddedOrNot = MainPageController.listOfUsers.get(i).getUserPosts().get(j).handleReply(reply);
                    if(replyAddedOrNot == -1){
                        replyStatusLabel.setText("You are Already Registered");
                    }else if(replyAddedOrNot == 0){
                        replyStatusLabel.setText("Event capacity is full");
                    }else if(replyAddedOrNot == 1){
                        replyStatusLabel.setText("Event Registration Accepted");
                    }else{
                        replyStatusLabel.setText("Something Went Wrong!!");
                    }
                }
            }
        }
    }

    /*******************************************************************************************************************
     * This method checks the users who created the posts and returns their index position stored in an ArrayList.
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
        replyStatusLabel.setText("");
        String creatorID = tableView.getSelectionModel().getSelectedItem().getCreatorID();
        System.out.println("MADARCHOD");
        if (!tableView.getSelectionModel().isEmpty()) {
            MainPageController.postIdForReply = tableView.getSelectionModel().getSelectedItem().getPostId();
            MainPageController.postIdReply = tableView.getSelectionModel().getSelectedItem().getPostOwnId();
            if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'E') {
                postSpecificLabel.setStyle("-fx-background-color:  #66CDAA;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
                if((!(creatorID.equalsIgnoreCase(MainPageController.currentUserName))) &&
                        (tableView.getSelectionModel().getSelectedItem().getStatus().equalsIgnoreCase("OPEN"))){
                    joinEventButton.setDisable(false);
                }else {
                    joinEventButton.setDisable(true);
                }
                replyButton.setDisable(true);
                offerValueTextField.setDisable(true);
            } else if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'S') {
                postSpecificLabel.setStyle("-fx-background-color:   #F4A460;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
                if(!(creatorID.equalsIgnoreCase(MainPageController.currentUserName)) &&
                        (tableView.getSelectionModel().getSelectedItem().getStatus().equalsIgnoreCase("OPEN"))){
                    replyButton.setDisable(false);
                    offerValueTextField.setDisable(false);
                }else {
                    replyButton.setDisable(true);
                    offerValueTextField.setDisable(true);
                }
                joinEventButton.setDisable(true);
            } else if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'J') {
                postSpecificLabel.setStyle("-fx-background-color:   Add8E6;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
                if(!(creatorID.equalsIgnoreCase(MainPageController.currentUserName)) &&
                        (tableView.getSelectionModel().getSelectedItem().getStatus().equalsIgnoreCase("OPEN"))){
                    replyButton.setDisable(false);
                    offerValueTextField.setDisable(false);
                } else {
                    replyButton.setDisable(true);
                    offerValueTextField.setDisable(true);
                }
                joinEventButton.setDisable(true);
            }
            if (creatorID.equalsIgnoreCase(MainPageController.currentUserName)) {
                moreDetailsButton.setDisable(false);
            } else{
                moreDetailsButton.setDisable(true);
            }
        }
    }

    /*******************************************************************************************************************
     * This method displays the additional details of the post that is selected from the tableView.
     ******************************************************************************************************************/
    public void getSelectedPostDetailsWithMouse(MouseEvent mouseEvent) {
        if (!tableView.getSelectionModel().isEmpty()) {
            String creatorID = tableView.getSelectionModel().getSelectedItem().getCreatorID();
            MainPageController.postIdForReply = tableView.getSelectionModel().getSelectedItem().getPostId();
            MainPageController.postIdReply = tableView.getSelectionModel().getSelectedItem().getPostOwnId();
            if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'E') {
                postSpecificLabel.setStyle("-fx-background-color:  #66CDAA;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
                if((!(creatorID.equalsIgnoreCase(MainPageController.currentUserName))) &&
                        (tableView.getSelectionModel().getSelectedItem().getStatus().equalsIgnoreCase("OPEN"))){
                    joinEventButton.setDisable(false);
                }else {
                    joinEventButton.setDisable(true);
                }
                replyButton.setDisable(true);
                offerValueTextField.setDisable(true);
            } else if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'S') {
                postSpecificLabel.setStyle("-fx-background-color:   #F4A460;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
                if(!(creatorID.equalsIgnoreCase(MainPageController.currentUserName)) &&
                        (tableView.getSelectionModel().getSelectedItem().getStatus().equalsIgnoreCase("OPEN"))){
                    replyButton.setDisable(false);
                    offerValueTextField.setDisable(false);
                }else {
                    replyButton.setDisable(true);
                    offerValueTextField.setDisable(true);
                }
                joinEventButton.setDisable(true);
            } else if (tableView.getSelectionModel().getSelectedItem().getPostId().charAt(0) == 'J') {
                postSpecificLabel.setStyle("-fx-background-color:   Add8E6;");
                postSpecificLabel.setText(tableView.getSelectionModel().getSelectedItem().getPostDetails());
                if(!(creatorID.equalsIgnoreCase(MainPageController.currentUserName)) &&
                        (tableView.getSelectionModel().getSelectedItem().getStatus().equalsIgnoreCase("OPEN"))){
                    replyButton.setDisable(false);
                    offerValueTextField.setDisable(false);
                } else {
                    replyButton.setDisable(true);
                    offerValueTextField.setDisable(true);
                }
                joinEventButton.setDisable(true);
            }
            if (creatorID.equalsIgnoreCase(MainPageController.currentUserName)) {
                moreDetailsButton.setDisable(false);
            } else{
                moreDetailsButton.setDisable(true);
            }
        }
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
        joinEventButton.setDisable(true);
        moreDetailsButton.setDisable(true);
        replyButton.setDisable(true);
        offerValueTextField.setDisable(true);

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

        //Configuring the choice box list
        typeChoiceBox.getItems().add("All");
        typeChoiceBox.getItems().add("Event");
        typeChoiceBox.getItems().add("Sale");
        typeChoiceBox.getItems().add("Job");

        statusChoiceBox.getItems().add("OPEN");
        statusChoiceBox.getItems().add("CLOSED");
        statusChoiceBox.getItems().add("All");

        creatorChoiceBox.getItems().add("All");
        for(int i=0; i<MainPageController.listOfUsers.size();i++){
            creatorChoiceBox.getItems().add(MainPageController.listOfUsers.get(i).getUserName());
        }

        typeChoiceBox.setValue("All");
        statusChoiceBox.setValue("All");
        creatorChoiceBox.setValue("All");
/*
        //Wrap the ObservableList in a FilteredList initially
        FilteredList<Post> filteredData = new FilteredList<Post>(tableView.getItems(),b -> true);

        typeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            filteredData.setPredicate(Post -> {
                if(newValue == null){
                    return true;
                }
                System.out.println(postIDColumn.getCellFactory());
                if(tableView.getColumns().equals()){
                    return true;
                }
                return false;
            });
        });
        SortedList<Post> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
*/
    }
}