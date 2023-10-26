package ru.job4j.cinemaweb.service;

import ru.job4j.cinemaweb.dto.FilmSessionDto;

import java.util.Collection;

public interface FilmSessionService {

    FilmSessionDto findById(int id);

    Collection<FilmSessionDto> findAll();

}