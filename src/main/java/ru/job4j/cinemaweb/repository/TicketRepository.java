package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.Ticket;

import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> findById(int id);

}
