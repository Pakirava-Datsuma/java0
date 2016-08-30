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

    ListChangeListener<? super ObservableBook> booksUpdater = (ListChangeListener<ObservableBook>) c -> {
        List<Book> target = currentLibrary.getValue().getBooks();
        while (c.next()) {
            if (c.wasAdded()) {
                for (ObservableBook observableBook : c.getAddedSubList()) {
                    target.add(observableBook.getBook());
                    addPieUpdaterTo(observableBook);
                }
            } else if (c.wasRemoved()) {
                for (ObservableBook observableBook : c.getRemoved()) {
                    target.remove(observableBook.getBook());
                }
            }
        }
    };
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
                currentObservableBooks.removeListener(booksUpdater); //booksUpd OFF
                currentUser.set(newLibrary.getUser());
                currentObservableBooks.setAll(ObservableBook.getObservableBooks(newLibrary.getBooks()));
                currentObservableBooks.addListener(booksUpdater);   //booksUpd ON
                infoTab.libraryInfoPane.updatePie(currentObservableBooks);


                ;
            }
        });

// set addBookBtn action
// 1*LMB = add new book
// 2*RMB = add some TEST books
        bookListTab.tableAddButton
                .setOnMouseClicked(event -> {
                    switch (event.getButton()) {
                        case PRIMARY:

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
                            break;
                        case SECONDARY:
                            if (event.getClickCount() == 2) {
                                currentObservableBooks.add(new ObservableBook("H.Potter", "J.Roulling", "fantasy", 321));
                                currentObservableBooks.add(new ObservableBook("Diving into C++", "H.Deiteil, R.Deiteil", "study", 810));
                                currentObservableBooks.add(new ObservableBook("Gun", "Aghata Christi", "detective", 524));
                                currentObservableBooks.forEach(ObservableBook::setRandomStatistics);
                            }
                            break;
                    }
                });

// set rmBookBtn action
// 1*LMB = remove book
// 2*RMB = remove ALL books
        bookListTab.tableRemoveButton
                .setOnMouseClicked(event ->
                {
                    switch (event.getButton()) {
                        case PRIMARY:
                            currentObservableBooks.remove(selectedObservableBook.getValue());
                            break;
                        case SECONDARY:
                            if (event.getClickCount() == 2)
                                currentObservableBooks.clear();
                            break;


                    }
                });

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
                        boolean openTabForeground = event.getClickCount() == 2;
                        boolean openTabBackground = event.getButton()== MouseButton.SECONDARY;
                        if (openTabForeground || openTabBackground) {
                            System.out.println("open new tab");
                            Tab newTab = bookTabManager.add(target);
                            if (openTabForeground)
                                rootTabPane.getSelectionModel().select(newTab);
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
            String newFilename = newUser.getName().concat(".lib");
            bookListTab.setText(newUser.getName().isEmpty() ? "My library.lib" : newFilename);
            currentLibrary.getValue().libraryFile = new File(newFilename);});

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
        rootTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        rootTabPane.getTabs().addAll(bookListTab, infoTab);
        rootTabPane.setMinSize(300, 500);
    }

    private void addPieUpdaterTo(ObservableBook observableBook) {
        observableBook.getReadPagesProperty().addListener((observable, oldValue, newValue) ->
                infoTab.libraryInfoPane.updatePie(currentObservableBooks));
        observableBook.getGenreProperty().addListener((observable, oldValue, newValue) ->
                infoTab.libraryInfoPane.updatePie(currentObservableBooks));
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

        primaryStage.setScene(new Scene(rootTabPane));
        primaryStage.show();

    }

}

