package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    @FXML private Button enterButton;
    @FXML private Button exitButton;
    @FXML private TextField userNameTextField;
    @FXML private Label userNameWarningLabel;

    /*******************************************************************************************************************
     * This method first checks if the anything is entered in the userNameTextField, then save the userName info, and
     * finally loads the next screen.
     ******************************************************************************************************************/
    public void enterButtonPushed() {
        //If the nothing is entered in the userName text field
        if(userNameTextField.getText().equalsIgnoreCase("")){
        String messageLabelWarning = "You must enter a Username first!!\n";
            this.userNameWarningLabel.setText(messageLabelWarning);
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}