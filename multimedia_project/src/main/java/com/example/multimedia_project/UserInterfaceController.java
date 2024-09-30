package com.example.multimedia_project;

import beans.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.List;

public class UserInterfaceController {

    private User user;
    public List<Book> searchedBooks;
    @FXML
    private TextField bookTitleText;
    @FXML
    private TextField bookAuthorText;
    @FXML
    private TextField bookPublicationYearText;
    @FXML
    private FlowPane issuedBooksFlowPane;
    @FXML
    private Button searchBookButton;
    @FXML
    private FlowPane searchedBooksFlowPane;

    public UserInterfaceController(User user) {
        this.user = user;
    }

    @FXML
    public void initialize() {
        if (user != null) {
            List<IssueBook> issueBookList = user.viewIssuedBooks();
            if (issueBookList != null) {
                for (IssueBook issueBook : issueBookList) {
                    if (issueBook.getReturnStatus() == false) {
                        StringBuilder issuedBooks = new StringBuilder();
                        issuedBooks.append("Title: " + issueBook.getBook().getTitle() + "\n");
                        issuedBooks.append("ISBN: " + issueBook.getBook().getISBN() + "\n");
                        issuedBooks.append("Issue Date: " + issueBook.getIssueDate() + "\n");
                        issuedBooks.append("Return Date: " + issueBook.getIssueDate().plusDays(5) + "\n");

                        Button ratingCommentButton = new Button("Add rating/ comment");

                        ratingCommentButton.setOnAction(event -> {
                            Stage stage = (Stage) ratingCommentButton.getScene().getWindow();
                            stage.close();

                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("user_book_add_rating_review.fxml"));
                                Book book = null;
                                for (Book currentBook : Book.getBooks()) {
                                    if (currentBook.getISBN().equals(issueBook.getBook().getISBN())) {
                                        book = currentBook;
                                    }
                                }
                                UserBookAddRatingReviewController nextController = new UserBookAddRatingReviewController(user, book);
                                loader.setController(nextController);
                                Parent root = loader.load();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        FlowPane buttonsPane = new FlowPane(ratingCommentButton);
                        issuedBooksFlowPane.getChildren().addAll(new Label(issuedBooks.toString()), buttonsPane);
                    }
                }
            } else {
                searchedBooksFlowPane.getChildren().add(new Label("User has no issued books."));
            }
        } else {
            searchedBooksFlowPane.getChildren().add(new Label("No user information available."));
        }

        searchBookButton.setOnAction(event -> searchBook());
    }

    @FXML
    private void searchBook() {
        String title = bookTitleText.getText();
        String author = bookAuthorText.getText();
        String publicationYear = bookPublicationYearText.getText();

        if (searchedBooks != null) {searchedBooks.clear();}

        if (Book.getBooks().isEmpty()) {
            searchedBooksFlowPane.getChildren().add(new Label("No books available"));
            return;
        }

        if (title.isEmpty() && author.isEmpty() && publicationYear.isEmpty()) {
            searchedBooksFlowPane.getChildren().add(new Label("All fields are empty."));
            return;
        }

        int publicationYearInt = 0;
        if (!publicationYear.isEmpty()) {
            try {
                publicationYearInt = Integer.parseInt(publicationYear);
            } catch (NumberFormatException e) {
                searchedBooksFlowPane.getChildren().add(new Label("Publication year must be a valid integer."));
                return;
            }
        }

        if (!title.isEmpty()) {
            if (!author.isEmpty()) {
                if (!publicationYear.isEmpty()) {
                    searchedBooks = user.searchBooksByTitleAuthorPublicationYear(title, author, publicationYearInt);
                } else {
                    searchedBooks = user.searchBooksByTitleAuthor(title, author);
                }
            } else {
                if (!publicationYear.isEmpty()) {
                    searchedBooks = user.searchBooksByTitlePublicationYear(title, publicationYearInt);
                } else {
                    searchedBooks = user.searchBooksByTitle(title);
                }
            }
        } else {
            if (!author.isEmpty()) {
                if (!publicationYear.isEmpty()) {
                    searchedBooks = user.searchBooksByAuthorPublicationYear(author, publicationYearInt);
                } else {
                    searchedBooks = user.searchBooksByAuthor(author);
                }
            } else {
                if (!publicationYear.isEmpty()) {
                    // Search by publication year only
                    searchedBooks = user.searchBooksByPublicationYear(publicationYearInt);
                }
            }
        }
        displaySearchBook();
    }

    @FXML
    private void displaySearchBook() {
        searchedBooksFlowPane.getChildren().clear();
        if (searchedBooks.isEmpty()) {
            searchedBooksFlowPane.getChildren().add(new Label("No results found"));
        } else {
            for (Book book : searchedBooks) {
                StringBuilder searchResults = new StringBuilder();
                searchResults.append("Book results: \n");
                searchResults.append(("Title: ")).append(book.getTitle()).append("\n");
                searchResults.append(("Author: ")).append(book.getAuthor()).append("\n");
                searchResults.append(("ISBN: ")).append(book.getISBN()).append("\n");
                searchResults.append(("Average Rating: ")).append(book.getAverageRating()).append("\n");
                searchResults.append(("Ratings: ")).append(book.getRatings().size()).append("\n");

                Button seeMoreButton = new Button("See More");
                Button issueBookButton = new Button("Issue Book");

                seeMoreButton.setOnAction(event -> {
                    Stage stage = (Stage) seeMoreButton.getScene().getWindow();
                    stage.close();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_book_see_more.fxml"));
                        UserBookSeeMoreController nextController = new UserBookSeeMoreController(user, book);
                        loader.setController(nextController);
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                issueBookButton.setOnAction(event -> {
                    Stage stage = (Stage) issueBookButton.getScene().getWindow();
                    stage.close();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("user_issue_book.fxml"));
                        UserIssueBookController nextController = new UserIssueBookController(user, book);
                        loader.setController(nextController);
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                FlowPane buttonsPane = new FlowPane(seeMoreButton, issueBookButton);
                searchedBooksFlowPane.getChildren().addAll(new Label(searchResults.toString()), buttonsPane);
            }
        }
    }
}
