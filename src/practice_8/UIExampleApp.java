package practice_8;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by swanta on 13.08.16.
 */
public class UIExampleApp {
    private final static String TEST_LIBRARY_FILENAME = "testlibrary.lib";
    private static Library testLibrary = Library.createSerializableLibrary(TEST_LIBRARY_FILENAME);

    public static void main(String[] args) {
        Application.launch(LibraryGUI.class, args);
    }
}
