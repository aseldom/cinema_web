package ru.job4j.cinemaweb.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.cinemaweb.model.Film;

import org.sql2o.Sql2o;
import java.util.Collection;

@Repository
public class Sql2oFilmRepository implements FilmRepository {

    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Film findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films WHERE id = :id", true)
                    .addParameter("id", id);
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
        }
    }

    @Override
    public Collection<Film> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }
}
