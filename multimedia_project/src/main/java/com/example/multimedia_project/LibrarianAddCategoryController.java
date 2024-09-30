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


public class LibrarianAddCategoryController {
    private Librarian librarian;
    @FXML
    private Label alertLabel;
    @FXML
    private TextField titleText;
    @FXML
    private Button backButton;
    @FXML
    private Button doneButton;

    public LibrarianAddCategoryController (Librarian librarian) {
        this.librarian = librarian;
    }

    @FXML
    private void addCategory () {
        String title = titleText.getText();
        librarian.addCategory(title);
        alertLabel.setVisible(true);
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
}
