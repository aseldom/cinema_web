package ru.job4j.cinemaweb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import ru.job4j.cinemaweb.dto.FileDto;
import ru.job4j.cinemaweb.service.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    private FileService fileService;

    private FileController fileController;

    @BeforeEach
    public void initServices() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    public void whenGetByIdThenResponseEntityOk() {
        FileDto fileDto = new FileDto("file1", new byte[]{1, 2, 3});
        when(fileService.getFileById(anyInt())).thenReturn(Optional.of(fileDto));
        var result = fileController.getById(1);

        assertThat(result).isEqualTo(ResponseEntity.ok(fileDto.getContent()));
    }

    @Test
    public void whenGetByIdThenResponseEntityNotFound() {
        when(fileService.getFileById(anyInt())).thenReturn(Optional.empty());
        var result = fileController.getById(1);
        assertThat(result).isEqualTo(ResponseEntity.notFound().build());
    }

}