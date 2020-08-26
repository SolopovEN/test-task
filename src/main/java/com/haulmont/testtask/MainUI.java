package com.haulmont.testtask;

import com.haulmont.testtask.ui.grid.models.BookGrid;
import com.haulmont.testtask.ui.grid.controller.GridFactory;
import com.haulmont.testtask.ui.grid.GridTypes;
import com.haulmont.testtask.ui.WindowController;
import com.haulmont.testtask.ui.grid.models.intfs.FilterableGrid;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    WindowController windowController;

    @Override
    protected void init(VaadinRequest request) {

        GridFactory gridFactory = new GridFactory();
        windowController = new WindowController();

        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        VerticalLayout firstTable = gridFactory.getGrid(GridTypes.AUTHOR).create(mainLayout);
        VerticalLayout secondTable = gridFactory.getGrid(GridTypes.BOOK).create(mainLayout);
        VerticalLayout thirdTable = gridFactory.getGrid(GridTypes.GENRE).create(mainLayout);

        HorizontalLayout filterLayout = ((FilterableGrid) gridFactory.getGrid(GridTypes.BOOK)).createFilter();

        layout.addComponents(firstTable, secondTable, thirdTable);
        mainLayout.addComponents(filterLayout, layout);
        mainLayout.setComponentAlignment(filterLayout, Alignment.TOP_CENTER);
        setContent(mainLayout);
    }


}