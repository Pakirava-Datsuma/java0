package practice_8;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swanta on 27.08.16.
 */
public class BookTabs {
    ObservableList<Tab> tabs;


    public BookTabs(ObservableList<Book> books, ObservableList<Tab> rootTabs) {
        tabs = rootTabs;
        books.addListener(
                new ListChangeListener<Book>() {
                    @Override
                    public void onChanged(Change<? extends Book> c) {
                        while (c.next()) {
                            List<Book> bookList = (List<Book>) c.getRemoved();
                            if (c.wasRemoved()) {
                                removeAll(bookList);
                            }
                        }
                    }
                });
    }

    private void removeAll(List<Book> books) {
        books.forEach(this::remove);
    }

    private void remove(Book book) {
        tabs.removeIf(tab -> ((BookTab)tab).has(book));
    }

    public Tab add(Book book) {
        Tab tab = find(book);
        if (tab == null) {
            System.out.println("new tab for: " + book.toString());
            tab = new BookTab(book);
            tabs.add(tab);
        }else {
            System.out.println("tab for book already created: " + book.toString());
        }
        return tab;
    }

    private Tab find(Book book) {
        for (Tab tab : tabs) {
            BookTab bookTab;
            try {
                bookTab = ((BookTab) tab);
            } catch (Exception e) {
                continue;
            }
            if (bookTab.getBook() == book){
                return tab;
            }
        }
        return null;
    }

}
