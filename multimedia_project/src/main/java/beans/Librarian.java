package beans;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Librarian implements Serializable{
    private int id;
    private String name, email, password;
    private static final long serialVersionUID = 1L;
    private static List<Librarian> librarians = new ArrayList<>();


    public Librarian(int id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static List<Librarian> getLibrarians() {
        return librarians;
    }

    public static void setLibrarians(List<Librarian> librarians) {
        Librarian.librarians = librarians;
    }

    public static Librarian registerLibrarian(int ID, String name, String password, String email) {
        for (Librarian librarian : Librarian.getLibrarians()) {
            if (librarian.getName().equals(name) || librarian.getId() == ID || librarian.getEmail().equals(email)) {
                System.out.println("Librarian is already registered.");
                return null;
            }
        }

        Librarian newLibrarian = new Librarian(ID, name, password, email);
        Librarian.getLibrarians().add(newLibrarian);
        return newLibrarian;
    }

    public static void deleteLibrarian(Librarian librarian) {
        librarian.getLibrarians().remove(librarian);
    }

    public void addBook (String title, String author, String publisher, String ISBN, int publicationYear, String category, int copies) {
        Book newBook = new Book(title, author, publisher, ISBN, publicationYear, category, copies);
        newBook.addBook();
        for (BookCategory bookCategory : BookCategory.getBookCategories()) {
            if (bookCategory.getTitle().equals(category)) {
                bookCategory.addBook(newBook);
            }
        }
        BookCategory newCategory = new BookCategory(category);
        newCategory.getBooks().add(newBook);
        BookCategory.getBookCategories().add(newCategory);
    }

    public void deleteBook (Book book) {
        book.deleteBook();
    }

    public void editBook (String ISBN, String newTitle, String newAuthor, String newPublisher, int newPublicationYear, int newCopies) {
        Book bookToEdit = null;

        for (Book book : Book.getBooks()) {
            if (book.getISBN().equals(ISBN)) {
                bookToEdit = book;
                break;
            }
        }
        if (bookToEdit == null) {
            System.out.println("Book not found.");
        }
        else {
            bookToEdit.setTitle(newTitle);
            bookToEdit.setAuthor(newAuthor);
            bookToEdit.setPublisher(newPublisher);
            bookToEdit.setPublicationYear(newPublicationYear);
            bookToEdit.setCopies(newCopies);
            System.out.println("Book edited successfully.");
        }
    }

    public void editBookCategory (String ISBN, String newCategory) {
        for (BookCategory category : BookCategory.getBookCategories()) {
            for (Book book : category.getBooks()) {
                if (book.getISBN().equals(ISBN)) {
                    category.getBooks().remove(book);

                    for (BookCategory newCategoryObj : BookCategory.getBookCategories()) {
                        if (newCategoryObj.getTitle().equals(newCategory)) {
                            newCategoryObj.getBooks().add(book);
                            book.setCategory(newCategory);
                            for (Book realbook : Book.getBooks()) {
                                if (realbook.getISBN().equals(book.getISBN())) {
                                    realbook.setCategory(newCategory);
                                }
                            }
                            return;
                        }
                    }

                    // If the newCategory doesn't exist, create it
                    BookCategory newCategoryObj = new BookCategory(newCategory);
                    newCategoryObj.getBooks().add(book);
                    BookCategory.getBookCategories().add(newCategoryObj);
                    book.setCategory(newCategory);
                    for (Book realbook : Book.getBooks()) {
                        if (realbook.getISBN().equals(book.getISBN())) {
                            realbook.setCategory(newCategory);
                        }
                    }
                    return;
                }
            }
        }
        System.out.println("Book with ISBN " + ISBN + " not found in any category.");
    }

    public void updateCategory (String oldCategory, String newCategory) {
        for (BookCategory category : BookCategory.getBookCategories()) {
            if (category.getTitle().equals(oldCategory)) {
                category.setTitle(newCategory);
                for (Book book : category.getBooks()) {
                    for (Book realbook : Book.getBooks()) {
                        if (book.getISBN().equals(realbook.getISBN())) {
                            realbook.setCategory(newCategory);
                        }
                    }
                    book.setCategory(newCategory);
                }
                System.out.println("Category '" + oldCategory + "' changed to '" + newCategory);
            } else {
                System.out.println("Category '" + oldCategory + "not found" + newCategory);
            }
        }
    }

    public void addCategory (String category) {
        BookCategory newBookCategory = new BookCategory(category);
        BookCategory.getBookCategories().add(newBookCategory);
    }

    public void removeCategory (String categoryToRemove) {
        for (BookCategory category : BookCategory.getBookCategories()) {
            if (category.getTitle().equals(categoryToRemove)) {
                category.deleteCategory();
                return;
            }
        }
        System.out.println("Category '" + categoryToRemove + "' not found.");
    }

    public void deleteUser (String ID) {
        for (User user : User.getRegisteredUsers()) {
            if (user.getID().equals(ID)) {
                user.deleteUser();
            }
        }
    }

    public void editUser (String ID, String newUsername, String newName, String newSurname, String newEmail) {
        for (User user : User.getRegisteredUsers()) {
            if (user.getID().equals(ID)) {
                user.setUsername(newUsername);
                user.setName(newName);
                user.setSurname(newSurname);
                user.setEmail(newEmail);
            }
        }
    }

    public void viewUserIssuedBooks (User user) {
        for (User u : User.getRegisteredUsers()) {
            if (u.getUsername().equals(user)) {
                u.viewIssuedBooks();
            }
        }
    }

    public void viewAllUserIssuedBooks () {
        for (User user : User.getRegisteredUsers()) {
            user.viewIssuedBooks();
        }
    }

    public void terminateBorrowing (User user, Book book) {
        for (IssueBook borrowing: IssueBook.getIssueBookTable()) {
            if (borrowing.getUser().getID().equals(user.getID()) && borrowing.getBook().getISBN().equals(book.getISBN())) {
                borrowing.ReturnBook();
            }
        }
    }

    // Serialization method
    public static void serializeLibrarians(List<Librarian> librarians) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/medialab/librarians.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(librarians);
            fileOut.close();
            System.out.println("Serialization successful.");
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Deserialization method
    public static List<Librarian> deserializeLibrarian(String f) {
        List<Librarian> librarians = new ArrayList<>();
        try {
            File file = new File(f);
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
                System.out.println("File created: " + f);
                return librarians; // Return null as the file is newly created
            }
            if (file.length() == 0) {
                System.out.println("File is empty: " + f);
                return librarians; // Return null if the file is empty
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            if (obj instanceof List) {
                librarians = (List<Librarian>) obj;
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
        return librarians;
    }

}