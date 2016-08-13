package practice_8;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

/**
 * Created by swanta on 13.08.16.
 */
public class BooksSerializer implements Serializable {
    private List books;

    public BooksSerializer(List books) {
        this.books = books;
    }
}
