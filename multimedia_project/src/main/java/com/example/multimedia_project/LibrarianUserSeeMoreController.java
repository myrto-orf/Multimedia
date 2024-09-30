package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class LibrarianUserSeeMoreController {
    private Librarian librarian;
    private User user;
    @FXML
    private Button backButton;
    @FXML
    private Button editUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label IDLabel;
    @FXML
    private Label emailLabel;

    public LibrarianUserSeeMoreController (Librarian librarian, User user) {
        this.librarian = librarian;
        this.user = user;
    }

    @FXML
    private void initialize () {
        usernameLabel.setText(user.getUsername());
        nameLabel.setText(user.getName());
        surnameLabel.setText(user.getSurname());
        IDLabel.setText(user.getID());
        emailLabel.setText(user.getEmail());
    }

    @FXML
    private void goBackToLibrarianInterface () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_interface.fxml"));
            LibrarianInterfaceController nextController = new LibrarianInterfaceController(librarian);
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

    @FXML
    private void editUser () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_edit_user.fxml"));
            LibrarianEditUserController nextController = new LibrarianEditUserController(librarian, user);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) editUserButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteUser () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_delete_user.fxml"));
            LibrarianDeleteUserController nextController = new LibrarianDeleteUserController(librarian, user);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) deleteUserButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
