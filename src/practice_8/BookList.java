package practice_8;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by swanta on 13.08.16.
 */
public class BookList extends ArrayList<BookData> implements Serializable {
    public BookList(Collection books) {
        super(books);
    }
}
