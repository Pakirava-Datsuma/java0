package practice_8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by swanta on 28.08.16.
 */
public class LibraryBox extends VBox {
    ChoiceBox<Library> libraryChoice;
    PieChart pie;

    {
        pie = new PieChart();
        libraryChoice = new ChoiceBox<>();
        getChildren().addAll(libraryChoice, pie);
        setSpacing(20.0);

    }

    private void updatePie(List<ObservableBook> books) {
        Map<String, Integer> map = new HashMap<>();
        for (ObservableBook observableBook : books) {
            map.merge(observableBook.getGenre(), observableBook.getReadPages(), (i1, i2) -> i1 + i2);
        }
        pie.setData(FXCollections.observableList(new ArrayList<PieChart.Data>(
                map.keySet().stream()
                        .map(v -> new PieChart.Data(v, map.get(v)))
                        .collect(Collectors.toList())
        )));

    }
}
