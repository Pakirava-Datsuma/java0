package practice_8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by swanta on 13.08.16.
 */
public class Library {
    private ObservableList<Book> books = FXCollections.observableArrayList();
    private List<Book> bookList = books;

    private File libraryFile = new File("testLibrary.lib");
    private boolean isFileLoaded = false;

    public Library() {
        if (this.libraryFile.exists()) {
            loadBooksFromFile();
        }
    }

    public boolean loadBooksFromFile (){
        isFileLoaded = false;
        try (FileInputStream fis = new FileInputStream(libraryFile)) {
            Collection<BookData> bookData = new ArrayList();
            ObjectInputStream ois = new ObjectInputStream(fis);
            bookData = (ArrayList) ois.readObject();
            this.books.setAll(Book.getBooks(bookData));
            isFileLoaded = true;
        } catch (IOException e) {
            //"can't access file"
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //"no books were saved earlier"
            e.printStackTrace();
        }
        return isFileLoaded;
    }

    public boolean saveBooksToFile () {
//        TODO: overwrite exists data
        isFileLoaded = false;
        try (FileOutputStream fis = new FileOutputStream(libraryFile)) {
            ObjectOutputStream ois = new ObjectOutputStream(fis);
            Collection<BookData> bookData = Book.getBooksData(books);
            ois.writeObject(bookData);
            isFileLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isFileLoaded;
    }

    public ObservableList<Book> getBooks(){
        return books;
    }

    public void addBook(String title, String author, String genre, int pagesCount) {
        Book newBook = new Book(title, author, genre, pagesCount);
        books.add(newBook);
    }

    public boolean isFileLoaded() {
        return isFileLoaded;
    }


}
