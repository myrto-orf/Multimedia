package com.example.multimedia_project;

import beans.BookCategory;
import beans.Librarian;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LibrarianEditCategoryController {
    private Librarian librarian;
    private BookCategory category;
    @FXML
    private Button cancelButton;
    @FXML
    private Button doneButton;
    @FXML
    private Label alertLabel;
    @FXML
    private TextField titleText;


    public LibrarianEditCategoryController (Librarian librarian, BookCategory category) {
        this.librarian = librarian;
        this.category = category;
    }

    @FXML
    private void initialize () {
        titleText.setText(category.getTitle());
    }

    @FXML
    private void editCategory () {
        librarian.updateCategory(category.getTitle(), titleText.getText());
        alertLabel.setVisible(true);
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
