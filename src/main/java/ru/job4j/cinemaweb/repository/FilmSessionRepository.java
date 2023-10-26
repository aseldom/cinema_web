package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.FilmSession;

import java.util.Collection;

public interface FilmSessionRepository {

    FilmSession findById(int id);

    Collection<FilmSession> findAll();

}