package controller;

import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Post;
import model.Reply;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PostDetailController implements Initializable {
    @FXML private Button closePostButton;
    @FXML private Button deletePostButton;
    @FXML private Button saveEditButton;
    @FXML private Button postUploadImageButton;
    @FXML private Button backToMainWindowButton;
    @FXML private Label postDetailWindowLabel;
    @FXML private TableView<Reply> replyTableView;
    @FXML private TableColumn<Reply,String> responderIdTableColumn;
    @FXML private TableColumn<Reply,Double> offerValueTableColumn;
    @FXML private Label postExceptionLabel;
    @FXML private ImageView postImageView;

    private File filePath;
    private FileChooser fileChooser;
    private Image uploadImage;

    /*******************************************************************************************************************
     * This method uploads the image once upload button is pushed
     ******************************************************************************************************************/
    public void postUploadImageButtonPushed(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        this.filePath = fileChooser.showOpenDialog(stage);
        try{
            BufferedImage bufferedImage = ImageIO.read(filePath);
            System.out.println(bufferedImage);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            this.uploadImage = image;
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /*******************************************************************************************************************
     * This allows the user to go back to the main window.
     ******************************************************************************************************************/
    public void backToMainWindowButtonPushed(ActionEvent event) throws IOException {
        //Now loading the new stage
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        Scene scene = new Scene(parent);

        //This line get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("MainPage");
        window.setScene(scene);
        window.show();
    }

    /*******************************************************************************************************************
     * This method prints the Post details on the Label
     ******************************************************************************************************************/
    public void getPostDetailsOnLabel(){
        for(int i=0; i<MainPageController.listOfUsers.size(); i++){
            for(int j=0; j<MainPageController.listOfUsers.get(i).getUserPosts().size(); j++){
                String postId = MainPageController.listOfUsers.get(i).getUserPosts().get(j).getPostId();
                String postDetails = MainPageController.listOfUsers.get(i).getUserPosts().get(j).getPostDetails();
                if(postId.equalsIgnoreCase(MainPageController.postIdForReply)){
                    if(postId.charAt(0) == 'E') {
                        String replyDetails = MainPageController.listOfUsers.get(i).getUserPosts().get(j).getReplyDetails();
                        postDetailWindowLabel.setStyle("-fx-background-color:  #66CDAA;");
                        postDetailWindowLabel.setText(postDetails+replyDetails);
                    } else if(postId.charAt(0) == 'J'){
                        postDetailWindowLabel.setStyle("-fx-background-color:   Add8E6;");
                        postDetailWindowLabel.setText(postDetails);
                    }else{
                        postDetailWindowLabel.setStyle("-fx-background-color:  #F4A460;");
                        postDetailWindowLabel.setText(postDetails);
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
     * This method will return an observable list of reply objects.
     ******************************************************************************************************************/
    public ObservableList<Reply> getReplies(){
        //Check the total number of posts
        ArrayList<Integer> indexOfUsersWithPosts = usersWhoCreatedPosts();
        ObservableList<Reply> replies = FXCollections.observableArrayList();

        for(int i=0; i<indexOfUsersWithPosts.size(); i++){
            ArrayList<Post> p = MainPageController.listOfUsers.get(indexOfUsersWithPosts.get(i)).getUserPosts();
            for(int j=0; j<p.size(); j++){
                if(p.get(j).getPostId().equalsIgnoreCase(MainPageController.postIdForReply)) {
                    ArrayList<Reply> replyListOfEachPost = p.get(j).getReplies();
                    for (int k = 0; k < replyListOfEachPost.size(); k++) {
                        replies.add(replyListOfEachPost.get(k));
                    }
                }
            }
        }
        return replies;
    }

    /*******************************************************************************************************************
     * This is an initializer.
     ******************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getPostDetailsOnLabel();

        //Set up the columns in the table view
        responderIdTableColumn.setCellValueFactory(new PropertyValueFactory<Reply,String>("respId"));
        offerValueTableColumn.setCellValueFactory(new PropertyValueFactory<Reply, Double>("value"));

        //Load the data in table View
        replyTableView.setItems(getReplies());
    }
}
