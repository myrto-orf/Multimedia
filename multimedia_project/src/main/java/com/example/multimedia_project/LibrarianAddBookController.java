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


public class LibrarianAddBookController {
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
    private TextField categoryText;
    @FXML
    private TextField copiesText;
    @FXML
    private Button addBookButton;
    @FXML
    private Button backButton;
    @FXML
    private Label errorLabel;

    private Librarian librarian;

    public LibrarianAddBookController (Librarian librarian) {
        this.librarian = librarian;
    }

    @FXML
    public void addNewBook() {
        String title = titleText.getText();
        String author = authorText.getText();
        String publisher = publisherText.getText();
        String ISBN = ISBNText.getText();
        String publicationYear = publicationYearText.getText();
        String category = categoryText.getText();
        String copies = copiesText.getText();

        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || ISBN.isEmpty() ||
            publicationYear.isEmpty() || category.isEmpty() || copies.isEmpty()) {
            errorLabel.setText("One or more fields are empty");
            errorLabel.setVisible(true);
            return;
        }

        for (Book book : Book.getBooks()) {
            if (book.getISBN().equals(ISBN)) {
                errorLabel.setText("Book already exists");
                errorLabel.setVisible(true);
                return;
            }
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

        librarian.addBook(title, author, publisher, ISBN, publicationYearInt, category, copiesInt);
        errorLabel.setText("New Book added successfully");
        errorLabel.setVisible(true);
    }

    @FXML
    private void goBackToLibrarianInterface() {
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
