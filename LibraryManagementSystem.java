import java.util.ArrayList;
import java.util.List;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean available;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Reader {
    private String name;
    private List<Book> rentedBooks;

    public Reader(String name) {
        this.name = name;
        this.rentedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Book> getRentedBooks() {
        return rentedBooks;
    }

    public boolean rentBook(Book book) {
        if (book.isAvailable()) {
            rentedBooks.add(book);
            book.setAvailable(false);
            return true;
        }
        return false;
    }

    public void returnBook(Book book) {
        if (rentedBooks.remove(book)) {
            book.setAvailable(true);
        }
    }
}

class Librarian {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    public void addBook(Library library, Book book) {
        library.addBook(book);
    }

    public void removeBook(Library library, Book book) {
        library.removeBook(book);
    }

    public void searchBook(Library library, String searchQuery) {
        List<Book> foundBooks = library.findBooks(searchQuery);
        if (foundBooks.isEmpty()) {
            System.out.println("Knigi ne naideny.");
        } else {
            for (Book book : foundBooks) {
                System.out.println(book.getTitle() + " - " + book.getAuthor());
            }
        }
    }

    public void listAllBooks(Library library) {
        library.listAllBooks();
    }
}

class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public Book findBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> findBooks(String searchQuery) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().contains(searchQuery) || book.getAuthor().contains(searchQuery)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public void listAllBooks() {
        for (Book book : books) {
            System.out.println(book.getTitle() + " - " + book.getAuthor() + " (" + (book.isAvailable() ? "dostupna" : "na arende") + ")");
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Librarian librarian = new Librarian("Nari");

        Book book1 = new Book("Voina i mir", "Lev ", "1");
        Book book2 = new Book("Prestuplenie i nakazanie", "Dostaevskii ", "2");
        librarian.addBook(library, book1);
        librarian.addBook(library, book2);

        Reader reader = new Reader("Manat");

        System.out.println("Arendavat 'Voina i mir ': " + (reader.rentBook(book1) ? "Uspeshno" : "Nu udalos"));
        System.out.println("Knigi arendovanniye: ");
        for (Book book : reader.getRentedBooks()) {
            System.out.println(book.getTitle());
        }

        reader.returnBook(book1);
        System.out.println("Knigi arendovanniye posle vozvrata: ");
        for (Book book : reader.getRentedBooks()) {
            System.out.println(book.getTitle());
        }

        System.out.println("Poisk 'Prestuplenie':");
        librarian.searchBook(library, "Prestuplenie");

        System.out.println("Vse knigi v biblioteke:");
        librarian.listAllBooks(library);
    }
}
