package practice_8;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

/**
 * Created by swanta on 13.08.16.
 */
public class Library {
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private BooksSerializer serializer = new BooksSerializer(books);
    private File libraryFile;

    public Library(File libraryFile) {
        this.libraryFile = libraryFile;
    }

    public boolean loadBooksFromFile (){
        try (FileInputStream fis = new FileInputStream(libraryFile)) {
            BooksSerializer newBooks;
            ObjectInputStream ois = new ObjectInputStream(fis);
            newBooks = (BooksSerializer) ois.readObject();
            this.serializer = newBooks;
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

    public boolean saveBooksToFile () {
        try (FileOutputStream fis = new FileOutputStream(libraryFile)) {
            ObjectOutputStream ois = new ObjectOutputStream(fis);
            ois.writeObject(this.serializer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList getBooks (){
        return books;
    }

    public boolean addBook(String title, String author, String genre, int pagesCount) {
        return books.add(new Book(title, author, genre, pagesCount));
    }
}
