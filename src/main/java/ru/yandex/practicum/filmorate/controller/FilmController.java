package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap();
    protected LocalDate data = LocalDate.of(1895, Month.DECEMBER, 28);
    private int generateId = 0;

    protected void validate(Film film) throws ValidationException {
        if(film.getName() == null || film.getName().isBlank()) {
            log.error("Ошибка");
            throw new ValidationException("Hазвание не может быть пустым");
        }
        if(film.getDescription().length() > 200) {
            log.error("Ошибка");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if(film.getReleaseDate().isBefore(data)) {
            log.error("Ошибка");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if(film.getDuration() < 0) {
            log.error("Ошибка");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        if(film.getId() < 0) {
            log.debug("Идентификатор меньше 0");
            throw new ValidationException("Идентификатор должен быть положительным");
        }
    }


    //добавление фильма;
    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        validate(film);
        if (films.containsKey(film.getId())) {
            throw new ValidationException("Фильм уже есть в списке");
        } else {
            film.setId(++generateId);
            films.put(film.getId(), film);
            log.info("Фильм {} добавлен", film.getName());
            return film;
        }
    }

    //обновление фильма;
    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        validate(film);
        if (!films.containsKey(film.getId())) {
            create(film);
        } else {
            films.put(film.getId(), film);
            log.info("Фильм {} обновлен", film.getName());

        }
        return film;
    }

    //получение всех фильмов.
    @GetMapping
    public Collection<Film> allFilms() {
        log.info("Список фильмов {}", films.size());
        return films.values();
    }
}
