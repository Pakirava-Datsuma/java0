package practice_8;

import javafx.application.Application;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;

/**
 * Created by swanta on 13.08.16.
 */
public class UIExampleApp {
    private static Library testLibrary = new Library();

    public static void main(String[] args) {
//        test();
        Application.launch(LibraryGUI.class, args);
    }

    private static void test() {
        //        testSerialize();
//        testLibrary.getBooks().clear();
//        testAddTestBooks();
//        testWriteBooks();
//        testLoadBooks();
        testShowBooks();

    }

    private static void testShowBooks() {
        System.out.println("-------testShowBooks-----------");
        for (Book book : testLibrary.getBooks()) {
            System.out.println(book.toString());
        }
    }

    public static void testSerialize() {
        System.out.println("-------testSerialize-----------");
        try (ObjectOutputStream oos = new ObjectOutputStream(System.out);) {
            oos.writeObject(
                    new HashSet() {
                        {
                            new Book("title", "author", "genre", 321) {{
                                addTodayReadPagesQuantity(12);
                            }};
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testAddTestBooks() {
        System.out.println("-------testAddTestBooks-----------");
        testLibrary.addBook("H.Potter", "J.Roulling", "fantasy", 321);
        testLibrary.addBook("Diving into C++", "H.Deiteil, R.Deiteil", "study", 810);
        testLibrary.getBooks().stream().forEach(Book::setRandomStatistics);
        System.out.println("total books in library: " + testLibrary.getBooks().size());
    }
    public static void testWriteBooks() {
        System.out.println("-------testWriteBooks-----------");
        testLibrary.saveBooksToFile();
        System.out.println("test book set: " + (testLibrary.isFileLoaded() ? "written ok" : "write FAILED"));
    }

    private static void testLoadBooks() {
        System.out.println("-------testLoadBooks-----------");
        testLibrary.loadBooksFromFile();
        System.out.println("test book set: " + (testLibrary.isFileLoaded() ? "loaded ok" : "load FAILED"));
    }
}
