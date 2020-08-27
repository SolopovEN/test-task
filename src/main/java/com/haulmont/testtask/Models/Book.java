package com.haulmont.testtask.Models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Table(name = "Book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "[' 'а-яА-Яa-zA-ZЁё]{1,100}", message = "Название книги должно состоять из букв")
    @Column(name = "title")
    private String title;


    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name="genre_id")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "publisher")
    private Publisher publisher;

    @Max(value = 2020, message = "Год должен быть не больше текущего")
    @Min(value = 1800, message = "Год должен быть не менее 1800")
    @Column(name = "year")
    private Integer year;

    @Pattern(regexp = "[' 'а-яА-Яa-zA-ZЁё]{1,100}", message = "Название города должно состоять из букв")
    @Column(name = "city")
    private String city;

    public Book(String title, Author author, Genre genre, Publisher publisher, Integer year, String city) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.year = year;
        this.city = city;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Book() {
    }
}
