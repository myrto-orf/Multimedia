package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LibrarianDeleteBookController {
    private Librarian librarian;
    private Book book;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label hiddenLabel;

    public LibrarianDeleteBookController (Librarian librarian, Book book) {
        this.librarian = librarian;
        this.book = book;
    }

    @FXML
    private void deleteBook () {
        librarian.deleteBook(book);
        hiddenLabel.setVisible(true);
    }

    @FXML
    private void goBack () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_book_see_more.fxml"));
            LibrarianBookSeeMoreController nextController = new LibrarianBookSeeMoreController(librarian, book);
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
