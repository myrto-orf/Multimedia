package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.*;

public class User implements Serializable {
    private String username, password, name, surname, ID, email;
    private static List<User> registeredUsers = new ArrayList<>();
    private int issuedBooks;
    private static final long serialVersionUID = 1L;

    public User(String username, String password, String name, String surname, String ID, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.ID = ID;
        this.email = email;
        this.issuedBooks = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname (String surname) {
        this.surname = surname;
    }

    public String getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public int getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(int issuedBooks) {
        this.issuedBooks = issuedBooks;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public static void setRegisteredUsers(List<User> users) {
        User.registeredUsers = users;
    }

    public void incrementIssuedBooks() {
        this.issuedBooks++;
    }

    public void decrementIssuedBooks() {
        if (issuedBooks > 0) {
            this.issuedBooks--;
        }
    }

    public static User registerUser(String username, String password, String name, String surname, String ID, String email) {
        User newUser = new User(username, password, name, surname, ID, email);
        User.getRegisteredUsers().add(newUser);
        return newUser;
    }

    public void deleteUser() {
        for (IssueBook issueBook : IssueBook.getIssueBookTable()) {
            if (issueBook.getUser().equals(this)) {
                issueBook.ReturnBook();
            }
        }
        this.setIssuedBooks(0);
        this.getRegisteredUsers().remove(this);
    }

    public List<Book> searchBooksByTitle (String title) {
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : Book.getBooks()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }

    public List<Book> searchBooksByAuthor (String author) {
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : Book.getBooks()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }

    public List<Book> searchBooksByPublicationYear (int publicationYear) {
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : Book.getBooks()) {
            if (book.getPublicationYear() == publicationYear) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }

    public List<Book> searchBooksByTitleAuthor (String title, String author) {
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : Book.getBooks()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                    book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }

    public List<Book> searchBooksByTitlePublicationYear (String title, int publicationYear) {
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : Book.getBooks()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                    book.getPublicationYear() == publicationYear) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }

    public List<Book> searchBooksByAuthorPublicationYear (String author, int publicationYear) {
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : Book.getBooks()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase()) &&
                    book.getPublicationYear() == publicationYear) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }

    public List<Book> searchBooksByTitleAuthorPublicationYear (String title, String author, int publicationYear) {
        List<Book> searchedBooks = new ArrayList<>();
        for (Book book : Book.getBooks()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                    book.getAuthor().toLowerCase().contains(author.toLowerCase()) &&
                    book.getPublicationYear() == publicationYear) {
                searchedBooks.add(book);
            }
        }
        return searchedBooks;
    }

    public void issueBook (Book book) {
        for (User user: User.getRegisteredUsers()) {
            if (user.getID().equals(this.getID())) {
                IssueBook.registerIssueBook(this, book);
            }
        }
    }

    public List<IssueBook> viewIssuedBooks() {
        List<IssueBook> issuedBooks = new ArrayList<>();
        if (IssueBook.getIssueBookTable() != null) {
            for (IssueBook issueBook : IssueBook.getIssueBookTable()) {
                if (issueBook.getUser().getID().equals(this.getID())) {
                    issuedBooks.add(issueBook);
                }
            }
        }
        return issuedBooks;
    }

    public void addUserComment(Book book, String comment) {
        if (book != null) {
            for (IssueBook borrowing : IssueBook.getIssueBookTable()) {
                if (borrowing.getUser().getID().equals(this.getID()) && borrowing.getBook().getISBN().equals(book.getISBN())) {
                    book.addComment(comment);
                    System.out.println("Comment added");
                }
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    public void addUserRating(Book book, Integer rating) {
        if (book != null) {
            if (rating >= 1 && rating <= 5) {
                for (IssueBook borrowing : IssueBook.getIssueBookTable()) {
                    if (borrowing.getUser().getID().equals(this.getID()) && borrowing.getBook().getISBN().equals(book.getISBN())) {
                        book.addRating(rating);
                        System.out.println("Rating added");
                    }
                }
            } else {
                System.out.println("Rating must be between 1 and 5");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    // Serialization method
    public static void serializeUsers(List<User> users) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/medialab/users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            fileOut.close();
            System.out.println("Serialization successful.");
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Deserialization method
    public static List<User> deserializeUsers(String f) {
        List<User> users = new ArrayList<>();
        try {
            File file = new File(f);
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
                System.out.println("File created: " + f);
                return users; // Return null as the file is newly created
            }
            if (file.length() == 0) {
                System.out.println("File is empty: " + f);
                return users; // Return null if the file is empty
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            if (obj instanceof List) {
                users = (List<User>) obj;
            }
            in.close();
            fileIn.close();
            System.out.println("Deserialization successful.");
        } catch (IOException c) {
            System.out.println("Error during serialization: " + c.getMessage());
            c.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Book class not found");
            e.printStackTrace();
        }
        return users;
    }

}

