package ru.job4j.cinemaweb.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FilmSessionDto {

    private int id;
    private int fileId;
    private int year;
    private int duration;
    private int minimalAge;
    private int price;
    private int rows;
    private int columns;
    private String genre;
    private String filmName;
    private String description;
    private String hall;
    private Timestamp startTime;

}
