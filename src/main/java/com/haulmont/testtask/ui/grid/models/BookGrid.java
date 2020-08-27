package com.haulmont.testtask.ui.grid.models;

import com.haulmont.testtask.Models.Author;
import com.haulmont.testtask.Models.Book;
import com.haulmont.testtask.Models.Publisher;
import com.haulmont.testtask.ui.WindowController;
import com.haulmont.testtask.ui.grid.models.intfs.DefaultGrid;
import com.haulmont.testtask.ui.grid.models.intfs.FilterableGrid;
import com.haulmont.testtask.utils.AuthorController;
import com.haulmont.testtask.utils.BookController;
import com.vaadin.ui.*;

import java.util.Collection;

public class BookGrid implements DefaultGrid, FilterableGrid {

    private final BookController bookController = new BookController();
    private Grid<Book> bookGrid;
    private ComboBox<Author> authorField;
    private static BookGrid instance;

    private BookGrid() {}

    public static BookGrid getInstance() {
        if (instance == null) {
            instance = new BookGrid();
        }
        return instance;
    }

    @Override
    public VerticalLayout create(AbstractOrderedLayout mainLayout) {

        VerticalLayout bookTable = new VerticalLayout();

        Button bookAddButton = new Button("Добавить");

        bookAddButton.addClickListener(clickEvent -> {
            Window window = new WindowController().bookAddWindow();
            mainLayout.getUI().addWindow(window);
            window.addCloseListener(closeEvent -> {
                bookGrid.setItems(bookController.getBooks());
                GenreGrid.getInstance().refreshGenreGrid();
            });
        });

        HorizontalLayout secondTwoButton = new HorizontalLayout();
        secondTwoButton.setWidth("100%");
        Button secondEditButton = new Button("Редактировать");

        bookGrid = new Grid<>();
        bookGrid.setWidth("100%");
        SingleSelect<Book> bookSingleSelect = bookGrid.asSingleSelect();
        Button bookDeleteButton = new Button("Удалить");
        bookDeleteButton.addClickListener(clickEvent -> {
            Book book = bookSingleSelect.getValue();
            if (book == null) Notification.show("Выберите книгу для удаления");
            else {
                bookController.deleteBook(book);
                bookGrid.setItems(bookController.getBooks());
                GenreGrid.getInstance().refreshGenreGrid();
            }
        });
        secondEditButton.addClickListener(clickEvent -> {
            Book book = bookSingleSelect.getValue();
            if (book == null) Notification.show("Выберите книгу для редактирования");
            else {
                Window window = new WindowController().updateWindowBook(book);
                mainLayout.getUI().addWindow(window);
                window.addCloseListener(closeEvent -> {
                    bookGrid.setItems(bookController.getBooks());
                    GenreGrid.getInstance().refreshGenreGrid();
                });
            }
        });
        secondTwoButton.addComponents(secondEditButton, bookDeleteButton);
        secondTwoButton.setComponentAlignment(bookDeleteButton, Alignment.MIDDLE_RIGHT);
        bookTable.addComponents(bookAddButton, bookGrid, secondTwoButton);

        bookGrid.setItems(bookController.getBooks());
        Grid.Column<Book, Object> authorColumn = bookGrid.addColumn(book -> book.getAuthor().getSurname());
        authorColumn.setCaption("Автор");
        bookGrid.addColumn(Book::getTitle).setCaption("Название");
        Grid.Column<Book, Object> genreColumn = bookGrid.addColumn(book -> book.getGenre().getTitle());
        genreColumn.setCaption("Жанр");
        bookGrid.addColumn(Book::getPublisher).setCaption("Издатель");
        bookGrid.addColumn(Book::getYear).setCaption("Год");
        bookGrid.addColumn(Book::getCity).setCaption("Город");

        return bookTable;
    }

    public HorizontalLayout createFilter() {

        HorizontalLayout filterLayout = new HorizontalLayout();

        Button filterButton = new Button("Фильтр");

        TextField titleField = new TextField("Название", "");
        authorField = new ComboBox<>("Автор");
        refreshFilter(new AuthorController().getAuthors());
        authorField.setItemCaptionGenerator(Author::getSurname);
        ComboBox<Publisher> publisherField = new ComboBox<>("Издатель");
        publisherField.setItems(Publisher.MOSCOW, Publisher.PITER, Publisher.OREILLY);
        publisherField.setItemCaptionGenerator(Publisher::getCaption);
        filterLayout.addComponents(titleField, authorField, publisherField, filterButton);
        filterLayout.setComponentAlignment(filterButton, Alignment.BOTTOM_LEFT);

        filterButton.addClickListener(clickEvent -> {
            StringBuilder author = new StringBuilder();
            StringBuilder publisher = new StringBuilder();
            if (publisherField.getValue() != null)
                publisher.append(publisherField.getValue().getCaption());
            if (authorField.getValue() != null)
                author.append(authorField.getValue().getId());
            bookGrid.setItems(bookController.filterBooks(titleField.getValue(), author.toString(), publisher.toString()));
        });

        return filterLayout;
    }

    public void refreshBookGrid() {
        bookGrid.setItems(bookController.getBooks());
    }

    public void refreshFilter(Collection<Author> items) {
        authorField.setItems(items);
    }
}
