package practice_8;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by swanta on 13.08.16.
 */
public class Library {
    private final ObservableList<Book> books = FXCollections.observableArrayList();
    private final List<BookData> booksData = new ArrayList<>();
    private final ListChangeListener booksDataUpdater = new ListChangeListener<Book>() {
        {
            books.addListener(this);
        }

        @Override
        public void onChanged(Change<? extends Book> changeList) {
            while (changeList.next()) {
                if (changeList.wasAdded()) {
                    booksData.addAll(
                            changeList.getAddedSubList().stream()
                                    .map(Book::getData)
                                    .collect(Collectors.toList()));
                } else if (changeList.wasRemoved()) {
                    booksData.removeAll(
                            changeList.getRemoved().stream()
                                    .map(Book::getData)
                                    .collect(Collectors.toList())
                    );
                } else {
                    throw new RuntimeException("unexpected changes in ObservableList of books in library");
                }
            }
        }
    };


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
            List<BookData> bookData;// = new ArrayList<>();
            ObjectInputStream ois = new ObjectInputStream(fis);
            bookData = (List<BookData>) ois.readObject();
            this.books.setAll(Book.getBooks(bookData));
            isFileLoaded = true;
        }  catch (ClassNotFoundException e) {
            //"no books were saved earlier"
            e.printStackTrace();
        } catch (IOException e) {
            //"can't access file"
            e.printStackTrace();
        }
        return isFileLoaded;
    }

    public boolean saveBooksToFile () {
        isFileLoaded = false;
        try (FileOutputStream fis = new FileOutputStream(libraryFile, false)) { //file will be overwritten
            ObjectOutputStream ois = new ObjectOutputStream(fis);
            ois.writeObject(this.booksData);
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
