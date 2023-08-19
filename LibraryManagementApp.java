import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author;
    }
}

class Library {
    List<Book> books;
    private static final String FILE_PATH = "books.txt"; // Provide an explicit path

    public Library() {
        books = new ArrayList<>();
        loadBooks(); // Load books from file when the library is created
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayBooks() {
        System.out.println("Books in the library:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    public void deleteBook(int index) {
        if (index >= 0 && index < books.size()) {
            books.remove(index);
            System.out.println("Book deleted.");
        } else {
            System.out.println("Invalid book index.");
        }
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public void saveBooks() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                writer.println(book.getTitle() + "," + book.getAuthor());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    books.add(new Book(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class LibraryManagementApp {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            library.saveBooks();
            System.out.println("Books saved.");
        }));

        while (true) {
            System.out.println("Select mode:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int mode = scanner.nextInt();

            if (mode == 1) {
                System.out.println("Admin Mode");
                adminMode(library, scanner);
            } else if (mode == 2) {
                System.out.println("User Mode");
                userMode(library, scanner);
            } else if (mode == 3) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }


    public static void adminMode(Library library, Scanner scanner) {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Delete Book");
            System.out.println("4. Exit Admin Mode");
            System.out.print("Enter your choice: ");
            int adminChoice = scanner.nextInt();

            if (adminChoice == 1) {
                scanner.nextLine();  // Consume newline
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                System.out.print("Enter book author: ");
                String author = scanner.nextLine();
                library.addBook(new Book(title, author));
                System.out.println("Book added.");
            } else if (adminChoice == 2) {
                library.displayBooks();
            } else if (adminChoice == 3) {
                System.out.print("Enter the index of the book to delete: ");
                int index = scanner.nextInt() - 1;
                library.deleteBook(index);
            } else if (adminChoice == 4) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public static void userMode(Library library, Scanner scanner) {
        while (true) {
            System.out.println("User Menu:");
            System.out.println("1. Search and Issue Book");
            System.out.println("2. Display Books");
            System.out.println("3. Exit User Mode");
            System.out.print("Enter your choice: ");
            int userChoice = scanner.nextInt();

            if (userChoice == 1) {
                scanner.nextLine();  // Consume newline
                System.out.print("Enter book title to search: ");
                String title = scanner.nextLine();
                Book foundBook = library.findBook(title);
                if (foundBook != null) {
                    System.out.println("Book found: " + foundBook);
                    library.deleteBook(library.books.indexOf(foundBook));
                } else {
                    System.out.println("Book not found.");
                }
            } else if (userChoice == 2) {
                library.displayBooks();
            } else if (userChoice == 3) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}