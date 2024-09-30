package com.example.multimedia_project;

import beans.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LibrarianLoginController {
    @FXML
    private TextField librarianUsernameText;

    @FXML
    private TextField librarianPasswordText;
    @FXML
    private Button librarianLoginButton;
    @FXML
    private Label errorMessageLabel;
    public Librarian librarian;

    @FXML
    private void handleLibrarianLogin(ActionEvent event) {
        String username = librarianUsernameText.getText();
        String password = librarianPasswordText.getText();


        Librarian authenticatedLibrarian = authenticateLibrarian(username, password);
        if (authenticatedLibrarian != null) {
            librarian = authenticatedLibrarian;
            Stage stage = (Stage) librarianUsernameText.getScene().getWindow();
            stage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_interface.fxml"));
                LibrarianInterfaceController nextController = new LibrarianInterfaceController(librarian);
                loader.setController(nextController);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            errorMessageLabel.setText("Username or password is incorrect");
        }
    }

    public Librarian authenticateLibrarian(String username, String password) {
        if (Librarian.getLibrarians() == null) {
            return null;
        }
        for (Librarian librarian : Librarian.getLibrarians()) {
            if (librarian.getName().equals(username) && librarian.getPassword().equals(password)) {
                return librarian;
            }
        }
        return null;
    }
}
