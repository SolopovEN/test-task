package com.haulmont.testtask.ui.grid.models;

import com.haulmont.testtask.Models.Genre;
import com.haulmont.testtask.ui.WindowController;
import com.haulmont.testtask.ui.grid.models.intfs.DefaultGrid;
import com.haulmont.testtask.utils.GenreController;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class GenreGrid implements DefaultGrid {

    private static final GenreController genreController = new GenreController();
    Grid<Genre> genreGrid;
    private static GenreGrid instance;

    private GenreGrid() {
    }

    public static GenreGrid getInstance() {
        if (instance == null) {
            instance = new GenreGrid();
        }
        return instance;
    }


    @Override
    public VerticalLayout create(AbstractOrderedLayout mainLayout) {
        VerticalLayout genreTable = new VerticalLayout();
        Button thirdAddButton = new Button("Добавить");

        thirdAddButton.addClickListener(clickEvent -> {
            Window window = new WindowController().genreAddWindow();
            mainLayout.getUI().addWindow(window);
            window.addCloseListener(closeEvent -> genreGrid.setItems(genreController.getGenres()));
        });

        HorizontalLayout genreBottomButtonsLayout = new HorizontalLayout();
        HorizontalLayout genreTopButtonsLayout = new HorizontalLayout();
        genreBottomButtonsLayout.setWidth("100%");
        genreTopButtonsLayout.setWidth("100%");
        Button statistic = new Button("Статистика");
        Button thirdEditButton = new Button("Редактировать");

        Label label = new Label("Жанры");
        label.setStyleName(ValoTheme.LABEL_HUGE);
        genreGrid = new Grid<>();
        genreGrid.setWidth("100%");
        SingleSelect<Genre> genreSingleSelect = genreGrid.asSingleSelect();
        Button genreDeleteButton = new Button("Удалить");
        genreDeleteButton.addClickListener(clickEvent -> {
            Genre genre = genreSingleSelect.getValue();
            if (genre == null) Notification.show("Выберите жанр для удаления");
            else {
                genreController.deleteGenre(genre);
                genreGrid.setItems(genreController.getGenres());
                BookGrid.getInstance().refreshBookGrid();
            }
        });
        thirdEditButton.addClickListener(clickEvent -> {
            Genre genre = genreSingleSelect.getValue();
            if (genre == null) Notification.show("Выберите жанр для редактирования");
            else {
                Window window = new WindowController().updateWindowGenre(genre);
                mainLayout.getUI().addWindow(window);
                window.addCloseListener(closeEvent -> genreGrid.setItems(genreController.getGenres()));
            }
        });
        genreBottomButtonsLayout.addComponents(thirdEditButton, genreDeleteButton);
        genreBottomButtonsLayout.setComponentAlignment(genreDeleteButton, Alignment.MIDDLE_RIGHT);
        genreTopButtonsLayout.addComponents(thirdAddButton, statistic);
        genreTopButtonsLayout.setComponentAlignment(statistic, Alignment.MIDDLE_RIGHT);
        genreTable.addComponents(label, genreTopButtonsLayout, genreGrid, genreBottomButtonsLayout);
        genreTable.setComponentAlignment(label, Alignment.TOP_CENTER);
        genreGrid.setItems(genreController.getGenres());
        genreGrid.addColumn(Genre::getTitle).setCaption("Название жанра");
        Grid.Column<Genre, Long> statColumn = genreGrid.addColumn(genreController::getBooksCount).setCaption("Статистика");
        statColumn.setHidden(true);
        statistic.addClickListener(clickEvent -> {
            statColumn.setHidden(false);
        });

        return genreTable;
    }

    public void refreshGenreGrid() {
        genreGrid.setItems(genreController.getGenres());
    }
}
