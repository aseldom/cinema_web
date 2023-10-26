package ru.job4j.cinemaweb.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinemaweb.model.Hall;
import ru.job4j.cinemaweb.repository.HallRepository;

@Service
public class SimpleHallService implements HallService {

    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Hall findById(int id) {
        return hallRepository.findById(id);
    }
}
