package practice_8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.util.Collection;

/**
 * Created by swanta on 13.08.16.
 */
public class Library {
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private File libraryFile;

    public Library(File libraryFile) {
        this.libraryFile = libraryFile;
    }

    public boolean loadBooksFromFile (){
        try (FileInputStream fis = new FileInputStream(libraryFile)) {
            BookList newBooks;
            ObjectInputStream ois = new ObjectInputStream(fis);
            newBooks = (BookList) ois.readObject();
            return true;
        } catch (IOException e) {
            //"can't access file"
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //"no books were saved earlier"
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList getBooks(){
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }


}
