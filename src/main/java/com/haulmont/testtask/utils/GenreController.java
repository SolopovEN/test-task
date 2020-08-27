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
        List<Genre> genres = session.createQuery("from Genre", Genre.class).getResultList();
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

    public long getBooksCount(Genre genre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        long count = (long) session.createQuery("select count(*) from Book book where book.genre = :id")
                .setLong("id", genre.getId())
                .uniqueResult();
        session.close();
        return count;
    }
}
