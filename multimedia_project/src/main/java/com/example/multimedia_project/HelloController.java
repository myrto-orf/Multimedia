package com.example.multimedia_project;

import beans.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private ListView topFiveBooksList;
    @FXML
    private Button userLoginButton;
    @FXML
    private Button userSignupButton;
    @FXML
    private Button librarianLoginButton;

    @FXML
    public void initialize() {
        if (Book.getBooks().isEmpty()) {
            return;
        }
        for (Book book : Book.viewTopFiveRatedBooks()) {
            Label bookLabel = new Label();
            bookLabel.setText("Title: " + book.getTitle() +
                    "\nAuthor: " + book.getAuthor() +
                    "\nISBN: " + book.getISBN() +
                    "\nAverage Rating: " + String.valueOf(book.getAverageRating()) +
                    "\nRatings: " + String.valueOf(book.getRatings().size()));

            bookLabel.setStyle("-fx-font-size: 9pt; " + "-fx-padding: 20px; ");

            topFiveBooksList.getItems().add(bookLabel);
        }
    }

    @FXML
    private void handleUserLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) userLoginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleUserSignup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_signup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) userSignupButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLibrarianLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) librarianLoginButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}