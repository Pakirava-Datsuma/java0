package practice_8;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;

/**
 * Created by swanta on 13.08.16.
 */
public class UIExampleApp {
    static Library testLibrary = new Library();

    public static void main(String[] args) {
        test();
//        Application.launch(LibraryGUI.class, args);
    }

    private static void test() {
        //        testSerialize();
        testWriteTestBooks();
        testLoadBooks();
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

    public static void testWriteTestBooks() {
        System.out.println("-------testWriteTestBooks-----------");
        testLibrary.addBook("H.Potter", "J.Roulling", "fantasy", 321);
        testLibrary.addBook("Diving into C++", "H.Deiteil, R.Deiteil", "study", 810);
        testLibrary.saveBooksToFile();
        System.out.println("test book set: " + (testLibrary.isFileLoaded() ? "written ok" : "write FAILED"));
    }

    private static void testLoadBooks() {
        System.out.println("-------testLoadBooks-----------");
        testLibrary.loadBooksFromFile();
        System.out.println("test book set: " + (testLibrary.isFileLoaded() ? "loaded ok" : "load FAILED"));
    }
}
