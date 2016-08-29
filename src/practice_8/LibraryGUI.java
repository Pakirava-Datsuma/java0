package practice_8;

import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Created by swanta on 17.07.16.
 */
public class LibraryGUI extends Application {
//    private SimpleObjectProperty<List<Book>> currentBookList;
    private ObservableList<ObservableBook> currentObservableBooks = FXCollections.observableArrayList();
    private SimpleObjectProperty<ObservableBook> lastOpenedBook = new SimpleObjectProperty<>();
    private SimpleObjectProperty<ObservableBook> selectedObservableBook = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Library> currentLibrary = new SimpleObjectProperty<>();
    private ObservableList<Library> libraries = FXCollections.observableArrayList();
    private SimpleObjectProperty<User> currentUser = new SimpleObjectProperty<>();
    private SimpleBooleanProperty unselectedBook = new SimpleBooleanProperty();
    @NotNull
    private SimpleObjectProperty<ObservableBook> currentBook = new SimpleObjectProperty<>();
    private InfoTab infoTab = new InfoTab();
    private BookListTab bookListTab = new BookListTab();;
    private TabPane rootTabPane = new TabPane();

    private BookTabs bookTabManager = new BookTabs(rootTabPane.getTabs());


    private boolean firstRun; //TODO: hello message at first run

    @Override
    public void stop() throws Exception {
        Library.saveLibrary(currentLibrary.getValue());
        super.stop();
    }

    {
        currentUser = infoTab.userInfoPane.user;
        infoTab.libraryInfoPane.libraryChoice.setItems(libraries);

// link List<ObservableBook> to tableView
        bookListTab.tableView.setItems(currentObservableBooks);

// link currentUser and currentBooks to currentLibrary
        currentLibrary.addListener((observable, oldLibrary, newLibrary) -> {
            if (oldLibrary != null) {
                Library.saveLibrary(oldLibrary);
            }
            if (newLibrary != null) {
                currentUser.set(newLibrary.getUser());
                //                    currentBookList.set(newLibrary.getObservableBooks());

            //TODO: this shouldn't edit library books
            //            currentObservableBooks.removeListener();
                currentObservableBooks.setAll(ObservableBook.getObservableBooks(newLibrary.getBooks()));
            //            currentObservableBooks.addListener();

                ;
            }
        });

// link userBox


        //        currentBookList.addListener((observable, oldValue, newValue) -> {
        //
        //        });

// link library books to observablebooks
        currentObservableBooks.addListener(new ListChangeListener<ObservableBook>() {
            @Override
            public void onChanged(Change<? extends ObservableBook> c) {
                List<Book> target = currentLibrary.getValue().getBooks();
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (ObservableBook observableBook : c.getAddedSubList()) {
                            target.add(observableBook.getBook());
                        }
                    } else if (c.wasRemoved()) {
                        for (ObservableBook observableBook : c.getRemoved()) {
                            target.remove(observableBook.getBook());
                        }
                    }
                }
            }
        });

// set addBookBtn action
        bookListTab.tableAddButton
                .setOnAction((ActionEvent e) -> {
                    if (!(
                            bookListTab.addAuthorField.getText().isEmpty() ||
                                    bookListTab.addPagesField.getText().isEmpty() ||
                                    bookListTab.addTitleField.getText().isEmpty() ||
                                    bookListTab.addSubjectField.getText().isEmpty())) {
                        currentObservableBooks.add(new ObservableBook(
                                bookListTab.addTitleField.getText(),
                                bookListTab.addAuthorField.getText(),
                                bookListTab.addSubjectField.getText(),
                                Integer.parseInt(bookListTab.addPagesField.getText())));
                        bookListTab.addAuthorField.clear();
                        bookListTab.addPagesField.clear();
                        bookListTab.addTitleField.clear();
                        bookListTab.addSubjectField.clear();
                    }
                });

// set rmBookBtn action
        bookListTab.tableRemoveButton
            .setOnAction((ActionEvent e) ->
                    currentObservableBooks.remove(selectedObservableBook.getValue()));

// link currentLibrary to choiceBox
        infoTab.libraryInfoPane.libraryChoice.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) ->
                        currentLibrary.setValue(newValue));

// set openBookTab action
        bookListTab.tableView
                .setOnMouseClicked(event -> {
                    ObservableBook target = (ObservableBook) bookListTab.tableView.getSelectionModel().selectedItemProperty().getValue();
                    System.out.println("tableView click");
                    if (target !=null) {
                        if (event.getClickCount() == 2 || event.getButton()== MouseButton.SECONDARY) {

                            System.out.println("open new tab");
                            bookTabManager.add(target);
                        }
                        selectedObservableBook.set(target);
                    }
                    else {
                        System.out.println("tableView unselect");
                    }
                });

// link "-" button to selectedObservableBook
        selectedObservableBook.addListener((observable, oldValue, newValue) ->
                bookListTab.tableRemoveButton.setDisable(newValue == null));

// link bookListTab name to user
        currentUser.addListener((observable, oldUser, newUser) -> {
            bookListTab.setText(newUser.getName().isEmpty() ? "My library" : newUser.getName()); });

// link bookTabManager to rootTabs (close tabs of deleted books)
        currentObservableBooks.addListener(
                new ListChangeListener<ObservableBook>() {
                    @Override
                    public void onChanged(Change<? extends ObservableBook> c) {
                        while (c.next()) {
                            List<ObservableBook> removedBooks = (List<ObservableBook>) c.getRemoved();
                            if (c.wasRemoved()) {
                                System.out.println("books removed. closing tabs...");
                                bookTabManager.removeAll(removedBooks);
                            }
                        }
                    }
                });

        rootTabPane.getTabs().addAll(bookListTab, infoTab);
        rootTabPane.setMinSize(300, 500);
    }

    @Override
    public void init() throws Exception {

//        if (firstRun) {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        libraries.addAll(Library.getSerializedLibraries());
        for ( Library library : libraries )
            if (library != null) infoTab.libraryInfoPane.libraryChoice.getSelectionModel().select(library);
        if (infoTab.libraryInfoPane.libraryChoice.getValue() == null)
            infoTab.libraryInfoPane.libraryChoice.setItems(FXCollections.observableArrayList(Library.createNonSerializableLibrary(new File(""))));
        currentObservableBooks.add(new ObservableBook("H.Potter", "J.Roulling", "fantasy", 321));
        currentObservableBooks.add(new ObservableBook("Diving into C++", "H.Deiteil, R.Deiteil", "study", 810));
        currentObservableBooks.add(new ObservableBook("Gun", "Aghata Christi", "detective", 524));
        currentObservableBooks.forEach(ObservableBook::setRandomStatistics);
        primaryStage.setScene(new Scene(rootTabPane));
        primaryStage.show();

    }

}

