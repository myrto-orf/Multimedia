package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class LibrarianDeleteCategoryController {
    private Librarian librarian;
    private BookCategory category;
    @FXML
    private Label hiddenLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button deleteButton;

    public LibrarianDeleteCategoryController (Librarian librarian, BookCategory category) {
        this.librarian = librarian;
        this.category = category;
    }

    @FXML
    private void deleteCategory () {
        librarian.removeCategory(category.getTitle());
        hiddenLabel.setVisible(true);
    }

    @FXML
    private void goBack () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_category_see_more.fxml"));
            LibrarianCategorySeeMoreController nextController = new LibrarianCategorySeeMoreController(librarian, category);
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
