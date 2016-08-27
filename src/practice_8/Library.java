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
    private ObservableList<Book> books = FXCollections.observableArrayList();
//    private ArrayList<BookData> booksData = new ArrayList<>();
//    private ListChangeListener booksDataUpdater = new ListChangeListener<Book>() {
//        {
//            books.addListener(this);
//        }
//
//        @Override
//        public void onChanged(Change<? extends Book> changeList) {
//            while (changeList.next()) {
//                if (changeList.wasAdded()) {
//                    booksData.addAll(
//                            changeList.getAddedSubList().stream()
//                                    .map(Book::getData)
//                                    .collect(Collectors.toList()));
//                } else if (changeList.wasRemoved()) {
//                    booksData.removeAll(
//                            changeList.getRemoved().stream()
//                                    .map(Book::getData)
//                                    .collect(Collectors.toList())
//                    );
//                } else {
//                    throw new RuntimeException("unexpected changes in ObservableList of books in library");
//                }
//            }
//        }
//    };


    private File libraryFile;
    private boolean isFileLoaded = false;

    private Library(File file) {
        this.libraryFile = file;
        if (this.libraryFile.exists()) {
            loadBooksFromFile();
        }
    }

    public static Library createNonSerializableLibrary() {
        return new Library(null);
    }
    public static Library createSerializableLibrary(String filename) {
        File newFile = new File(filename);
        if (newFile == null) {
            return null;
        }
        else {
            Library newLibrary = new Library(newFile);
            return newLibrary;
        }
    }

    public boolean loadBooksFromFile (){
        isFileLoaded = false;
        try (FileInputStream fis = new FileInputStream(libraryFile)) {
            List<Book> books = new ArrayList<>();
            ObjectInputStream ois = new ObjectInputStream(fis);
            books = (List<Book>) ois.readObject();
            this.books = FXCollections.observableArrayList(books);
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
            ois.writeObject(books);
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
