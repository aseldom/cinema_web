package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.Genre;

import java.util.Collection;

public interface GenreRepository {

    Genre findById(int id);

    Collection<Genre> findAll();
}