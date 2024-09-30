package com.example.multimedia_project;

import beans.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class LibrarianInterfaceController {

    private Librarian librarian;
    @FXML
    private Button addBookButton;
    @FXML
    private Button addCategoryButton;
    @FXML
    private ListView categoryList;
    @FXML
    private ListView userList;
    @FXML
    private ListView issueBookList;
    @FXML
    private TreeView<String> bookTreeView;


    public LibrarianInterfaceController (Librarian librarian) {
        this.librarian = librarian;
    }

    @FXML
    private void initialize () {
        initializeTreeView();
        initializeCategoryList();
        initializeUserList();
        initializeIssueBooksList();
    }

    @FXML
    private void addBook() {
        if (librarian != null) {
            Stage stage = (Stage) bookTreeView.getScene().getWindow();
            stage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_add_book.fxml"));
                LibrarianAddBookController nextController = new LibrarianAddBookController(librarian);
                loader.setController(nextController);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void initializeTreeView() {
        TreeItem<String> root = new TreeItem<>("Categories");
        bookTreeView.setRoot(root);

        if (BookCategory.getBookCategories().isEmpty()) {
            System.out.println("No categories found");
        }

        root.getChildren().clear();

        for (BookCategory category : BookCategory.getBookCategories()) {
            displayBooksInCategory(category, root);
        }

        bookTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.isLeaf()) {
                Book selectedBook = getBookFromTitle(newValue.getValue());
                if (selectedBook != null) {
                    seeMoreForBook(selectedBook);
                }
            }
        });
    }

    private void seeMoreForBook(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_book_see_more.fxml"));
            LibrarianBookSeeMoreController nextController = new LibrarianBookSeeMoreController(librarian, book);
            loader.setController(nextController);
            Parent root = loader.load();
            Stage stage = (Stage) bookTreeView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Book getBookFromTitle(String title) {
        for (BookCategory category : BookCategory.getBookCategories()) {
            for (Book book : category.getBooks()) {
                if (book.getTitle().equals(title)) {
                    for (Book realbook : Book.getBooks()) {
                        if (realbook.getTitle().equals(book.getTitle())){
                            return realbook;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void displayBooksInCategory(BookCategory category, TreeItem<String> root) {
        TreeItem<String> categoryNode = findOrCreateCategoryNode(category.getTitle(), root);

        for (Book book : category.getBooks()) {
            boolean bookExists = categoryNode.getChildren().stream().anyMatch(node -> node.getValue().equals(book.getTitle()));
            if (!bookExists) {
                TreeItem<String> bookItem = new TreeItem<>(book.getTitle());

                bookItem.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getClickCount() == 1) {
                        seeMoreForBook(book);
                    }
                });

                categoryNode.getChildren().add(bookItem);
            }
        }
    }

    private TreeItem<String> findOrCreateCategoryNode(String categoryTitle, TreeItem<String> root) {
        for (TreeItem<String> node : root.getChildren()) {
            if (node.getValue().equals(categoryTitle)) {
                return node;
            }
        }

        TreeItem<String> newCategoryNode = new TreeItem<>(categoryTitle);
        root.getChildren().add(newCategoryNode);
        return newCategoryNode;
    }

    @FXML
    private void initializeCategoryList() {
        Set<String> addedCategories = new HashSet<>();

        for (BookCategory category : BookCategory.getBookCategories()) {
            if (!addedCategories.contains(category.getTitle())) {
                Label categoryLabel = new Label(category.getTitle());
                categoryLabel.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_category_see_more.fxml"));
                        LibrarianCategorySeeMoreController nextController = new LibrarianCategorySeeMoreController(librarian, category);
                        loader.setController(nextController);
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) categoryList.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                categoryList.getItems().add(categoryLabel);
                addedCategories.add(category.getTitle());
            }
        }
    }

    @FXML
    private void addCategory () {
        Stage stage = (Stage) addCategoryButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_add_category.fxml"));
            LibrarianAddCategoryController nextController = new LibrarianAddCategoryController(librarian);
            loader.setController(nextController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initializeUserList () {
        for (User user : User.getRegisteredUsers()) {
            Label userLabel = new Label(user.getUsername());
            userLabel.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_user_see_more.fxml"));
                    LibrarianUserSeeMoreController nextController = new LibrarianUserSeeMoreController(librarian, user);
                    loader.setController(nextController);
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) categoryList.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            userList.getItems().add(userLabel);
        }
    }

    @FXML
    private void initializeIssueBooksList () {
        for (IssueBook issueBook : IssueBook.getIssueBookTable()) {
            if (issueBook.getReturnStatus() == false) {
                Label issueBookLabel = new Label("User: " + issueBook.getUser().getUsername() + " Book: " + issueBook.getBook().getTitle());
                issueBookLabel.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_issue_book_see_more.fxml"));
                        LibrarianIssueBookSeeMoreController nextController = new LibrarianIssueBookSeeMoreController(librarian, issueBook);
                        loader.setController(nextController);
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) categoryList.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                issueBookList.getItems().add(issueBookLabel);
            }
        }
    }
}
