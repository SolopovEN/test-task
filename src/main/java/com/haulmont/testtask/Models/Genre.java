package com.haulmont.testtask.Models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    private long id;

    @Pattern(regexp = "[' 'а-яА-Яa-zA-ZЁё]{1,100}", message = "Название жанра должно состоять из букв")
    @Column(name = "Title")
    private String title;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<Book> book;

    public int getBookCount() {
        return this.book.size();
    }

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

    public Collection<Book> getBook() {
        return book;
    }

    public void setBook(Collection<Book> book) {
        this.book = book;
    }
}
