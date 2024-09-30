package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LibrarianEditBookController {
    @FXML
    private TextField titleText;
    @FXML
    private TextField authorText;
    @FXML
    private TextField publisherText;
    @FXML
    private TextField ISBNText;
    @FXML
    private TextField publicationYearText;
    @FXML
    private TextField copiesText;
    @FXML
    private Button addBookButton;
    @FXML
    private Button backButton;
    @FXML
    private Label errorLabel;

    private Librarian librarian;
    private Book book;

    public LibrarianEditBookController (Librarian librarian, Book book) {
        this.librarian = librarian;
        this.book = book;
    }

    @FXML
    public void initialize() {
        titleText.setText(book.getTitle());
        authorText.setText(book.getAuthor());
        publisherText.setText(book.getPublisher());
        ISBNText.setText(book.getISBN());
        String publicationYearString = String.valueOf(book.getPublicationYear());
        publicationYearText.setText(publicationYearString);
        String copiesString = String.valueOf(book.getCopies());
        copiesText.setText(copiesString);
    }

    @FXML
    public void editBook() {
        String title = titleText.getText();
        String author = authorText.getText();
        String publisher = publisherText.getText();
        String ISBN = ISBNText.getText();
        String publicationYear = publicationYearText.getText();
        String copies = copiesText.getText();

        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || ISBN.isEmpty() ||
                publicationYear.isEmpty() || copies.isEmpty()) {
            errorLabel.setText("One or more fields are empty");
            errorLabel.setVisible(true);
            return;
        }

        int publicationYearInt;
        int copiesInt;
        try {
            publicationYearInt = Integer.parseInt(publicationYear);
        } catch (NumberFormatException e) {
            errorLabel.setText("Publication year must be a valid integer.");
            errorLabel.setVisible(true);
            return;
        }

        try {
            copiesInt = Integer.parseInt(copies);
        } catch (NumberFormatException e) {
            errorLabel.setText("Number of copies must be a valid integer.");
            errorLabel.setVisible(true);
            return;
        }

        librarian.editBook(ISBN, title, author, publisher, publicationYearInt, copiesInt);
        errorLabel.setText("Book edited successfully");
        errorLabel.setVisible(true);
    }

    @FXML
    private void goBack () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_book_see_more.fxml"));
            LibrarianBookSeeMoreController nextController = new LibrarianBookSeeMoreController(librarian, book);
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
