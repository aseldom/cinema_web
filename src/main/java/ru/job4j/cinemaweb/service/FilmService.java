package ru.job4j.cinemaweb.service;

import ru.job4j.cinemaweb.dto.FilmDto;

import java.util.Collection;

public interface FilmService {

    FilmDto findById(int id);

    Collection<FilmDto> findAll();

}