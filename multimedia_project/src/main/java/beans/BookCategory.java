package beans;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Represents a category of books.
 */
public class BookCategory implements Serializable {
    private String title;
    private List<Book> books;
    private static final long serialVersionUID = 1L;
    private static List<BookCategory> categories = new ArrayList<>();

    /**
     * Constructs a BookCategory object with the given title.
     *
     * @param title the title of the book category
     */
    public BookCategory(String title) {
        this.title = title;
        this.books = new ArrayList<>();
    }

    /**
     * Returns the title of the book category.
     *
     * @return the title of the book category
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book category.
     *
     * @param title the new title of the book category
     */
    public void setTitle(String title) {
        this.title = title;
        for (Book book : books) {
            book.setCategory(title);
        }
    }

    /**
     * Returns the list of books in this category.
     *
     * @return the list of books in this category
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Returns the serialVersionUID.
     *
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Returns the list of all book categories.
     *
     * @return the list of all book categories
     */
    public static List<BookCategory> getBookCategories() {
        return categories;
    }

    /**
     * Sets the list of book categories.
     *
     * @param categories the list of book categories
     */
    public static void setBookCategories(List<BookCategory> categories) {
        BookCategory.categories = categories;
    }

    /**
     * Adds a book to this category.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        books.add(book);
        for (Book realbook : Book.getBooks()) {
            if (book.getISBN().equals(realbook.getISBN())) {
                realbook.setCategory(title);
            }
        }
        book.setCategory(title);
    }

    /**
     * Deletes this category and all its books.
     */
    public void deleteCategory() {
        for (Book book : books) {
            for (IssueBook issueBook : IssueBook.getIssueBookTable()) {
                if (issueBook.getBook().getTitle().equals(book.getTitle())) {
                    issueBook.ReturnBook();
                }
            }
            for (Book realbook : Book.getBooks()) {
                if (realbook.getTitle().equals(book.getTitle())) {
                    realbook.deleteBook();
                }
            }
            categories.remove(this);
        }
        books.clear();
    }

    /**
     * Serializes the list of book categories to a file.
     *
     * @param bookCategories the list of book categories to serialize
     */
    public static void serializeBookCategories(List<BookCategory> bookCategories) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/medialab/bookCategories.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bookCategories);
            fileOut.close();
            System.out.println("Serialization successful.");
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deserializes the list of book categories from a file.
     *
     * @param f the filename to deserialize from
     * @return the deserialized list of book categories
     */
    public static List<BookCategory> deserializeBookCategories(String f) {
        List<BookCategory> bookCategories = new ArrayList<>();
        try {
            File file = new File(f);
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
                System.out.println("File created: " + f);
                return bookCategories; // Return null as the file is newly created
            }
            if (file.length() == 0) {
                System.out.println("File is empty: " + f);
                return bookCategories; // Return null if the file is empty
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            if (obj instanceof List) {
                bookCategories = (List<BookCategory>) obj;
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
        return bookCategories;
    }
}
