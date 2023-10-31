package ru.job4j.cinemaweb.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemaweb.configuration.DatasourceConfiguration;
import ru.job4j.cinemaweb.model.File;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oFileRepositoryTest {

    private static Sql2oFileRepository sql2oFileRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFileRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @Test
    public void whenFindFileWithId1ThenGetFileWithId1() {
        File file1 = new File(1, "001.jpg", "files\\001.jpg");
        assertThat(sql2oFileRepository.findById(file1.getId()).get()).usingRecursiveComparison().isEqualTo(file1);
    }

    @Test
    public void whenFindAllFiles() {
        File file1 = new File(1, "001.jpg", "files\\001.jpg");
        File file2 = new File(2, "002.jpg", "files\\002.jpg");
        File file3 = new File(3, "003.jpg", "files\\003.jpg");
        File file4 = new File(4, "004.jpg", "files\\004.jpg");
        List<File> expectedFiles = List.of(file1, file2, file3, file4);
        Collection<File> actualFiles = sql2oFileRepository.findAll();
        for (var actualFile : actualFiles) {
            assertThat(actualFile).usingRecursiveComparison().isEqualTo(expectedFiles.get(actualFile.getId() - 1));
        }
    }

}