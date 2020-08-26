package com.haulmont.testtask.ui;

import com.haulmont.testtask.Models.Author;
import com.haulmont.testtask.Models.Book;
import com.haulmont.testtask.Models.Genre;
import com.haulmont.testtask.Models.Publisher;
import com.haulmont.testtask.utils.AuthorController;
import com.haulmont.testtask.utils.BookController;
import com.haulmont.testtask.utils.GenreController;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;

public class WindowController {

    BookController bookController = new BookController();
    AuthorController authorController = new AuthorController();
    GenreController genreController = new GenreController();


    public VerticalLayout createDefaultLayout(AbstractOrderedLayout layout1, Button windowApply) {

        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout windowNSM = new HorizontalLayout();
        windowNSM.setWidth("100%");
        layout.setHeight("90%");
        windowNSM.setHeight("10%");

        Button windowCancel = new Button("Отменить");

        windowCancel.addClickListener(clickEvent -> layout.findAncestor(Window.class).close());

        windowNSM.addComponents(windowApply, windowCancel);
        windowNSM.setComponentAlignment(windowApply, Alignment.BOTTOM_LEFT);
        windowNSM.setComponentAlignment(windowCancel, Alignment.BOTTOM_RIGHT);
        layout.addComponents(layout1, windowNSM);
        layout.setComponentAlignment(windowNSM, Alignment.BOTTOM_CENTER);
        layout.setMargin(true);

        return layout;
    }

    public Window createDefaultWindow(String caption) {

        Window window = new Window(caption);
        window.setModal(true);
        window.setWidth("400");
        window.setHeightUndefined();
        window.center();

        return window;
    }

    public Window authorAddWindow() {

        AuthorController authorController = new AuthorController();

        VerticalLayout nsmLayout = new VerticalLayout();

        Window window = createDefaultWindow("Добавить автора");

        TextField windowName = new TextField("Имя");
        TextField windowSurname = new TextField("Фамилия");
        TextField windowMiddleName = new TextField("Отчество");

        nsmLayout.addComponents(windowName, windowSurname, windowMiddleName);
        Button windowApply = new Button("Применить");
        windowApply.addClickListener(clickEvent -> {

            tryToSave(() -> {
                authorController.save(new Author(windowName.getValue(), windowSurname.getValue(), windowMiddleName.getValue()));
                window.close();
            });

        });
        VerticalLayout layout = createDefaultLayout(nsmLayout, windowApply);
        layout.setHeightUndefined();
        window.setContent(layout);
        return window;
    }

    public Window bookAddWindow() {

        VerticalLayout layout = new VerticalLayout();

        Window window = createDefaultWindow("Добавить книгу");

        TextField windowTitle = new TextField("Название");
        ComboBox<Author> windowAuthor = new ComboBox<>("Автор");
        windowAuthor.setItems(authorController.getAuthors());
        windowAuthor.setItemCaptionGenerator(Author::getSurname);
        windowAuthor.setEmptySelectionAllowed(false);
        ComboBox<Genre> windowGenre = new ComboBox<>("Жанр");
        windowGenre.setItems(genreController.getGenres());
        windowGenre.setItemCaptionGenerator(Genre::getTitle);
        windowGenre.setEmptySelectionAllowed(false);
        ComboBox<Publisher> windowPublisher = new ComboBox<>("Издатель");
        windowPublisher.setItems(Publisher.MOSCOW, Publisher.PITER, Publisher.OREILLY);
        windowPublisher.setItemCaptionGenerator(Publisher::getCaption);
        TextField windowYear = new TextField("Год");
        TextField windowCity = new TextField("Город");
        layout.addComponents(windowTitle, windowAuthor, windowGenre, windowPublisher, windowYear, windowCity);
        Button windowApply = new Button("Применить");
        windowApply.addClickListener(clickEvent -> {

            try {

                tryToSave(() -> {
                    bookController.save(new Book(windowTitle.getValue(), windowAuthor.getValue(), windowGenre.getValue(), windowPublisher.getValue(), Integer.parseInt(windowYear.getValue()), windowCity.getValue()));
                    window.close();
                });

            } catch (NumberFormatException e) {
                Notification.show("Укажите год в цифрах");
            }

        });
        VerticalLayout layout1 = createDefaultLayout(layout, windowApply);
        layout1.setHeightUndefined();
        window.setContent(layout1);

        return window;
    }

    public Window genreAddWindow() {

        VerticalLayout layout = new VerticalLayout();

        Window window = createDefaultWindow("Добавить жанр");

        TextField windowTitle = new TextField("Название");

        layout.addComponent(windowTitle);
        Button windowApply = new Button("Применить");
        windowApply.addClickListener(clickEvent -> {

            tryToSave(() -> {
                genreController.save(new Genre(windowTitle.getValue()));
                window.close();
            });

        });
        VerticalLayout layout1 = createDefaultLayout(layout, windowApply);
        layout1.setHeightUndefined();
        window.setContent(layout1);

        return window;
    }

    public Window updateWindowAuthor(Author author) {

        VerticalLayout verticalLayout = new VerticalLayout();

        Window window = createDefaultWindow("Редактировать автора");

        TextField windowName = new TextField("Имя");
        windowName.setValue(author.getName());
        TextField windowSurname = new TextField("Фамилия");
        windowSurname.setValue(author.getSurname());
        TextField windowMiddleName = new TextField("Отчество");
        windowMiddleName.setValue(author.getMiddleName());

        verticalLayout.addComponents(windowName, windowSurname, windowMiddleName);
        Button windowApply = new Button("Применить");
        windowApply.addClickListener(clickEvent -> {
            author.setName(windowName.getValue());
            author.setSurname(windowSurname.getValue());
            author.setMiddleName(windowMiddleName.getValue());

            tryToSave(() -> {
                authorController.updateAuthor(author);
                window.close();
            });

        });
        VerticalLayout layout1 = createDefaultLayout(verticalLayout, windowApply);
        layout1.setHeightUndefined();
        window.setContent(layout1);
        return window;
    }

    public Window updateWindowGenre(Genre genre) {

        VerticalLayout verticalLayout = new VerticalLayout();

        Window window = createDefaultWindow("Редактирование жанра");

        TextField windowTitle = new TextField("Название");
        windowTitle.setValue(genre.getTitle());

        verticalLayout.addComponent(windowTitle);
        Button windowApply = new Button("Применить");
        windowApply.addClickListener(clickEvent -> {
            genre.setTitle(windowTitle.getValue());

            tryToSave(() -> {
                genreController.updateGenre(genre);
                window.close();
            });

        });
        VerticalLayout layout = createDefaultLayout(verticalLayout, windowApply);
        layout.setHeightUndefined();
        window.setContent(layout);
        return window;
    }

    public Window updateWindowBook(Book book) {

        VerticalLayout verticalLayout = new VerticalLayout();

        Window window = createDefaultWindow("Редактирование книги");

        TextField windowTitle = new TextField("Название");
        windowTitle.setValue(book.getTitle());
        ComboBox<Author> windowAuthor = new ComboBox<>("Автор");
        windowAuthor.setItems(authorController.getAuthors());
        windowAuthor.setItemCaptionGenerator(Author::getSurname);
        windowAuthor.setEmptySelectionAllowed(false);
        windowAuthor.setValue(book.getAuthor());
        ComboBox<Genre> windowGenre = new ComboBox<>("Жанр");
        windowGenre.setItems(genreController.getGenres());
        windowGenre.setItemCaptionGenerator(Genre::getTitle);
        windowGenre.setEmptySelectionAllowed(false);
        windowGenre.setValue(book.getGenre());
        ComboBox<Publisher> windowPublisher = new ComboBox<>("Издатель");
        windowPublisher.setItems(Publisher.MOSCOW, Publisher.PITER, Publisher.OREILLY);
        windowPublisher.setItemCaptionGenerator(Publisher::getCaption);
        windowPublisher.setEmptySelectionAllowed(false);
        windowPublisher.setValue(book.getPublisher());
        TextField windowYear = new TextField("Год");
        windowYear.setValue(book.getYear().toString());
        TextField windowCity = new TextField("Город");
        windowCity.setValue(book.getCity());
        verticalLayout.addComponents(windowTitle, windowAuthor, windowGenre, windowPublisher, windowYear, windowCity);
        Button windowApply = new Button("Применить");
        windowApply.addClickListener(clickEvent -> {

            if (windowAuthor.getValue() == null || windowGenre.getValue() == null)
                Notification.show("Заполните поля автора и жанра");
            else {
                try {

                    book.setTitle(windowTitle.getValue());
                    book.setAuthor(windowAuthor.getValue());
                    book.setGenre(windowGenre.getValue());
                    book.setPublisher(windowPublisher.getValue());
                    book.setYear(Integer.parseInt(windowYear.getValue()));
                    book.setCity(windowCity.getValue());

                    tryToSave(() -> {
                        bookController.updateBook(book);
                        window.close();
                    });
                } catch (NumberFormatException e) {
                    Notification.show("Укажите год в цифрах");
                }


            }
        });
        VerticalLayout layout = createDefaultLayout(verticalLayout, windowApply);
        layout.setHeightUndefined();
        window.setContent(layout);
        return window;
    }

    public void tryToSave(Runnable consumer) {
        try {
            consumer.run();
        } catch (ConstraintViolationException e) {
            StringBuilder stringBuilder = new StringBuilder();
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                stringBuilder.append(violation.getMessage()).append("\n");
            }
            Notification.show(stringBuilder.toString());
        }
    }
}
