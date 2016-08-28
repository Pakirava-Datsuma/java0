package practice_8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by swanta on 28.08.16.
 */
public class InfoTab extends Tab {

    private Pane rootPane;
    private Pane userInfoPane;
    private Pane libraryInfoPane;
    private Pane programInfoPane;
    private List<Book> books;
    private SimpleObjectProperty<User> user;

    {
        // self settings

        userInfoPane = new UserBox();
        libraryInfoPane = new LibraryBox();

        rootPane = new GridPane() {{
            setConstraints(libraryInfoPane, 0, 0, 1, 2);
            setConstraints(userInfoPane, 1, 0);
            setConstraints(programInfoPane, 1, 1);
        }};

        setText("Statistics");
        setContent(rootPane);
        setClosable(false);
    }

    public InfoTab(ObservableList<Book> books, SimpleObjectProperty<User> user) {

        //outer links (listeners)

        books.addListener(new ListChangeListener<Book>() {
            @Override
            public void onChanged(Change<? extends Book> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        c.getAddedSubList().forEach(book ->
                                book.getReadPagesProperty().addListener((observable, oldValue, newValue) ->
                                        updatePie()));
                    }
                    updatePie();
                }
            }
        });
        user.addListener((observable, oldUser, newUser) -> {
            updateUser();
        });
        this.books = books;
        this.user = user;
        this.libraries = libraries;
    }

    private void updatePie() {
        Map<String, Integer> map = new HashMap<>();
        for (Book book : books) {
            map.merge(book.getGenre(), book.getReadPages(), (i1, i2) -> i1 + i2);
        }
        pie.setData(FXCollections.observableList(new ArrayList<PieChart.Data>(
                map.keySet().stream()
                        .map(v -> new PieChart.Data(v, map.get(v)))
                        .collect(Collectors.toList())
        )));

    }


    public void updateUser() {

    }

}
