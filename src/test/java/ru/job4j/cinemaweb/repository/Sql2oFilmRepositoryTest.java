package ru.job4j.cinemaweb.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemaweb.configuration.DatasourceConfiguration;
import ru.job4j.cinemaweb.model.Film;

import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenFindFilmWithId1ThenGetFilmWithId1() {
        Film film1 = new Film(1, "Повелитель ветра",
                "Знаменитый российский путешественник Фёдор Конюхов, за плечами которого покорение земных полюсов, океанов и высочайших вершин, отправляется в новое, самое опасное своё путешествие. Ему предстоит обогнуть Землю на воздушном шаре в полном одиночестве.",
                2023, 6, 12, 99, 1);
        assertThat(sql2oFilmSessionRepository.findById(film1.getId())).usingRecursiveComparison().isEqualTo(film1);
    }

    @Test
    public void whenFindFilmWithAbsentIdThenGetNull() {
        Film film1 = new Film(99, "Повелитель ветра", "Description",
                2023, 6, 12, 99, 1);
        assertThat(sql2oFilmSessionRepository.findById(film1.getId())).isEqualTo(null);
    }

}