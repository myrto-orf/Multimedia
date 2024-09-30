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

public class UserBookSeeMoreController {
    private Book book;
    private User user;
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
    private Label numberOfRatingsLabel;
    @FXML
    private ListView commentsList;
    @FXML
    private Button backButton;
    @FXML
    private Button issueBookButton;

    public UserBookSeeMoreController(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    @FXML
    private void initialize() {
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
        numberOfRatingsLabel.setText(String.valueOf(book.getRatings().size()));
        for (String comment : book.getComments()) {
            Label commentLabel = new Label(comment);
            commentsList.getItems().add(commentLabel);
        }
    }

    @FXML
    private void goBackToUserInterface () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_interface.fxml"));
            UserInterfaceController nextController = new UserInterfaceController(user);
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
    private void issueBook () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_issue_book.fxml"));
            UserIssueBookController nextController = new UserIssueBookController(user, book);
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
