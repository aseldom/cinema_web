package ru.job4j.cinemaweb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinemaweb.dto.FileDto;
import ru.job4j.cinemaweb.dto.FilmDto;
import ru.job4j.cinemaweb.mapper.FilmMapper;
import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.Genre;
import ru.job4j.cinemaweb.service.FileService;
import ru.job4j.cinemaweb.service.FilmService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmsControllerTest {

    private FileService fileService;

    private FilmService filmService;

    private FilmsController filmsController;

    private final FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        filmService = mock(FilmService.class);
        filmsController = new FilmsController(filmService, fileService);
    }

    @Test
    public void whenRequestThenGetIt() {
        Film film1 = new Film(1, "Повелитель ветра",
                "Знаменитый российский путешественник Фёдор Конюхов, за плечами которого покорение земных полюсов,\n"
                        + "     океанов и высочайших вершин, отправляется в новое, самое опасное своё путешествие.\n"
                        + "     Ему предстоит обогнуть Землю на воздушном шаре в полном одиночестве.",
                2023, 6, 12, 99, 1);
        Genre genre = new Genre(1, "Мелодрама");
        FilmDto filmDto1 = filmMapper.getDtoFromModel(film1, genre);
        FileDto fileDto1 = new FileDto("file1", new byte[]{1, 2, 3});
        Model model = new ConcurrentModel();
        String expectedMessage = "films/list";
        when(filmService.findAll()).thenReturn(List.of(filmDto1));
        when(fileService.findAll()).thenReturn(List.of(fileDto1));

        String actualMessage = filmsController.getAll(model);
        var actualFilm = (List<FilmDto>) model.getAttribute("films");
        var actualFile = (List<FileDto>) model.getAttribute("images");

        assertThat(actualFilm.get(0)).isEqualTo(filmDto1);
        assertThat(actualFile.get(0)).isEqualTo(fileDto1);
        assertThat(actualMessage).isEqualTo(expectedMessage);

    }

}