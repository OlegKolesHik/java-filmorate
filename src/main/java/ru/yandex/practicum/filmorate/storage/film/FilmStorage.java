package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    //Создайте интерфейсы FilmStorage и UserStorage, в которых будут определены
    // методы добавления, удаления и модификации объектов.

    //добавление фильма;
    Film createFilm(Film film);

    Film update(Film film);


    Collection<Film> allFilms();

    Film likeFilm(Long id, Long userId);

    Film likeDelete(Long id, Long userId);

    Film filmById(Long id);

    List<Film> returnFilmsPopular(int count);
}
