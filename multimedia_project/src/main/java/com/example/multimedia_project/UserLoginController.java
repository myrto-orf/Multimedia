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

public class UserLoginController {

    public User user;
    @FXML
    private TextField userUsernameText;

    @FXML
    private TextField userPasswordText;
    @FXML
    private Button userLoginButton;
    @FXML
    private Label errorMessageLabel;

    @FXML
    private void handleUserLogin(ActionEvent event) {
        String username = userUsernameText.getText();
        String password = userPasswordText.getText();


        User authenticatedUser = authenticateUser(username, password);
        if (authenticatedUser != null) {
            user = authenticatedUser;
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

        } else {
            errorMessageLabel.setText("Username or password is incorrect");
        }
    }

    public User authenticateUser(String username, String password) {
        if (User.getRegisteredUsers() == null) {
            return null;
        }
        for (User user : User.getRegisteredUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
