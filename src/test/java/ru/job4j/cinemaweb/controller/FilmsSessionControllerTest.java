package ru.job4j.cinemaweb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ru.job4j.cinemaweb.dto.FilmDto;
import ru.job4j.cinemaweb.dto.FilmSessionDto;
import ru.job4j.cinemaweb.model.*;
import ru.job4j.cinemaweb.service.FilmSessionService;
import ru.job4j.cinemaweb.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmsSessionControllerTest {

    private TicketService ticketService;
    private FilmSessionService filmSessionService;
    private FilmSession filmSession;

    private FilmsSessionController filmsSessionController;
    private Film film;
    private Genre genre;
    private Hall hall;
    private FilmDto filmDto;
    private FilmSessionDto filmSessionDto;
    private final Model model = new ConcurrentModel();

    @BeforeEach
    public void initServices() {

        filmSessionService = mock(FilmSessionService.class);
        ticketService = mock(TicketService.class);
        filmsSessionController = new FilmsSessionController(filmSessionService, ticketService);
        film = new Film(1, "Peace", "Peace of the world", 2023, 1, 12, 120, 1);
        genre = new Genre(1, "Horror");
        hall = new Hall(1, "Large", "Large hall", 20, 20);
        filmSession = new FilmSession(1, 1, 1,
                Timestamp.valueOf("2023-10-18 11:00:00"),
                Timestamp.valueOf("2023-10-18 13:00:00"),
                150);
        filmDto = new FilmDto(film, genre);
        filmSessionDto = new FilmSessionDto(filmDto, filmSession, hall);

    }

    @Test
    public void whenRequestGetAllThenReturnModelAndMessage() {
        String expectedMessage = "sessions/list";

        when(filmSessionService.findAll()).thenReturn(List.of(filmSessionDto));
        String actualMessage = filmsSessionController.getAll(model);

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat(((List<FilmSessionDto>) (model.getAttribute("sessions"))).get(0)).isEqualTo(filmSessionDto);
    }

    @Test
    public void whenRequestGetByIdThenReturnModelAndMessage() {
        HttpServletRequest request = new MockHttpServletRequest();

        String expectedMessage = "sessions/buy";

        when(filmSessionService.findById(anyInt())).thenReturn(filmSessionDto);
        String actualMessage = filmsSessionController.getById(model, 1, request);

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat((FilmSessionDto) model.getAttribute("ses")).isEqualTo(filmSessionDto);
    }

    @Test
    public void whenRequestGetByIdThenReturnErrorMessage() {
        HttpServletRequest request = new MockHttpServletRequest();
        String expectedMessage = "errors/404";

        when(filmSessionService.findById(anyInt())).thenReturn(null);
        String actualMessage = filmsSessionController.getById(model, 1, request);

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat((String) model.getAttribute("message")).isEqualTo("Сеанс с указанным идентификатором не найден");
    }

    @Test
    public void whenRequestBuyThenReturnTicketIdIdAndMessage() {
        User user = new User(1, "email@domen.org", "name", "password");
        Ticket ticket = new Ticket(99, 2, 3, 4, 5);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", user);
        httpSession.setAttribute("sessionId", 1);
        String expectedMessage = "redirect:/tickets/ticket";

        when(request.getSession()).thenReturn(httpSession);
        when(ticketService.save(any())).thenReturn(Optional.of(ticket));

        String actualMessage = filmsSessionController.buy(1, 1, model, request);

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat((Integer) httpSession.getAttribute("ticketId")).isEqualTo(ticket.getId());
    }

    @Test
    public void whenRequestBuyThenReturnErrorMessage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String expectedMessage = "errors/404";

        when(request.getSession()).thenThrow(new RuntimeException());

        String actualMessage = filmsSessionController.buy(1, 1, model, request);

        assertThat(actualMessage).isEqualTo(expectedMessage);
        assertThat((String) model.getAttribute("message")).isEqualTo("Билет на данное место уже куплен, пожалуйста выберете другое место");
    }

}