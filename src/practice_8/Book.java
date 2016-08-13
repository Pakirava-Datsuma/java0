package practice_8;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by swanta on 17.07.16.
 */
public class Book {
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty subject;
    private final IntegerProperty pages;

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getSubject() {
        return subject.get();
    }

    public SimpleStringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getPages() {
        return pages.get();
    }

    public IntegerProperty pagesProperty() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages.set(pages);
    }

    public Book(SimpleStringProperty author, SimpleStringProperty subject, SimpleStringProperty title, IntegerProperty pages) {
        this.author = author;
        this.subject = subject;
        this.title = title;
        this.pages = pages;
    }
}
