package ru.job4j.cinemaweb.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FilmDto {

    private int id;
    private String name;
    private String description;
    private int year;
    private int minimalAge;
    private int duration;
    private String genre;
    private int fileId;

}
