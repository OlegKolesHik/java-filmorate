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

    private Map<Integer, Film> films = new HashMap();
    LocalDate data = LocalDate.of(1895, Month.DECEMBER, 28);

    //название не может быть пустым;
    //максимальная длина описания — 200 символов;
    //дата релиза — не раньше 28 декабря 1895 года;
    //продолжительность фильма должна быть положительной.

    public void validate(Film film) throws ValidationException {
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
        }


    //добавление фильма;
    @PostMapping
    public Film add(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        if (films.containsKey(film.getId())) {
            throw new ValidationException("Фильм уже есть в списке");
        } else {
            films.put(film.getId(), film);
            log.info("Фильм {} добавлен", film.getName());
            return film;
        }
    }

    //обновление фильма;
    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        films.put(film.getId(), film);
        log.info("Фильм {} обновлен", film.getName());
        return film;
    }

    //получение всех фильмов.
    @GetMapping
    public Collection<Film> allFilms() {
        return films.values();
    }
}
