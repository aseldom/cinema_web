package ru.job4j.cinemaweb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.job4j.cinemaweb.dto.FilmDto;
import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.Genre;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(source = "genre.name", target = "genre")
    @Mapping(source = "film.id", target = "id")
    @Mapping(source = "film.name", target = "name")
    FilmDto getDtoFromModel(Film film, Genre genre);

}
