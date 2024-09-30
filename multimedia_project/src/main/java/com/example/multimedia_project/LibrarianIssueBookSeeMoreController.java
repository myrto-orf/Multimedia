package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;


public class LibrarianIssueBookSeeMoreController {
    Librarian librarian;
    IssueBook issueBook;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label issueDateLabel;
    @FXML
    private Label returnDateLabel;
    @FXML
    private Label returnStatusLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button terminateButton;

    public LibrarianIssueBookSeeMoreController (Librarian librarian, IssueBook issueBook) {
        this.librarian = librarian;
        this.issueBook = issueBook;
    }

    @FXML
    private void initialize () {
        usernameLabel.setText(issueBook.getUser().getUsername());
        titleLabel.setText(issueBook.getBook().getTitle());
        issueDateLabel.setText(issueBook.getIssueDate().toString());
        LocalDate returnDate = issueBook.getIssueDate().plusDays(5);
        returnDateLabel.setText(returnDate.toString());
        if (issueBook.getReturnStatus() == true) {
            returnStatusLabel.setText("Returned");
        } else {
            returnStatusLabel.setText("Issued");
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

    @FXML
    private void terminateIssueBook () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_terminate_issue_book.fxml"));
            LibrarianTerminateIssueBookController nextController = new LibrarianTerminateIssueBookController(librarian, issueBook);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) terminateButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
