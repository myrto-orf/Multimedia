package com.example.multimedia_project;

import beans.*;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Welcome to E-Library!");
        Image icon = new Image("file:src/icon.png");
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            Book.serializeBooks(Book.getBooks());
            User.serializeUsers(User.getRegisteredUsers());
            BookCategory.serializeBookCategories(BookCategory.getBookCategories());
            IssueBook.serializeIssueBooks(IssueBook.getIssueBookTable());
            Librarian.serializeLibrarians(Librarian.getLibrarians());
        });
    }

    public static void main(String[] args) {
        Book.setBooks(Book.deserializeBooks("src/main/medialab/books.ser"));
        User.setRegisteredUsers(User.deserializeUsers("src/main/medialab/users.ser"));
        BookCategory.setBookCategories(BookCategory.deserializeBookCategories("src/main/medialab/bookCategories.ser"));
        IssueBook.setIssueBookTable(IssueBook.deserializeIssueBooks("src/main/medialab/issueBooks.ser"));
        Librarian.setLibrarians(Librarian.deserializeLibrarian("src/main/medialab/librarians.ser"));

        Librarian.registerLibrarian(12345,"medialab", "medialab_2024", "myrtoorfan@gmail.com");

        launch();
    }
}