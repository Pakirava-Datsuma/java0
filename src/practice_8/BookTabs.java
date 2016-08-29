package practice_8;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

import java.util.List;

/**
 * Created by swanta on 27.08.16.
 */
public class BookTabs {
    ObservableList<Tab> tabs;

    public BookTabs(ObservableList<Tab> tabs) {
        this.tabs = tabs;
    }

    public void removeAll(List<ObservableBook> observableBooks) {
        observableBooks.forEach(this::remove);
    }

    private void remove(ObservableBook observableBook) {
//        try {
            tabs.removeIf(tab -> tab.getClass().equals(BookTab.class));
//        } catch (ClassCastException e) {
//            System.out.println("normal tab, skip");
//        }
    }

    public Tab add(ObservableBook observableBook) {
        Tab tab = find(observableBook);
        if (tab == null) {
            System.out.println("new tab for: " + observableBook.toString());
            tab = new BookTab(observableBook);
            tabs.add(tab);
        }else {
            System.out.println("tab for observableBook already created: " + observableBook.toString());
        }
        return tab;
    }

    private Tab find(ObservableBook observableBook) {
        for (Tab tab : tabs) {
            BookTab bookTab;
            try {
                bookTab = ((BookTab) tab);
            } catch (Exception e) {
                continue;
            }
            if (bookTab.getBook() == observableBook){
                return tab;
            }
        }
        return null;
    }

}
