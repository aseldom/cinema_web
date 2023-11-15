package ru.job4j.cinemaweb.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinemaweb.service.FileService;
import ru.job4j.cinemaweb.service.FilmService;

@Controller
@AllArgsConstructor
@RequestMapping("/films")
public class FilmsController {

    private final FilmService filmService;
    private final FileService fileService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        model.addAttribute("images", fileService.findAll());
        return "films/list";
    }
}
