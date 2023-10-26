package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.Film;

import java.util.Collection;

public interface FilmRepository {

    Film findById(int id);

    Collection<Film> findAll();

}