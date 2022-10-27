package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    public Film createFilm(Film film);

    public void deleteFilm(Film film);

    public Film updateFilm(Film film) throws NotFoundException;

    public Film getFilm(Long id) throws NotFoundException;

    public List<Film> getFilmList() throws NotFoundException;

    public List<Film> getMostLikedFilms(Integer count);
}
