package practice_8;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
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
public class UserInfoTab extends Tab {
    private ChoiceBox<Library> libraryChoice = new ChoiceBox<Library>(){};
    PieChart pie = new PieChart();
    Pane userBox = new UserBox();
    Pane progressPane = new HBox(pie, userBox);
    List<Book> books;

    {
        setText("Statistics");
        setContent(progressPane);
        setClosable(false);
    }

    public UserInfoTab(ObservableList<Book> books) {
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
        this.books = books;
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


}
