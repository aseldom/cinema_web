package ru.job4j.cinemaweb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinemaweb.dto.FilmDto;
import ru.job4j.cinemaweb.dto.FilmSessionDto;
import ru.job4j.cinemaweb.mapper.FilmMapper;
import ru.job4j.cinemaweb.model.*;
import ru.job4j.cinemaweb.service.FilmSessionService;
import ru.job4j.cinemaweb.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private TicketService ticketService;
    private FilmSessionService filmSessionService;
    private FilmSession filmSession;
    private final FilmMapper filmMapper = Mappers.getMapper(FilmMapper.class);

    private TicketController ticketController;
    private Film film;
    private Genre genre;
    private Hall hall;
    private FilmDto filmDto;
    private FilmSessionDto filmSessionDto;
    private final Model model = new ConcurrentModel();
    private Ticket ticket;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(filmSessionService, ticketService);
        film = new Film(1, "Peace", "Peace of the world", 2023, 1, 12, 120, 1);
        genre = new Genre(1, "Horror");
        hall = new Hall(1, "Large", "Large hall", 20, 20);
        filmSession = new FilmSession(1, 1, 1,
                Timestamp.valueOf("2023-10-18 11:00:00"),
                Timestamp.valueOf("2023-10-18 13:00:00"),
                150);
        filmDto = filmMapper.getDtoFromModel(film, genre);
        filmSessionDto = new FilmSessionDto();
//        filmSessionDto = new FilmSessionDto(filmDto, filmSession, hall);
        ticket = new Ticket(99, 2, 3, 4, 5);

    }

    @Test
    public void whenRequestGetByIdThenReturnMessageAndModelAttributes() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("ticketId", 99);
        String expectedMessage = "tickets/ticket";

        when(request.getSession()).thenReturn(httpSession);
        when(ticketService.findById(anyInt())).thenReturn(Optional.of(ticket));
        when(filmSessionService.findById(anyInt())).thenReturn(filmSessionDto);

        String actualMessage = ticketController.getById(model, request);

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat((FilmSessionDto) model.getAttribute("ses")).isEqualTo(filmSessionDto);
        assertThat((Ticket) model.getAttribute("ticket")).isEqualTo(ticket);
    }

    @Test
    public void whenRequestGetByIdAndTicketIsNullThenReturnErrorMessage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("ticketId", 99);
        String expectedMessage = "errors/404";

        when(request.getSession()).thenReturn(httpSession);
        when(ticketService.findById(anyInt())).thenReturn(Optional.empty());

        String actualMessage = ticketController.getById(model, request);

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat(model.getAttribute("message")).isEqualTo("Такого билета нет");
    }

    @Test
    public void whenRequestGetByIdAndFilmSessionIsAbsentThenReturnMessage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("ticketId", 99);
        String expectedMessage = "errors/404";

        when(request.getSession()).thenReturn(httpSession);
        when(ticketService.findById(anyInt())).thenReturn(Optional.of(ticket));
        when(filmSessionService.findById(anyInt())).thenReturn(null);

        String actualMessage = ticketController.getById(model, request);

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat(model.getAttribute("message")).isEqualTo("Сеанс с указанным идентификатором не найден");
    }

}