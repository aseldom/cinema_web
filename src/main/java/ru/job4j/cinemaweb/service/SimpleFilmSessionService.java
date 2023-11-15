package ru.job4j.cinemaweb.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinemaweb.dto.FilmSessionDto;
import ru.job4j.cinemaweb.mapper.FilmSessionMapper;
import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.FilmSession;
import ru.job4j.cinemaweb.model.Genre;
import ru.job4j.cinemaweb.model.Hall;
import ru.job4j.cinemaweb.repository.FilmRepository;
import ru.job4j.cinemaweb.repository.FilmSessionRepository;
import ru.job4j.cinemaweb.repository.GenreRepository;
import ru.job4j.cinemaweb.repository.HallRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionMapper filmSessionMapper;
    private final FilmRepository filmRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final GenreRepository genreRepository;
    private final HallRepository hallRepository;

    @Override
    public FilmSessionDto findById(int id) {
        FilmSession filmSession = filmSessionRepository.findById(id);
        Film film = filmRepository.findById(filmSession.getFilmId());
        Genre genre = genreRepository.findById(film.getGenreId());
        Hall hall = hallRepository.findById(filmSessionRepository.findById(id).getHallId());
        return filmSessionMapper.getDtoFromModel(film, filmSession, genre, hall);
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
         Collection<FilmSession> filmSessions = filmSessionRepository.findAll();
         Collection<FilmSessionDto> filmSessionsDto = new ArrayList<>();
         for (FilmSession filmSession : filmSessions) {
             filmSessionsDto.add(findById(filmSession.getId()));
         }
         return filmSessionsDto;
    }
}
