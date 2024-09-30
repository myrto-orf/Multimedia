package beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class IssueBook implements Serializable{
    private User user;
    private Book book;
    private LocalDate issueDate;
    private boolean returnStatus;
    private static List<IssueBook> issueBookTable = new ArrayList<>();
    private static final long serialVersionUID = 1L;


    public IssueBook(User user, Book book) {
        this.user = user;
        this.book = book;
        this.issueDate = LocalDate.now();
        this.returnStatus = true;
    }

    public boolean getReturnStatus() {
        return returnStatus;
    }
    public void setReturnStatus(boolean returnStatus) {
        this.returnStatus = returnStatus;
    }
    public LocalDate getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() { return book; }

    public void setBook(Book book) {
        this.book = book;
    }

    public static List<IssueBook> getIssueBookTable() {
        return issueBookTable;
    }

    public static void setIssueBookTable (List<IssueBook> issuedBooks) {
        IssueBook.issueBookTable = issuedBooks;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public static void registerIssueBook(User user, Book book) {
        if (user.getIssuedBooks() == 2) {
            System.out.println("User has reached maximum number of issued books.");
            return;
        }

        if (book.getCopies() == 0) {
            System.out.println("Book not available");
            return;
        }

        if (book.getIssued() == book.getCopies()) {
            System.out.println("All book copies are already issued.");
            return;
        }

        for (IssueBook issueBook : getIssueBookTable()) {
            if (issueBook.getUser().getID().equals(user.getID()) &&
                    issueBook.getBook().getISBN().equals(book.getISBN()) &&
                    issueBook.getReturnStatus() == false) {
                System.out.println("Book already issued by user.");
                return;
            }
        }

        IssueBook newIssueBook = new IssueBook(user, book);
        newIssueBook.setIssueDate(LocalDate.now());
        newIssueBook.setReturnStatus(false);
        user.incrementIssuedBooks();
        book.setIssued(book.getIssued() + 1);
        newIssueBook.getIssueBookTable().add(newIssueBook);
    }

    public void ReturnBook() {
        this.setReturnStatus(true);
        for (User user : User.getRegisteredUsers()) {
            if (user.getID().equals(this.getUser().getID())) {
                user.decrementIssuedBooks();
            }
        }
        for (Book book : Book.getBooks()) {
            if (book.getISBN().equals(this.getBook().getISBN())) {
                book.setIssued(this.getBook().getIssued() - 1);
            }
        }
    }

    // Serialization method
    public static void serializeIssueBooks(List<IssueBook> issueBooks) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/medialab/issueBooks.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(issueBooks);
            fileOut.close();
            System.out.println("Serialization successful.");
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Deserialization method
    public static List<IssueBook> deserializeIssueBooks(String f) {
        List<IssueBook> issueBooks = new ArrayList<>();
        try {
            File file = new File(f);
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
                System.out.println("File created: " + f);
                return issueBooks; // Return null as the file is newly created
            }
            if (file.length() == 0) {
                System.out.println("File is empty: " + f);
                return issueBooks; // Return null if the file is empty
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            if (obj instanceof List) {
                issueBooks = (List<IssueBook>) obj;
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
        return issueBooks;
    }

}
