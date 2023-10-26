package ru.job4j.cinemaweb.dto;

import ru.job4j.cinemaweb.model.Film;
import ru.job4j.cinemaweb.model.Genre;

public class FilmDto {

    private int id;
    private String name;
    private String description;
    private int year;
    private int minimalAge;
    private int duration;
    private Genre genre;
    private int fileId;

    public FilmDto(Film film, Genre genre) {
        this.id = film.getId();
        this.name = film.getName();
        this.description = film.getDescription();
        this.year = film.getYear();
        this.minimalAge = film.getMinimalAge();
        this.duration = film.getDuration();
        this.genre = genre;
        this.fileId = film.getFileId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public int getDuration() {
        return duration;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getFileId() {
        return fileId;
    }
}
