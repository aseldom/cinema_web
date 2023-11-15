package ru.job4j.cinemaweb.controller;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;

    @GetMapping("/ticket")
    public String getById(Model model, HttpServletRequest request) {
        var session = request.getSession();
        int ticketId = (int) session.getAttribute("ticketId");
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
