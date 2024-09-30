package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserIssueBookController {
    private User user;
    private Book book;
    @FXML
    private Button cancelButton;
    @FXML
    private Button issueBookButton;
    @FXML
    private Label hiddenLabel;

    public UserIssueBookController(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    @FXML
    private void goBack () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user_book_see_more.fxml"));
            UserBookSeeMoreController nextController = new UserBookSeeMoreController(user, book);
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
    private void issueBook () {
        user.issueBook(book);
        hiddenLabel.setVisible(true);
    }
}
