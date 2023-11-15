package ru.job4j.cinemaweb.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.job4j.cinemaweb.dto.FilmDto;
import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

class FilmMapperTest {

    private final FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

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
        Genre genre = new Genre(1, "Boevik");
        FilmDto expectedFilmDto = new FilmDto(
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getYear(),
                film.getMinimalAge(),
                film.getDuration(),
                genre.getName(),
                film.getFileId());
        FilmDto actualFilmDto = filmMapper.getDtoFromModel(film, genre);
        assertThat(actualFilmDto).usingRecursiveComparison().isEqualTo(expectedFilmDto);
    }

}