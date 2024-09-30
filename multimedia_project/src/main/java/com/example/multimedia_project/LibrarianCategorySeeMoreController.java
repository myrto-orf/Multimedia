package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;


public class LibrarianCategorySeeMoreController {
    private Librarian librarian;
    private BookCategory category;
    @FXML
    private Button backButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private ListView bookList;

    public LibrarianCategorySeeMoreController (Librarian librarian, BookCategory category) {
        this.librarian = librarian;
        this.category = category;
    }

    @FXML
    private void initialize () {
        for (Book book : category.getBooks()) {
            bookList.getItems().add(book.getTitle());
        }
    }

    @FXML
    private void editCategory () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_edit_category.fxml"));
            LibrarianEditCategoryController nextController = new LibrarianEditCategoryController(librarian, category);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) editButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteCategory () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_delete_category.fxml"));
            LibrarianDeleteCategoryController nextController = new LibrarianDeleteCategoryController(librarian, category);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
