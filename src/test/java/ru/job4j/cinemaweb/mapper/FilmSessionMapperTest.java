package ru.job4j.cinemaweb.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.job4j.cinemaweb.dto.FilmSessionDto;
import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.FilmSession;
import ru.job4j.cinemaweb.model.Genre;
import ru.job4j.cinemaweb.model.Hall;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.*;

class FilmSessionMapperTest {

    private final FilmSessionMapper filmSessionMapper = Mappers.getMapper(FilmSessionMapper.class);

    @Test
    public void addAllParametersThenGetAllParameters() {
        Film film = new Film(
                1,
                "film",
                "best film",
                2000,
                1,
                18,
                120,
                1);
        FilmSession filmSession = new FilmSession(
                1,
                1,
                1,
                Timestamp.valueOf("2023-10-18 11:00:00"),
                Timestamp.valueOf("2023-10-18 12:00:00"),
                100);
        Genre genre = new Genre(1, "Boevik");
        Hall hall = new Hall(1, "Big", "Hall description", 10, 10);
        FilmSessionDto expectedFilmSessionDto = new FilmSessionDto(
                filmSession.getId(),
                film.getFileId(),
                film.getYear(),
                film.getDuration(),
                film.getMinimalAge(),
                filmSession.getPrice(),
                hall.getRows(),
                hall.getColumns(),
                genre.getName(),
                film.getName(),
                film.getDescription(),
                hall.getName(),
                filmSession.getStartTime());
        FilmSessionDto actualFilmSessionDto = filmSessionMapper.getDtoFromModel(film, filmSession, genre, hall);
        assertThat(actualFilmSessionDto).usingRecursiveComparison().isEqualTo(expectedFilmSessionDto);
    }

}