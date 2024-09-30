package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LibrarianEditUserController {
    private Librarian librarian;
    private User user;
    @FXML
    private Button backButton;
    @FXML
    private Button doneButton;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField emailText;
    @FXML
    private Label alertLabel;

    public LibrarianEditUserController(Librarian librarian, User user) {
        this.librarian = librarian;
        this.user = user;
    }

    @FXML
    private void initialize() {
        usernameText.setText(user.getUsername());
        nameText.setText(user.getName());
        surnameText.setText(user.getSurname());
        emailText.setText(user.getEmail());
        alertLabel.setVisible(false);
    }

    @FXML
    private void editUser () {
        String username = usernameText.getText();
        String name = nameText.getText();
        String surname = surnameText.getText();
        String email = emailText.getText();
        librarian.editUser(user.getID(), username, name, surname, email);
        alertLabel.setVisible(true);
    }

    @FXML
    private void goBack () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_user_see_more.fxml"));
            LibrarianUserSeeMoreController nextController = new LibrarianUserSeeMoreController(librarian, user);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
