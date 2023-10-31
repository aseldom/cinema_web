package ru.job4j.cinemaweb.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemaweb.configuration.DatasourceConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oHallRepositoryTest {

    private static Sql2oHallRepository sql2oHallRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @Test
    public void whenGetSameNameOfHalls() {
        var hallsNameExpect = List.of("Big", "Middle", "Small");
        var halls = sql2oHallRepository.findAll();
        List<String> listHallsName = new ArrayList<>();
        for (var hall : halls) {
            listHallsName.add(hall.getName());
        }
        assertThat(hallsNameExpect).usingRecursiveComparison().isEqualTo(listHallsName);
    }
}