package com.haulmont.testtask.Models;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    private long id;

    @Pattern(regexp = "[' 'а-яА-Яa-zA-ZЁё]{1,100}", message = "Имя автора должно состоять из букв")
    @Column(name = "Name")
    private String name;

    @Pattern(regexp = "[' 'а-яА-Яa-zA-ZЁё]{1,100}", message = "Фамилия автора должна состоять из букв")
    @Column(name = "Surname")
    private String surname;

    @Pattern(regexp = "[' 'а-яА-Яa-zA-ZЁё]{1,100}", message = "Отчество автора должно состоять из букв")
    @Column(name = "MiddleName")
    private String middleName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Book> book;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Collection<Book> getBook() {
        return book;
    }

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Author(String name, String surname, String middleName) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
    }

    public Author() {
    }
}
