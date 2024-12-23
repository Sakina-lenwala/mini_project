import java.util.ArrayList;
import java.util.Scanner;

class MemberLimitExceededException extends Exception {
    public MemberLimitExceededException(String message) {
        super(message);
    }
}

class BookLimitExceededException extends Exception {
    public BookLimitExceededException(String message) {
        super(message);
    }
}

interface LibraryItem {
    String getTitle();
    String getAuthor();
    String getIsbn();
}

abstract class User {
    protected String name;
    protected String memberId;

    public User(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    public String getName() { return name; }
    public String getMemberId() { return memberId; }


    public String toString() {
        return "Name: " + name + ", ID: " + memberId;
    }
}

class Book implements LibraryItem {
    private String title;
    private String author;
    private String isbn;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String getTitle() { return title; }
    @Override
    public String getAuthor() { return author; }
    @Override
    public String getIsbn() { return isbn; }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn;
    }
}

class LibraryMember extends User {
    public LibraryMember(String name, String memberId) {
        super(name, memberId);
    }
}

class Library {
    private ArrayList<LibraryItem> books;
    private ArrayList<User> members;
    private static final String OWNER_KEY = "1357";
    private static final int MEMBER_LIMIT = 3;
    private static final int BOOK_LIMIT = 15;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();


        books.add(new Book("Effective Java", "Joshua Bloch", "978-0134685991"));
        books.add(new Book("Clean Code", "Robert C. Martin", "978-0132350884"));
        books.add(new Book("Design Patterns", "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "978-0201633610"));
        books.add(new Book("Java Concurrency in Practice", "Brian Goetz", "978-0321349606"));
        books.add(new Book("Head First Java", "Kathy Sierra, Bert Bates", "978-0596009205"));


        members.add(new LibraryMember("Aliasgar Cyclewala", "M001"));
        members.add(new LibraryMember("Sakina Lenwala", "M002"));
        members.add(new LibraryMember("Husen Sathalia", "M003"));
    }

    public void addBook(String title, String author, String isbn, String enteredKey) throws BookLimitExceededException {
        if (books.size() >= BOOK_LIMIT) {
            throw new BookLimitExceededException("Cannot add more books. Book limit reached.");
        }
        if (enteredKey.equals(OWNER_KEY)) {
            books.add(new Book(title, author, isbn));
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Error: You cannot add a book as you are not the owner.");
        }
    }

    public void addMember(String name, String memberId) throws MemberLimitExceededException {
        if (members.size() >= MEMBER_LIMIT) {
            throw new MemberLimitExceededException("Cannot add more members. Member limit reached.");
        }
        members.add(new LibraryMember(name, memberId));
        System.out.println("Member added successfully.");
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("Books in the library:");
            for (LibraryItem item : books) {
                System.out.println(item);
            }
        }
    }

    public void displayMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            System.out.println("Library members:");
            for (User user : members) {
                System.out.println(user);
            }
        }
    }
}

public class LibrarySystem {
    static final String OWNER_KEY = "1357"; // Updated secret key for owner

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        int option;

        do {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Add a new member");
            System.out.println("2. Add a new book (Owner only)");
            System.out.println("3. Display all books");
            System.out.println("4. Display all members");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (option) {
                    case 1:
                        System.out.print("Enter member name: ");
                        String memberName = scanner.nextLine();
                        System.out.print("Enter member ID: ");
                        String memberId = scanner.nextLine();
                        library.addMember(memberName, memberId);
                        break;

                    case 2:
                        System.out.print("Enter the secret key (Owner only): ");
                        String enteredKey = scanner.nextLine();
                        if (!enteredKey.equals(OWNER_KEY)) {
                            System.out.println("Sorry....You are not an Owner");
                            System.out.println("You cannot add a new Book!!");
                            break;
                        }
                        System.out.print("Enter book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter book author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter book ISBN: ");
                        String isbn = scanner.nextLine();
                        library.addBook(title, author, isbn, enteredKey);
                        break;

                    case 3:
                        library.displayBooks();
                        break;

                    case 4:
                        library.displayMembers();
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
            catch (MemberLimitExceededException | BookLimitExceededException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (option != 5);

        scanner.close();
    }
}
