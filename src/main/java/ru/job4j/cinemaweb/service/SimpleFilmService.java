package ru.job4j.cinemaweb.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinemaweb.dto.FilmDto;
import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.Genre;
import ru.job4j.cinemaweb.repository.FilmRepository;
import ru.job4j.cinemaweb.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public FilmDto findById(int id) {
        var film = filmRepository.findById(id);
        var genre = findGenreById(film.getGenreId());
        return new FilmDto(film, genre);
    }

    private Genre findGenreById(int id) {
        return genreRepository.findById(id);
    }

    @Override
    public Collection<FilmDto> findAll() {
        var films = filmRepository.findAll();
        Collection<FilmDto> collection = new ArrayList<>();
        for (Film film : films) {
            var genre = findGenreById(film.getGenreId());
            collection.add(new FilmDto(film, genre));
        }
        return collection;
    }
}
