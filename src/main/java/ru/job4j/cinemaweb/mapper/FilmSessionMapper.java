package ru.job4j.cinemaweb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.cinemaweb.dto.FilmSessionDto;
import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.FilmSession;
import ru.job4j.cinemaweb.model.Genre;
import ru.job4j.cinemaweb.model.Hall;

@Mapper(componentModel = "spring")
public interface FilmSessionMapper {

    @Mapping(source = "filmSession.id", target = "id")
    @Mapping(source = "genre.name", target = "genre")
    @Mapping(source = "film.name", target = "filmName")
    @Mapping(source = "film.description", target = "description")
    @Mapping(source = "hall.name", target = "hall")
    FilmSessionDto getDtoFromModel(Film film, FilmSession filmSession, Genre genre, Hall hall);

}
