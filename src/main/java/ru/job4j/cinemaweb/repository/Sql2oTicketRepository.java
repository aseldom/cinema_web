package ru.job4j.cinemaweb.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinemaweb.model.Ticket;

import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sql2oUserRepository.class);

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO tickets (session_id, row_number, place_number, user_id)
                    VALUES (:sessionId, :row, :place, :userId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("row", ticket.getRow())
                    .addParameter("place", ticket.getPlace())
                    .addParameter("userId", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Exception e) {
            LOGGER.error("Error occurred", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets WHERE id = :id")
                    .addParameter("id", id);
            return Optional.of(query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetchFirst(Ticket.class));
        }

    }
}
