package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.Hall;

import java.util.Collection;

public interface HallRepository {

    Hall findById(int id);

    Collection<Hall> findAll();
}