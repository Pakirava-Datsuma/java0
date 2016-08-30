package practice_8;

import javafx.application.Application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by swanta on 29.08.16.
 */
public class LibraryTest {
    private final static String TEST_LIBRARY_FILENAME = "testlibrary.lib";
    private static Library testLibrary = Library.createSerializableLibrary(TEST_LIBRARY_FILENAME);

    public static void main(String[] args) {
        testLibrary.getBooks().clear();
        testAddTestBooks();
        testSerialize();
        testWriteBooks();
//        testLoadBooks();
//        testShowBooks();

    }

    private static void testShowBooks() {
        System.out.println("-------testShowBooks-----------");
        for (Book book : testLibrary.getBooks()) {
            System.out.println(book.toString());
        }
        System.out.println("observableBooks listed");
    }

    public static void testSerialize() {
        System.out.println("-------testSerialize-----------");
        String result;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("testSerialize"));) {
            ArrayList<Book> list = new ArrayList<>();
            Book book = new Book("title", "author", "genre", 321);
//            {{
//                List<XYChart.Data<String, Number>> list1 = new ArrayList<>();
//                list1.add(new XYChart.Data<>("test", 0));
//                setReadStatistics(FXCollections.observableList(list1));
//            }};
            list.add(book);
            oos.writeObject(list);
            oos.writeObject(new ArrayList<Book>(testLibrary.getBooks()));
            result = "ok";
        } catch (IOException e) {
            e.printStackTrace();
            result = "FAULT";
        }
        System.out.println("test: " + result);
    }

    public static void testAddTestBooks() {
        System.out.println("-------testAddTestBooks-----------");
        testLibrary.addBook("H.Potter", "J.Roulling", "fantasy", 321);
        testLibrary.addBook("Diving into C++", "H.Deiteil, R.Deiteil", "study", 810);
//        testLibrary.getBooks().forEach(Book::setRandomStatistics);
        System.out.println("total observableBooks in library: " + testLibrary.getBooks().size());
    }

    public static void testWriteBooks() {
        System.out.println("-------testWriteBooks-----------");
        System.out.println("test book set: " + (Library.saveLibrary(testLibrary) ? "written ok" : "write FAILED"));
    }

    private static void testLoadBooks() {
        System.out.println("-------testLoadBooks-----------");
        testLibrary = Library.loadLibrary(new File(TEST_LIBRARY_FILENAME));
        System.out.println("test book set: " + (testLibrary != null ? "loaded ok" : "load FAILED"));
    }
}

