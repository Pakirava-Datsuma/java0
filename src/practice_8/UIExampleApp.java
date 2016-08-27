package practice_8;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by swanta on 13.08.16.
 */
public class UIExampleApp {
    private static Library testLibrary = Library.createSerializableLibrary("testlibrary.lib");

    public static void main(String[] args) {
//        test();
        Application.launch(LibraryGUI.class, args);
    }

    private static void test() {
        testLibrary.getBooks().clear();
        testAddTestBooks();
        testSerialize();
//        testWriteBooks();
//        testLoadBooks();
//        testShowBooks();

    }

    private static void testShowBooks() {
        System.out.println("-------testShowBooks-----------");
        for (Book book : testLibrary.getBooks()) {
            System.out.println(book.toString());
        }
        System.out.println("books listed");
    }

    public static void testSerialize() {
        System.out.println("-------testSerialize-----------");
        String result;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("testSerialize"));) {
            ArrayList<BookData> list = new ArrayList<>();
            BookData bookData = new BookData("title", "author", "genre", 321) {{
                List<XYChart.Data<String, Number>> list1 = new ArrayList<>();
                list1.add(new XYChart.Data<>("test", 0));
                setReadStatistics(FXCollections.observableList(list1));
            }};
            list.add(bookData);
            oos.writeObject(list);
            oos.writeObject(new ArrayList<BookData>(Book.getBooksData(testLibrary.getBooks())));
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
        testLibrary.getBooks().forEach(Book::setRandomStatistics);
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
