package controller;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Post;
import model.User;
import javafx.event.ActionEvent;
import model.hsql_db.SQLJdbcAdaptor;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    // Variables from the Login window
    @FXML private Button enterButton;
    @FXML private Button exitButton;
    @FXML private TextField userNameTextField;
    @FXML private Label userNameWarningLabel;




    /*******************************************************************************************************************
     * This method saves all the information in the database before logging out of the system.
     ******************************************************************************************************************/
    public void exitButtonPushed(ActionEvent event) throws SQLException, ClassNotFoundException {
        SQLJdbcAdaptor adaptor = SQLJdbcAdaptor.getInstance();
        //adaptor.deleteTables();
        adaptor.initializeTables();
        //System.out.println("Does user table exist: "+adaptor.checkIfTableExist("user"));
        if(!(MainPageController.listOfUsers.isEmpty())) { // This statement first saves all the users in the database
            for (int i = 0; i < MainPageController.listOfUsers.size(); i++) {
                adaptor.insertValue("user", i, -1, -1);
            }
        }
        if(!(MainPageController.listOfUsers.isEmpty())){//This statement saves all the info about posts in the database
            for(int i=0; i<MainPageController.listOfUsers.size(); i++){
                if(!(MainPageController.listOfUsers.get(i).getUserPosts().isEmpty())) {
                    for (int j = 0; j < MainPageController.listOfUsers.get(i).getUserPosts().size(); j++) {
                        adaptor.insertValue("posts",i,j,-1);
                        if (MainPageController.listOfUsers.get(i).getUserPosts().get(j).getPostId().charAt(0) == 'E') {
                            adaptor.insertValue("event", i, j, -1);
                            if (!(MainPageController.listOfUsers.get(i).getUserPosts().get(j).getReplies().isEmpty())) {
                                for (int k = 0; k < MainPageController.listOfUsers.get(i).getUserPosts().get(j).getReplies().size(); k++) {
                                    System.out.println(MainPageController.listOfUsers.get(i).getUserPosts().get(j).getReplies().get(k).getReplyId());
                                    adaptor.insertValue("reply", i, j, k);
                                }
                            }
                        } else if (MainPageController.listOfUsers.get(i).getUserPosts().get(j).getPostId().charAt(0) == 'S') {
                            adaptor.insertValue("sale", i, j, -1);
                            if (!(MainPageController.listOfUsers.get(i).getUserPosts().get(j).getReplies().isEmpty())) {
                                for (int k = 0; k < MainPageController.listOfUsers.get(i).getUserPosts().get(j).getReplies().size(); k++) {
                                    adaptor.insertValue("reply", i, j, k);
                                }
                            }
                        } else {
                            adaptor.insertValue("job", i, j, -1);
                            if (!(MainPageController.listOfUsers.get(i).getUserPosts().get(j).getReplies().isEmpty())) {
                                for (int k = 0; k < MainPageController.listOfUsers.get(i).getUserPosts().get(j).getReplies().size(); k++) {
                                    adaptor.insertValue("reply", i, j, k);
                                }
                            }
                        }
                    }
                }
            }
        }
        //get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    /*******************************************************************************************************************
     * This method first checks if the anything is entered in the userNameTextField, then save the userName info, and
     * finally loads the next screen.
     ******************************************************************************************************************/
    public void enterButtonPushed(ActionEvent event) throws IOException {
        //If the nothing is entered in the userName text field then showcase the warning
        if(userNameTextField.getText().equalsIgnoreCase("")){
            String messageLabelWarning = "You must enter a Username first!!\n";
            this.userNameWarningLabel.setText(messageLabelWarning);
        }
        else {
            //Check if user already exists
            boolean doesUserExist = checkIfUserExists(userNameTextField.getText());
            if(doesUserExist == false) {
                //Create new instance for user and add it to the listOfUsers
                User newUser = new User(userNameTextField.getText());
                MainPageController.listOfUsers.add(newUser);
            }
            MainPageController.currentUserName = userNameTextField.getText();

            //Going to MainPage
            changeScreenToMainWindow(event);
        }
    }

    /*******************************************************************************************************************
     * This method checks if the user already exists or not. If user exists then it returns true otherwise false
     ******************************************************************************************************************/
    public boolean checkIfUserExists(String userName){
        if (!MainPageController.listOfUsers.isEmpty()) {
            for (User userInfo : MainPageController.listOfUsers) {
                if (userInfo.getUserName().equalsIgnoreCase(userName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*******************************************************************************************************************
     * This method changes the screen when Submit button is pressed with userName entered
     ******************************************************************************************************************/
    public void changeScreenToMainWindow(ActionEvent event) throws IOException{
        //Now loading the new stage
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
        Scene mainWindowScene = new Scene(mainWindowParent);

        //This line get the stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Main Window");
        window.setScene(mainWindowScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}