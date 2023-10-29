package ru.job4j.cinemaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinemaweb.model.Ticket;
import ru.job4j.cinemaweb.model.User;
import ru.job4j.cinemaweb.service.FileService;
import ru.job4j.cinemaweb.service.FilmSessionService;
import ru.job4j.cinemaweb.service.TicketService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sessions")
public class FilmsSessionController {

    private int sessionId = 0;
    private final FilmSessionService filmSessionService;
    private final FileService fileService;
    private final TicketService ticketService;

    public FilmsSessionController(FilmSessionService filmSessionService, FileService fileService, TicketService ticketService) {
        this.filmSessionService = filmSessionService;
        this.fileService = fileService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("sessions", filmSessionService.findAll());
        return "sessions/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpServletRequest request) {
        sessionId = id;
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
