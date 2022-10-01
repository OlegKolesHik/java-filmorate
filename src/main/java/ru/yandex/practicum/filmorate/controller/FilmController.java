package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

private final FilmService filmService;

    //обновление фильма;
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.update(film);
    }

    //добавление фильма;
    @PostMapping
    public Film createFilm(@RequestBody @Valid Film film) {
        return filmService.createFilm(film);
}

    //получение всех фильмов.
    @GetMapping
    public Collection<Film> allFilms() {
        return filmService.allFilms();
    }

    //PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
    @PutMapping("/{id}/like/{userId}")
    public Film likeFilm(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.likeFilm(id, userId);
    }

    @GetMapping("/{id}")
    public Film filmsById(@PathVariable Long id) {
        return filmService.filmById(id);
    }

    //DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
    @DeleteMapping("/{id}/like/{userId}")
    public Film likeDelete(@PathVariable Long id, @PathVariable Long userId){
        return filmService.likeDelete(id,userId);
    }

    //GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков. Если значение
    // параметра count не задано, верните первые 10.

    //Параметры метода, аннотированные  @RequestParam  , являются обязательными по умолчанию.
    // @RequestParam как необязательный с обязательным атрибутом count: required = false для внедрения параметра count
    //Установить значение по умолчанию для @RequestParam  , используя атрибут defaultValue : defaultValue = "10"
    @GetMapping("/popular")
    public List<Film> returnFilms(@RequestParam(required = false) Integer count) {
        log.info("Популярные фильмы = " + count);
        return filmService.getPopularFilms(count);
    }


}
