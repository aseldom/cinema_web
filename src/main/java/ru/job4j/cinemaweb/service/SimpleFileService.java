package ru.job4j.cinemaweb.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinemaweb.dto.FileDto;
import ru.job4j.cinemaweb.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository sql2oFileRepository;

    public SimpleFileService(FileRepository sql2oFileRepository) {
        this.sql2oFileRepository = sql2oFileRepository;
    }

    @Override
    public Optional<FileDto> getFileById(int id) {
        var file = sql2oFileRepository.findById(id);
        if (file.isEmpty()) {
            return Optional.empty();
        }
        var path = file.get().getPath();
        return Optional.of(new FileDto(file.get().getName(), readFileAsBites(path)));
    }

    @Override
    public Collection<FileDto> findAll() {
        var files = sql2oFileRepository.findAll();
        List<FileDto> filesDto = new ArrayList<>();
        for (var file : files) {
            var content = readFileAsBites(file.getPath());
            filesDto.add(new FileDto(file.getName(), content));
        }
        return filesDto;
    }

    private byte[] readFileAsBites(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
