package ru.job4j.cinemaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinemaweb.model.Ticket;
import ru.job4j.cinemaweb.service.FilmSessionService;
import ru.job4j.cinemaweb.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;

    public TicketController(FilmSessionService filmSessionService, TicketService ticketService) {
        this.filmSessionService = filmSessionService;
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket")
    public String getById(Model model, HttpServletRequest request) {
        int ticketId = (int) request.getSession().getAttribute("ticketId");
        Optional<Ticket> ticketOptional = ticketService.findById(ticketId);
        if (ticketOptional.isEmpty()) {
            model.addAttribute("message", "Такого билета нет");
            return "errors/404";
        }
        var filmSessionDto = filmSessionService.findById(ticketOptional.get().getSessionId());
        if (filmSessionDto == null) {
            model.addAttribute("message", "Сеанс с указанным идентификатором не найден");
            return "errors/404";
        }
        model.addAttribute("ses", filmSessionDto);
        model.addAttribute("ticket", ticketOptional.get());

        return "tickets/ticket";
    }

}
