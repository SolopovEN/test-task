package com.haulmont.testtask.Models;

public enum Publisher {
    MOSCOW,PITER,OREILLY;

    public String getCaption() {
        return name();
    }
}
