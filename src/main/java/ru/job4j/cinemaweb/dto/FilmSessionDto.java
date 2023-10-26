package ru.job4j.cinemaweb.dto;

import ru.job4j.cinemaweb.model.FilmSession;
import ru.job4j.cinemaweb.model.Hall;

import java.sql.Timestamp;

public class FilmSessionDto {

    private int id;
    private FilmDto filmDto;
    private Hall hall;
    private Timestamp startTime;
    private Timestamp endTime;
    private int price;

    public FilmSessionDto(FilmDto filmDto, FilmSession filmSession, Hall hall) {
        this.id = filmSession.getId();
        this.filmDto = filmDto;
        this.hall = hall;
        this.startTime = filmSession.getStartTime();
        this.endTime = filmSession.getEndTime();
        this.price = filmSession.getPrice();
    }

    public int getId() {
        return id;
    }

    public FilmDto getFilmDto() {
        return filmDto;
    }

    public Hall getHall() {
        return hall;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public int getPrice() {
        return price;
    }
}
