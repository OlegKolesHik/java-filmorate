package ru.yandex.practicum.filmorate.storage.logic;//package ru.yandex.practicum.filmorate.storage.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private long id;
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film createFilm(Film film) {
        validate(film);

        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteFilm(Film film) {

    }

    @Override
    public Film updateFilm(Film film) throws NotFoundException {
        validate(film);

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new NotFoundException("В списке нет такого фильма");
        }

        return film;
    }

    @Override
    public Film getFilm(Long id) throws NotFoundException {
        if (id < 0) {
            throw new NotFoundException(String.format("Id должен быть больше нуля", id));
        }
        return films.get(id);
    }

    @Override
    public List<Film> getFilmList() {
        return new ArrayList<>(films.values());
    }

    private long generateId() {
        return id += 1;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Фильм старше 28.12.1895.");
        }
    }

    public List<Film> getMostLikedFilms(Integer count) {
        return films.values().stream()
                .sorted()
                .limit(count)
                .collect(Collectors.toList());
    }
}
