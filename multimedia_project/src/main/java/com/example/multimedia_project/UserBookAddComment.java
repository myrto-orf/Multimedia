package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class UserBookAddComment {
    private User user;
    private Book book;
    @FXML
    private TextArea commentText;
    @FXML
    private Button cancelButton;
    @FXML
    private Button submitCommentButton;
    @FXML
    private Label hiddenLabel;

    public UserBookAddComment (User user, Book book) {
        this.user = user;
        this.book = book;
    }

    @FXML
    private void goBack () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_book_add_rating_review.fxml"));
            UserBookAddRatingReviewController nextController = new UserBookAddRatingReviewController(user, book);
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

    @FXML
    private void submitComment () {
        String comment = commentText.getText();
        if (comment.isEmpty()) {
            hiddenLabel.setText("Please add comment.");
            hiddenLabel.setVisible(true);
        } else {
            user.addUserComment(book, comment);
            hiddenLabel.setText("Comment added successfully");
            hiddenLabel.setVisible(true);
        }
    }
}
