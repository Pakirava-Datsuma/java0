package practice_8;

import javafx.application.Application;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by swanta on 13.08.16.
 */
public class UIExampleApp {

    public static void main(String[] args) {
/*
        try (ObjectOutputStream oos = new ObjectOutputStream(System.out);)
        {
            oos.writeObject(
                    new BookList(new HashSet() {
                        {
                            new Book("title", "author", "genre", 321) {{
                                addTodayReadPagesQuantity(12);
                            }};
                        }
                    }));
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        Application.launch(LibraryGUI.class, args);
    }
}
