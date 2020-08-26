package com.haulmont.testtask.ui.grid.models;

import com.haulmont.testtask.Models.Genre;
import com.haulmont.testtask.ui.WindowController;
import com.haulmont.testtask.ui.grid.models.intfs.DefaultGrid;
import com.haulmont.testtask.utils.GenreController;
import com.vaadin.ui.*;

public class GenreGrid implements DefaultGrid {

    GenreController genreController;
    Grid<Genre> genreGrid;

    @Override
    public VerticalLayout create(AbstractOrderedLayout mainLayout) {

        genreController = new GenreController();
        VerticalLayout genreTable = new VerticalLayout();
        Button thirdAddButton = new Button("Добавить");

        thirdAddButton.addClickListener(clickEvent -> {
            Window window = new WindowController().genreAddWindow();
            mainLayout.getUI().addWindow(window);
            window.addCloseListener(closeEvent -> genreGrid.setItems(genreController.getGenres()));
        });

        HorizontalLayout thirdTwoButton = new HorizontalLayout();
        thirdTwoButton.setWidth("100%");
        HorizontalLayout thirdTwoButtonUp = new HorizontalLayout();
        Button statistic = new Button("Статистика");
        thirdTwoButtonUp.setWidth("100%");
        Button thirdEditButton = new Button("Редактировать");

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
        thirdTwoButton.addComponents(thirdEditButton, genreDeleteButton);
        thirdTwoButton.setComponentAlignment(genreDeleteButton, Alignment.MIDDLE_RIGHT);
        thirdTwoButtonUp.addComponents(thirdAddButton, statistic);
        thirdTwoButtonUp.setComponentAlignment(statistic, Alignment.MIDDLE_RIGHT);
        genreTable.addComponents(thirdTwoButtonUp, genreGrid, thirdTwoButton);
        genreGrid.setItems(genreController.getGenres());
        genreGrid.addColumn(Genre::getTitle).setCaption("Название жанра");
        Grid.Column<Genre, Integer> statColumn = genreGrid.addColumn(Genre::getBookCount).setCaption("Статистика");
        statColumn.setHidden(true);
        statistic.addClickListener(clickEvent -> {
            statColumn.setHidden(false);
        });

        return genreTable;
    }

}
