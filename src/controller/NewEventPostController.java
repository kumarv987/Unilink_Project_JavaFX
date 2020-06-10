package controller;

import model.exceptions.FormNotFilledException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Event;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewEventPostController implements Initializable {
    private FileChooser fileChooser;
    private File filePath;
    private Image uploadImage;

    //Variables for the FXML file
    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField venueTextField;
    @FXML private TextField dateTextField;
    @FXML private TextField capacityTextField;
    @FXML private Button uploadButton;
    @FXML private Button saveButton;
    @FXML private Button backButton;
    @FXML private Label exceptionLabel;
    @FXML private ImageView photoImageView;

    /*******************************************************************************************************************
     * This method uploads the image once upload button is pushed
     ******************************************************************************************************************/
    public void uploadButtonPushed(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        fileChooser = new FileChooser();
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
     * This method saves the info on about the post once the save button is pushed
     ******************************************************************************************************************/
    public void saveButtonPushed(ActionEvent event) throws IOException {
        try {
            //This checks for exception
            saveButtonExceptionChecker();

            //Creating an event post
            for(int i=0; i<MainPageController.listOfUsers.size(); i++){
                if(MainPageController.listOfUsers.get(i).getUserName().equalsIgnoreCase(MainPageController.currentUserName)){
                    if(this.uploadImage != null) {
                        MainPageController.listOfUsers.get(i).
                                addPostToUserPosts(new Event(nameTextField.getText()
                                        , descriptionTextArea.getText()
                                        , venueTextField.getText()
                                        , dateTextField.getText()
                                        , Integer.parseInt(capacityTextField.getText())
                                        , this.uploadImage
                                        , MainPageController.currentUserName));
                    }
                    else {
                        MainPageController.listOfUsers.get(i).
                                addPostToUserPosts(new Event(nameTextField.getText()
                                        , descriptionTextArea.getText()
                                        , venueTextField.getText()
                                        , dateTextField.getText()
                                        , Integer.parseInt(capacityTextField.getText())
                                        , MainPageController.currentUserName));
                    }
                    //Now disable the save button once the the info is saved
                    this.saveButton.setDisable(true);
                }
            }
        } catch (FormNotFilledException e) {
            this.exceptionLabel.setText(e.getMessage());
        }
    }

    /*******************************************************************************************************************
     * This method is called by the saveButtonPushed method and checks for the exception.
     ******************************************************************************************************************/
    public void saveButtonExceptionChecker() throws FormNotFilledException {
        if(nameTextField.getText().equalsIgnoreCase("")){
            throw new FormNotFilledException("You must enter the name before saving!!");
        }
        if(venueTextField.getText().equalsIgnoreCase("")){
            throw new FormNotFilledException("You must enter the venue before saving!!");
        }
        if(dateTextField.getText().equalsIgnoreCase("")){
            throw new FormNotFilledException("You must enter the date before saving!!");
        }
        if(capacityTextField.getText().equalsIgnoreCase("")){
            throw new FormNotFilledException("You must enter the capacity before saving!!");
        }
    }

    /*******************************************************************************************************************
     * This method changes the screen back to the Main window
     ******************************************************************************************************************/
    public void backButtonPushed(ActionEvent event) throws IOException {
        //Now loading the new stage
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        Scene scene = new Scene(parent);

        //This line get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Main Window");
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
