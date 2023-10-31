package ru.job4j.cinemaweb.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemaweb.configuration.DatasourceConfiguration;
import ru.job4j.cinemaweb.model.FilmSession;

import java.sql.Timestamp;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @Test
    public void whenCheckFirstAndLastSessions() {
        FilmSession filmSession1 = new FilmSession(1, 1, 1,
                Timestamp.valueOf("2023-10-18 11:00:00"),
                Timestamp.valueOf("2023-10-18 13:00:00"),
                150);
        FilmSession filmSession2 = new FilmSession(12, 4, 3,
                Timestamp.valueOf("2023-10-18 17:00:00"),
                Timestamp.valueOf("2023-10-18 19:00:00"),
                350);
        assertThat(filmSession1).usingRecursiveComparison().isEqualTo(sql2oFilmSessionRepository.findById(filmSession1.getId()));
        assertThat(filmSession2).usingRecursiveComparison().isEqualTo(sql2oFilmSessionRepository.findById(filmSession2.getId()));
    }

    @Test
    public void whenAbsentIdThenNull() {
        FilmSession filmSession1 = new FilmSession(99, 1, 1,
                Timestamp.valueOf("2023-10-18 11:00:00"),
                Timestamp.valueOf("2023-10-18 13:00:00"),
                150);
        assertThat(sql2oFilmSessionRepository.findById(filmSession1.getId())).isEqualTo(null);
    }

}