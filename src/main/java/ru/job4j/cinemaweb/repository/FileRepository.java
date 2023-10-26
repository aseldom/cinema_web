package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.File;

import java.util.Collection;
import java.util.Optional;

public interface FileRepository {

    Optional<File> findById(int id);

    Collection<File> findAll();

}