package com.haulmont.testtask.utils;

import com.haulmont.testtask.Models.Author;
import com.haulmont.testtask.Models.Genre;
import org.hibernate.Session;
import com.haulmont.testtask.utils.HibernateUtil;
import org.hibernate.Transaction;

import java.util.List;

public class GenreController {

    public List<Genre> getGenres() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Genre> genres = (List<Genre>) session.createQuery("from Genre").list();
        session.close();

        return genres;
    }

    public void save(Genre genre) {

        HibernateUtil.saveEntity(genre);
    }

    public void deleteGenre(Genre genre) {

        HibernateUtil.deleteEntity(genre);
    }

    public void updateGenre(Genre genre) {

        HibernateUtil.updateEntity(genre);
    }
}
