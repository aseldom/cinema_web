package ru.job4j.cinemaweb.service;

import ru.job4j.cinemaweb.dto.FileDto;

import java.util.Collection;
import java.util.Optional;

public interface FileService {

    Optional<FileDto> getFileById(int id);

    Collection<FileDto> findAll();

}