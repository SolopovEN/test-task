package com.haulmont.testtask.Models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Table(name = "Genre")
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "[' 'а-яА-Яa-zA-ZЁё]{1,100}", message = "Название жанра должно состоять из букв")
    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Book> books;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre(String title) {
        this.title = title;
    }

    public Genre() {
    }
}
