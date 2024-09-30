package com.example.multimedia_project;

import beans.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class UserBookAddRatingReviewController {
    private User user;
    private Book book;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label ISBNLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label numberOfRatingsLabel;
    @FXML
    private ListView commentsList;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<Integer> ratingsComboBox;
    @FXML
    private Button commentButton;


    public UserBookAddRatingReviewController (User user, Book book) {
        this.user = user;
        this.book = book;
    }

    @FXML
    private void initialize () {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        ISBNLabel.setText(book.getISBN());
        categoryLabel.setText(book.getCategory());
        ratingLabel.setText(String.valueOf(book.getAverageRating()));
        numberOfRatingsLabel.setText(String.valueOf(book.getRatings().size()));
        for (String comment : book.getComments()) {
            Label commentLabel = new Label(comment);
            commentsList.getItems().add(commentLabel);
        }
        ratingsComboBox.getItems().addAll(1, 2, 3, 4, 5);
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
    private void addRating () {
        Integer selectedRating = ratingsComboBox.getValue();
        if (selectedRating != null) {
            user.addUserRating(book, selectedRating);
        }
        initialize();
    }

    @FXML
    private void addComment () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_book_add_comment.fxml"));
            UserBookAddComment nextController = new UserBookAddComment(user, book);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) commentButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
