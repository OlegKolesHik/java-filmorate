package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Service//бизнес-логика находится на уровне сервиса, поэтому мы используем аннотацию @Service, чтобы указать, что
// класс принадлежит этому уровню
public class FilmService {

    //Service - Бизнес-логика
    public InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void validateNewFilms(long id) {
        if (!filmStorage.containsNewFilms(id)) {
            throw new NotFoundException(String.format("Фильм не найден", id));
        }
    }

    public Film createFilm(Film film) {
        inMemoryFilmStorage.validate(film);
        return filmStorage.createFilm(film);
    }

    public Film update(Film film) {
        validateNewFilms(film.getId());
        inMemoryFilmStorage.validate(film);
        if (film.getId() < 0) {
            throw new NotFoundException("Id не может быть отрицательным");
        }
        return filmStorage.update(film);
    }

    public Collection<Film> allFilms() {
        return filmStorage.allFilms();
    }

    public Film likeFilm(Long idUser, Long idFilm) {
        if (idUser < 0 || idFilm < 0) {
            throw new NotFoundException("Id не может быть отрицательным");
        }
        return filmStorage.likeFilm(idUser,idFilm);
    }

    public Film likeDelete(Long idFilm, Long idUser) {
        validateNewFilms(idFilm);
        validateNewFilms(idUser);
        User user = userStorage.userById(idUser);
        if (idFilm < 0 || idUser < 0 || user == null) {
            throw new NotFoundException("Id не может быть отрицательным или пустым");
        }
        return filmStorage.likeDelete(idFilm, idUser);
    }

    public List<Film> getPopularFilms(Integer count) {
        if (count == null || count > 10 || count == 0) {
            count = 10;
        }
        return filmStorage.returnFilmsPopular(count);
    }

    public Film filmById(Long idFilm) {
        validateNewFilms(idFilm);
        if(idFilm < 0)
            throw new NotFoundException("Id не может быть отрицательным");
        return filmStorage.filmById(idFilm);
    }
}
