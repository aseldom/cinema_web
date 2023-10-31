package ru.job4j.cinemaweb.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinemaweb.configuration.DatasourceConfiguration;
import ru.job4j.cinemaweb.model.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    public void clearVacancies() {
        var tickets = sql2oTicketRepository.findAll();
        for (var ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var ticket = sql2oTicketRepository.save(new Ticket(0, 1, 2, 3, 4));
        var savedTicked = sql2oTicketRepository.findById(ticket.get().getId());
        assertThat(savedTicked.get()).usingRecursiveComparison().isEqualTo(ticket.get());
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(0, 1, 2, 3, 4));
        var ticket2 = sql2oTicketRepository.save(new Ticket(4, 5, 6, 7, 8));
        var ticket3 = sql2oTicketRepository.save(new Ticket(9, 10, 11, 12, 13));
        List<Optional<Ticket>> tickets = List.of(ticket1, ticket2, ticket3);
        for (var ticket : tickets) {
            var savedUser = sql2oTicketRepository.findById(ticket.get().getId());
            assertThat(savedUser.get()).usingRecursiveComparison().isEqualTo(ticket.get());
        }
    }

    @Test
    public void whenSaveTwiceSamePlaceThenGetEmpty() {
        var ticket1 = sql2oTicketRepository.save(new Ticket(0, 2, 2, 3, 4));
        var ticket2 = sql2oTicketRepository.save(new Ticket(0, 2, 2, 3, 4));
        assertThat(ticket2).isEmpty();
    }
}