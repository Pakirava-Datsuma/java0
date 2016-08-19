package practice_8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

/**
 * Created by swanta on 13.08.16.
 */
public class Library {
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private BookList booksData = new BookList(books);
    private File libraryFile;

    public Library(File libraryFile) {
        this.libraryFile = libraryFile;
    }

    public boolean loadBooksFromFile (){
        try (FileInputStream fis = new FileInputStream(libraryFile)) {
            BookList newBooks;
            ObjectInputStream ois = new ObjectInputStream(fis);
            newBooks = (BookList) ois.readObject();
            this.booksData = newBooks;
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
            ois.writeObject(this.booksData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList getBooks(){
        return books;
    }

    public void addBook(String title, String author, String genre, int pagesCount) {
        Book newBook = new Book(title, author, genre, pagesCount);
        books.add(newBook);
        booksData.add(newBook.getData());
    }
}
