package ru.job4j.cinemaweb.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemaweb.configuration.DatasourceConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oGenreRepositoryTest {

    private static Sql2oGenreRepository sql2oGenreRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oGenreRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
    }

    @Test
    public void whenGetSameNameOfGenres() {
        var genresNameExpect = List.of("Боевик", "Драма", "Мелодрама", "Приключения", "Семейный", "Триллер", "Ужасы");
        var genres = sql2oGenreRepository.findAll();
        List<String> listGenresName = new ArrayList<>();
        for (var genre : genres) {
            listGenresName.add(genre.getName());
        }
        assertThat(genresNameExpect).usingRecursiveComparison().isEqualTo(listGenresName);
    }

}