package com.haulmont.testtask.ui.grid.controller;

import com.haulmont.testtask.ui.grid.GridTypes;
import com.haulmont.testtask.ui.grid.models.AuthorGrid;
import com.haulmont.testtask.ui.grid.models.BookGrid;
import com.haulmont.testtask.ui.grid.models.intfs.DefaultGrid;
import com.haulmont.testtask.ui.grid.models.GenreGrid;

public class GridFactory {

    public DefaultGrid getGrid(GridTypes type) {

        DefaultGrid toReturn = null;
        switch (type) {
            case BOOK:
                toReturn = BookGrid.getInstance();
                break;
            case GENRE:
                toReturn = new GenreGrid();
                break;
            case AUTHOR:
                toReturn = new AuthorGrid();
                break;
            default:throw new IllegalArgumentException("Wrong grid type: " + type);
        }

        return toReturn;
    }
}
