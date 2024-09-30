package com.example.multimedia_project;

import beans.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserSignupController {

    private User user;
    @FXML
    private TextField userUsernameText;

    @FXML
    private TextField userPasswordText;
    @FXML
    private TextField userNameText;
    @FXML
    private TextField userSurnameText;
    @FXML
    private TextField userIDText;
    @FXML
    private TextField userEmailText;
    @FXML
    private Button userLoginButton;
    @FXML
    private Label errorMessageLabel;


    @FXML
    private void handleUserSignup(ActionEvent event) {
        String username = userUsernameText.getText();
        String password = userPasswordText.getText();
        String name = userNameText.getText();
        String surname = userSurnameText.getText();
        String id = userIDText.getText();
        String email = userEmailText.getText();


        User checkedUser = checkUserInfo(username, password, name, surname, id, email);
        if (checkedUser != null) {
            user = checkedUser;
            Stage stage = (Stage) userUsernameText.getScene().getWindow();
            stage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("user_interface.fxml"));
                UserInterfaceController nextController = new UserInterfaceController(user);
                loader.setController(nextController);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public User checkUserInfo(String username, String password, String name, String surname, String id, String email) {
        if (User.getRegisteredUsers().isEmpty()) {
            User newUser = User.registerUser(username, password, name, surname, id, email);
            return newUser;
        } else {
            for (User user : User.getRegisteredUsers()) {
                if (user.getUsername().equals(username)) {
                    errorMessageLabel.setText("Username " + username + " is already taken. Please choose a different username.");
                    return null;
                }
            }

            for (User user : User.getRegisteredUsers()) {
                if (user.getID().equals(id) || user.getEmail().equals(email)) {
                    errorMessageLabel.setText("User is already registered.");
                    return null;
                }
            }
            User newUser = User.registerUser(username, password, name, surname, id, email);
            return newUser;
        }
    }
}
