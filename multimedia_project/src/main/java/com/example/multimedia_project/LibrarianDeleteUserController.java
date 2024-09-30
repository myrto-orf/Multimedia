package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class LibrarianDeleteUserController {
    private Librarian librarian;
    private User user;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label hiddenLabel;


    public LibrarianDeleteUserController(Librarian librarian, User user) {
        this.librarian = librarian;
        this.user = user;
    }

    @FXML
    private void deleteUser() {
        librarian.deleteUser(user.getID());
        hiddenLabel.setVisible(true);
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_user_see_more.fxml"));
            LibrarianUserSeeMoreController nextController = new LibrarianUserSeeMoreController(librarian, user);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
