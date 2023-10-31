package ru.job4j.cinemaweb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {

    private IndexController indexController;

    @BeforeEach
    public void initServices() {
        indexController = new IndexController();
    }

    @Test
    public void whenGetIndexThenReturnMessageIndex() {
        String expectedMessage = "index";
        var res = indexController.getIndex();

        assertThat(res).isEqualTo(expectedMessage);
    }

}