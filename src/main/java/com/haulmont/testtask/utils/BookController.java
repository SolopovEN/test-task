package com.haulmont.testtask.utils;

import com.haulmont.testtask.Models.Author;
import com.haulmont.testtask.Models.Book;
import com.haulmont.testtask.Models.Genre;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BookController {

    public List<Book> getBooks() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = session.createQuery("from Book", Book.class).getResultList();
        session.close();

        return books;
    }

    public void save(Book book) {

        HibernateUtil.saveEntity(book);
    }

    public void deleteBook(Book book) {
        HibernateUtil.deleteEntity(book);
    }

    public void updateBook(Book book) {

        HibernateUtil.updateEntity(book);
    }

    public List<Book> filterBooks(String title, String... params) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Book> books = session.createQuery(generateQuery(title, params), Book.class).getResultList();
        session.close();

        return books;
    }

    public String generateQuery(String title, String... params) {

        StringBuilder builder = new StringBuilder();

        builder.append(String.format("from Book B where B.title like '%%%s%%'", title));
        if (!params[0].equals(""))
            builder.append(" and B.author = ").append(params[0]);
        if (!params[1].equals(""))
            builder.append(" and B.publisher = ").append(String.format("'%s'", params[1]));
        return builder.toString();
    }
}