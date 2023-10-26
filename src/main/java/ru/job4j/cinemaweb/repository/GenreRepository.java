package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.Genre;

public interface GenreRepository {

    Genre findById(int id);

}