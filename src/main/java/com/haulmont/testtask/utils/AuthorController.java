package com.haulmont.testtask.utils;

import com.haulmont.testtask.Models.Author;
import org.hibernate.Session;
import com.haulmont.testtask.utils.HibernateUtil;
import org.hibernate.Transaction;

import java.util.List;

public class AuthorController {

    public List<Author> getAuthors() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Author> authors = session.createQuery("from Author", Author.class).getResultList();
        session.close();

        return authors;
    }

    public void save(Author author) {
        HibernateUtil.saveEntity(author);
    }

    public void deleteAuthor(Author author) {
        HibernateUtil.deleteEntity(author);
    }

    public void updateAuthor(Author author) {
        HibernateUtil.updateEntity(author);
    }
}
