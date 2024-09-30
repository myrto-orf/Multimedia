package beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.*;
import java.util.stream.Collectors;

public class Book implements Serializable {
    private String title, author, publisher, ISBN, category;
    private int publicationYear, copies, issued;
    private List<Integer> ratings;
    private double averageRating;
    private List<String> comments;
    private static final long serialVersionUID = 1L;
    private static List<Book> books = new ArrayList<>();

    public Book(String title, String author, String publisher, String ISBN, int publicationYear, String category, int copies) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.category = category;
        this.copies = copies;
        this.averageRating = 0;
        this.ratings = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.issued = 0;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getCopies() {
        return copies;
    }

    public void setIssued(int issued) {
        this.issued = issued;
    }

    public int getIssued() {
        return issued;
    }

    public List<String> getComments() {return comments ;}

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<Integer> getRatings() {return ratings;}

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
        updateAverageRating();
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static List<Book> getBooks() {
        return books;
    }

    public static void setBooks(List<Book> books) {
        Book.books = books;
    }

    public void addBook() {
        books.add(this);
    }

    public void deleteBook() {
        this.setIssued(0);
        this.setCopies(0);

        // remove book from issued books list
        List<IssueBook> issuesToRemove = new ArrayList<>();
        for (IssueBook issueBook : IssueBook.getIssueBookTable()) {
            if (issueBook.getBook().equals(this)) {
                issuesToRemove.add(issueBook);
            }
        }
        IssueBook.getIssueBookTable().removeAll(issuesToRemove);

        // Remove the book from the specified category
        String bookCategory = this.getCategory();
        for (BookCategory category : BookCategory.getBookCategories()) {
            if (category.getTitle().equals(bookCategory)) {
                List<Book> categoryBooks = category.getBooks();
                if (categoryBooks.contains(this)) {
                    categoryBooks.remove(this);
                    break;
                }
            }
        }

        // remove book from book list
        books.remove(this);
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void updateAverageRating() {
        if (ratings.isEmpty()) {
            averageRating = 0.0;
            return;
        }
        int sum = 0;
        for (Integer rating : ratings) {
            sum += rating;
        }
        averageRating = (double) sum / ratings.size();
    }

    public void addRating(int rating) {
        ratings.add(rating);
        updateAverageRating();
    }

    public static List<Book> viewTopFiveRatedBooks () {
        List<Book> allBooks = Book.getBooks();
        if (allBooks.isEmpty()) {
            return null;
        }

        List<Book> topFiveBooks = allBooks.stream()
                .sorted(Comparator.comparingDouble(Book::getAverageRating).reversed())
                .limit(5)
                .collect(Collectors.toList());
        return topFiveBooks;
    }


    // Serialization method
    public static void serializeBooks(List<Book> books) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/medialab/books.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(books);
            fileOut.close();
            System.out.println("Serialization successful.");
        } catch (IOException e) {
            System.out.println("Error during serialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Deserialization method
    public static List<Book> deserializeBooks(String f) {
        List<Book> books = new ArrayList<>();
        try {
            File file = new File(f);
            if (!file.exists()) {
                file.createNewFile(); // Create the file if it doesn't exist
                System.out.println("File created: " + f);
                return books; // Return null as the file is newly created
            }
            if (file.length() == 0) {
                System.out.println("File is empty: " + f);
                return books; // Return null if the file is empty
            }
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            if (obj instanceof List) {
                books = (List<Book>) obj;
            }
            in.close();
            fileIn.close();
            System.out.println("Deserialization successful.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during deserialization: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

}
