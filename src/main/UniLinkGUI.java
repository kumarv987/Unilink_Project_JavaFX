package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;
import model.*;
import controller.*;
import model.hsql_db.SQLJdbcAdaptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UniLinkGUI extends Application {
    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Printing information from database
        SQLJdbcAdaptor sqlJdbcAdaptor = SQLJdbcAdaptor.getInstance();
        //sqlJdbcAdaptor.deleteTables();
        sqlJdbcAdaptor.initializeTables();
        loginPageEntry(stage);
    }

    private void loginPageEntry(Stage stage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}