package com.haulmont.testtask.ui.grid.models;

import com.haulmont.testtask.Models.Author;
import com.haulmont.testtask.ui.WindowController;
import com.haulmont.testtask.ui.grid.models.intfs.DefaultGrid;
import com.haulmont.testtask.utils.AuthorController;
import com.vaadin.ui.*;

public class AuthorGrid implements DefaultGrid {

    AuthorController authorController;
    Grid<Author> authorGrid;

    @Override
    public VerticalLayout create(AbstractOrderedLayout mainLayout) {

        authorController = new AuthorController();

        VerticalLayout authorTable = new VerticalLayout();
        Button authorAddButton = new Button("Добавить");

        authorAddButton.addClickListener(clickEvent -> {
            Window window = new WindowController().authorAddWindow();
            mainLayout.getUI().addWindow(window);
            window.addCloseListener(closeEvent -> {
                authorGrid.setItems(authorController.getAuthors());
                BookGrid.getInstance().refreshFilter(authorController.getAuthors());
            });
        });

        HorizontalLayout firstTwoButton = new HorizontalLayout();
        firstTwoButton.setWidth("100%");
        Button firstEditButton = new Button("Редактировать");

        authorGrid = new Grid<>();
        authorGrid.setWidth("100%");
        SingleSelect<Author> authorSingleSelect = authorGrid.asSingleSelect();
        Button authorDeleteButton = new Button("Удалить");
        authorDeleteButton.addClickListener(clickEvent -> {
            Author author = authorSingleSelect.getValue();
            if (author == null) Notification.show("Выберите автора для удаления");
            else {
                authorController.deleteAuthor(author);
                authorGrid.setItems(authorController.getAuthors());
                BookGrid.getInstance().refreshBookGrid();
                BookGrid.getInstance().refreshFilter(authorController.getAuthors());
            }
        });
        firstEditButton.addClickListener(clickEvent -> {
            Author author = authorSingleSelect.getValue();
            if (author == null) Notification.show("Выберете автора для редактирования");
            else {
                Window window = new WindowController().updateWindowAuthor(author);
                mainLayout.getUI().addWindow(window);
                window.addCloseListener(closeEvent -> {
                    authorGrid.setItems(authorController.getAuthors());
                    BookGrid.getInstance().refreshFilter(authorController.getAuthors());
                });
            }
        });
        firstTwoButton.addComponents(firstEditButton, authorDeleteButton);
        firstTwoButton.setComponentAlignment(authorDeleteButton, Alignment.MIDDLE_RIGHT);
        authorTable.addComponents(authorAddButton, authorGrid, firstTwoButton);

        authorGrid.setItems(authorController.getAuthors());
        authorGrid.addColumn(Author::getName).setCaption("Имя");
        authorGrid.addColumn(Author::getSurname).setCaption("Фамилия");
        authorGrid.addColumn(Author::getMiddleName).setCaption("Отчество");

        return authorTable;
    }

}
