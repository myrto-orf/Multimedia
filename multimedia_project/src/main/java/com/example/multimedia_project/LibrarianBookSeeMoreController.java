package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrarianBookSeeMoreController {
    private Book book;
    private Librarian librarian;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label ISBNLabel;
    @FXML
    private Label publicationYearLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label copiesLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private ListView commentsList;
    @FXML
    private Button editBookButton;
    @FXML
    private Button deleteBookButton;
    @FXML
    private Button backButton;


    public LibrarianBookSeeMoreController(Librarian librarian, Book book) {
        this.librarian = librarian;
        this.book = book;
    }

    @FXML
    public void initialize() {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        publisherLabel.setText(book.getPublisher());
        ISBNLabel.setText(book.getISBN());
        String publicationYearString = String.valueOf(book.getPublicationYear());
        publicationYearLabel.setText(publicationYearString);
        categoryLabel.setText(book.getCategory());
        String copiesString = String.valueOf(book.getCopies());
        copiesLabel.setText(copiesString);
        String ratingString = String.valueOf(book.getAverageRating());
        ratingLabel.setText(ratingString);
        for (String comment : book.getComments()) {
            Label commentLabel = new Label(comment);
            commentsList.getItems().add(commentLabel);
        }
    }

    @FXML
    private void editBook () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_edit_book.fxml"));
            LibrarianEditBookController nextController = new LibrarianEditBookController(librarian, book);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) editBookButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteBook () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_delete_book.fxml"));
            LibrarianDeleteBookController nextController = new LibrarianDeleteBookController(librarian, book);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) deleteBookButton.getScene().getWindow();
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
