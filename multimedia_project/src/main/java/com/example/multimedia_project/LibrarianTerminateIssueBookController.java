package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class LibrarianTerminateIssueBookController {
    private Librarian librarian;
    private IssueBook issueBook;
    @FXML
    private Button cancelButton;
    @FXML
    private Button terminateButton;
    @FXML
    private Label hiddenLabel;


    public LibrarianTerminateIssueBookController (Librarian librarian, IssueBook issueBook) {
        this.librarian = librarian;
        this.issueBook = issueBook;
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_issue_book_see_more.fxml"));
            LibrarianIssueBookSeeMoreController nextController = new LibrarianIssueBookSeeMoreController(librarian, issueBook);
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
    private void terminateIssueBook () {
        User currentUser = issueBook.getUser();
        Book currentBook = issueBook.getBook();
        librarian.terminateBorrowing(currentUser, currentBook);
        hiddenLabel.setVisible(true);
    }
}
