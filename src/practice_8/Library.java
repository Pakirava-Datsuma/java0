package practice_8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by swanta on 13.08.16.
 */
public class Library implements Serializable{
    private static final String FILENAME_REGEX = ".+\\.lib";
    private LibraryUser user;
    transient private ObservableList<Book> books = FXCollections.observableArrayList();

    transient private File libraryFile;
//    transient private boolean isFileLoaded = false;
    private Book[] serializableBookList;

    public static List<Library> getSerializedLibraries () {
        File[] files = new File(".").listFiles((dir, name) -> name.matches(FILENAME_REGEX));

        List<String> names = Arrays.asList(files).stream()
                .map(File::getName)
                .collect(Collectors.toList());

        List<Library> libraries =
            names.stream()
                    .map(Library::createSerializableLibrary)
                    .collect(Collectors.toList());
        return libraries;
    }

    private Library (){};
//    private Library (File file){};

    public static Library createNonSerializableLibrary() {
        return new Library();
    }
    public static Library createSerializableLibrary(String filename) {
        File newFile = new File(filename);
        if (!newFile.exists()) {
            return null;
        }
        else {
            return loadLibrary(newFile);
        }
    }

    public static Library loadLibrary(File file){
        Library library = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            library = (Library) ois.readObject();
            library.setSerializableBooks();
        }  catch (ClassNotFoundException e) {
            //"no books were saved earlier"
            e.printStackTrace();
            library = null;
        } catch (IOException e) {
            //"can't access file"
            e.printStackTrace();
        }
        return library;
    }

    private void setSerializableBooks() {
        books = FXCollections.observableArrayList(serializableBookList);
    }

    public static boolean saveLibrary(Library library) {
        File libraryFile = library.libraryFile;
        try (FileOutputStream fis = new FileOutputStream(libraryFile, false)) { //file will be overwritten
            ObjectOutputStream ois = new ObjectOutputStream(fis);
            library.getSerializableBooks();
            ois.writeObject(library);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void getSerializableBooks() {
        serializableBookList = books.toArray(serializableBookList);
    }

    public ObservableList<Book> getBooks(){
        return books;
    }

    public void addBook(String title, String author, String genre, int pagesCount) {
        Book newBook = new Book(title, author, genre, pagesCount);
        books.add(newBook);
    }

//    public boolean isFileLoaded() {
//        return isFileLoaded;
//    }


}
