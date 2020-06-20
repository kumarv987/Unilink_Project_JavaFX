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
import model.Sale;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewSalePostController implements Initializable{
    private FileChooser fileChooser;
    private File filePath;
    private Image uploadImage;

    //Variables for the FXML file
    @FXML private TextField nameTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private TextField askPriceTextField;
    @FXML private TextField minRaiseTextField;
    @FXML private Button uploadButton;
    @FXML private Button saveButton;
    @FXML private Button backButton;
    @FXML private Label exceptionLabel;
    @FXML private ImageView photo;

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
                                addPostToUserPosts(new Sale(nameTextField.getText()
                                        , descriptionTextArea.getText()
                                        , Double.parseDouble(askPriceTextField.getText())
                                        , Double.parseDouble(minRaiseTextField.getText())
                                        , this.uploadImage
                                        , MainPageController.currentUserName));
                    }
                    else{
                        MainPageController.listOfUsers.get(i).
                                addPostToUserPosts(new Sale(nameTextField.getText()
                                        , descriptionTextArea.getText()
                                        , Double.parseDouble(askPriceTextField.getText())
                                        , Double.parseDouble(minRaiseTextField.getText())
                                        , MainPageController.currentUserName));
                    }
                    //Now disable the save button
                    this.saveButton.setDisable(true);
                }
            }
        } catch (FormNotFilledException e) {
            this.exceptionLabel.setText(e.getMessage());
        } catch (NumberFormatException e){
            this.exceptionLabel.setText("Ask Price and Min Raise should be a number!!");
        }
    }

    /*******************************************************************************************************************
     * This method is called by the saveButtonPushed method and checks for the exception.
     ******************************************************************************************************************/
    public void saveButtonExceptionChecker() throws FormNotFilledException {
        if(nameTextField.getText().equalsIgnoreCase("")){
            throw new FormNotFilledException("You must enter the name before saving!!");
        }
        if(askPriceTextField.getText().equalsIgnoreCase("")){
            throw new FormNotFilledException("You must enter the asking price before saving!!");
        }
        if(minRaiseTextField.getText().equalsIgnoreCase("")){
            throw new FormNotFilledException("You must enter the minimum raise before saving!!");
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
