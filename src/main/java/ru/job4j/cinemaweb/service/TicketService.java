package ru.job4j.cinemaweb.service;

import ru.job4j.cinemaweb.model.Ticket;

import java.util.Optional;

public interface TicketService {

    Ticket save(Ticket ticket);

    Optional<Ticket> findById(int id);

}
