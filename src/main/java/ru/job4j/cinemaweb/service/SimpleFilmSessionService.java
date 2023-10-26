package ru.job4j.cinemaweb.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinemaweb.dto.FilmDto;
import ru.job4j.cinemaweb.dto.FilmSessionDto;
import ru.job4j.cinemaweb.model.FilmSession;
import ru.job4j.cinemaweb.model.Hall;
import ru.job4j.cinemaweb.repository.FilmSessionRepository;
import ru.job4j.cinemaweb.repository.HallRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;
    private final HallRepository hallRepository;
    private final SimpleFilmService simpleFilmService;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository, HallRepository hallRepository, SimpleFilmService simpleFilmService) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.hallRepository = hallRepository;
        this.simpleFilmService = simpleFilmService;
    }

    @Override
    public FilmSessionDto findById(int id) {
        Hall hall = hallRepository.findById(filmSessionRepository.findById(id).getHallId());
        FilmDto filmDto = simpleFilmService.findById(filmSessionRepository.findById(id).getFilmId());
        FilmSession filmSession = filmSessionRepository.findById(id);
        return new FilmSessionDto(filmDto, filmSession, hall);
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
