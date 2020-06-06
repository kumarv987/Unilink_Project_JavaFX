package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Service;
import model.User;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    //Variables for model package
    private Service modelServiceHandler = new Service();

    // Variables from the Login window
    @FXML private Button enterButton;
    @FXML private Button exitButton;
    @FXML private TextField userNameTextField;
    @FXML private Label userNameWarningLabel;

    /*******************************************************************************************************************
     * This method first checks if the anything is entered in the userNameTextField, then save the userName info, and
     * finally loads the next screen.
     ******************************************************************************************************************/
    public void enterButtonPushed(ActionEvent event) throws IOException, NullPointerException {
        //If the nothing is entered in the userName text field then showcase the warning
        if(userNameTextField.getText().equalsIgnoreCase("")){
        String messageLabelWarning = "You must enter a Username first!!\n";
            this.userNameWarningLabel.setText(messageLabelWarning);
        } else {
            //Create new instance for user and add it to the listOfUsers
            User newUser = new User(userNameTextField.getText());
            modelServiceHandler.addUsersToListOfUsers(newUser);

            //Setting a new stage
            changeScreen(event);
        }
    }

    /*******************************************************************************************************************
     * This method changes the screen when Submit button is pressed with userName entered
     ******************************************************************************************************************/
    private void changeScreen(ActionEvent event) throws IOException{
        //Now loading the new stage
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        Scene mainWindowScene = new Scene(mainWindowParent);

        //This line get the stage information
        Stage mainWindowStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainWindowStage.setScene(mainWindowScene);
        mainWindowStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}