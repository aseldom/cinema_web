package ru.job4j.cinemaweb.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinemaweb.dto.FilmDto;
import ru.job4j.cinemaweb.mapper.FilmMapper;
import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.Genre;
import ru.job4j.cinemaweb.repository.FilmRepository;
import ru.job4j.cinemaweb.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreRepository genreRepository;

    private final FilmMapper filmMapper;

    @Override
    public FilmDto findById(int id) {
        var film = filmRepository.findById(id);
        var genre = findGenreById(film.getGenreId());
        return filmMapper.getDtoFromModel(film, genre);
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
            collection.add(filmMapper.getDtoFromModel(film, genre));
        }
        return collection;
    }
}
