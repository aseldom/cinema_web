package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.Hall;

public interface HallRepository {

    Hall findById(int id);

}