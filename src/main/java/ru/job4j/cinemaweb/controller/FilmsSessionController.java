package ru.job4j.cinemaweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinemaweb.model.Ticket;
import ru.job4j.cinemaweb.model.User;
import ru.job4j.cinemaweb.service.FilmSessionService;
import ru.job4j.cinemaweb.service.TicketService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/sessions")
public class FilmsSessionController {

    private final FilmSessionService filmSessionService;
    private final TicketService ticketService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("sessions", filmSessionService.findAll());
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpServletRequest request) {
        request.getSession().setAttribute("sessionId", id);
        var filmSessionDto = filmSessionService.findById(id);
        if (filmSessionDto == null) {
            model.addAttribute("message", "Сеанс с указанным идентификатором не найден");
            return "errors/404";
        }
        model.addAttribute("ses", filmSessionDto);

        return "sessions/buy";
    }

    @PostMapping("/buy")
    public String buy(@RequestParam("selectedRow") int selectedRow, @RequestParam("selectedColumn") int selectedColumn, Model model, HttpServletRequest request) {
        try {
            var session = request.getSession();
            int sessionId = (int) session.getAttribute("sessionId");
            User user = (User) session.getAttribute("user");
            Ticket ticket = new Ticket(0, sessionId, selectedRow, selectedColumn, user.getId());
            ticket = ticketService.save(ticket).get();
            session.setAttribute("ticketId", ticket.getId());
            return "redirect:/tickets/ticket";
        } catch (Exception exception) {
            model.addAttribute("message", "Билет на данное место уже куплен, пожалуйста выберете другое место");
            return "errors/404";
        }
    }
}
