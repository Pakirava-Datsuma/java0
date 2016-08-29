package practice_8;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by swanta on 28.08.16.
 */
public class LibraryBox extends VBox {
    Label libraryText = new Label("choose lib:"){{
        setAlignment(Pos.BASELINE_RIGHT);
    }};
    ChoiceBox<Library> libraryChoice= new ChoiceBox<Library>() {{
        setAlignment(Pos.BASELINE_LEFT);
    }};
//    Button addLibrary = new Button("+"){{
//        setOnAction();
//    }}
    PieChart pie = new PieChart(){{
        setAlignment(Pos.CENTER);
    }};
    Pane choice = new HBox();

    {
        libraryChoice.setConverter(new StringConverter<Library>() {
            @Override
            public String toString(Library object) {
                return object.libraryFile.getName();
            }

            @Override
            public Library fromString(String string) {
                for (Library library : libraryChoice.getItems())
                    if (library.libraryFile.getName().equals(string))
                        return library;
                return null;
            }
        });
        choice.getChildren().addAll(libraryText, libraryChoice);
        getChildren().addAll(choice, pie);
        setSpacing(20.0);

    }

    public void updatePie(List<ObservableBook> books) {
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
