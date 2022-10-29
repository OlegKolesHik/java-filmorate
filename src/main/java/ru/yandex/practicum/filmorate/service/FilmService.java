package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.GenreStorage;
import ru.yandex.practicum.filmorate.storage.dao.LikeStorage;
import ru.yandex.practicum.filmorate.storage.dao.MpaStorage;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;
    final LocalDate CHECK_DATE = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(@Qualifier("dbFilm") FilmStorage filmStorage,
                       MpaStorage mpaStorage,
                       GenreStorage genreStorage,
                       LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
        this.likeStorage = likeStorage;
    }

    public Film createFilm(Film film) {
        validateFilmByDate(film);
        Film resultFilm = filmStorage.createFilm(film);

        if (film.getGenres() != null) {
            genreStorage.updateFilmGenreList(film);
        }
        return resultFilm;
    }

    public Film updateFilm(Film film) {
        if (film.getId() <= 0 || !filmStorage.getFilmList().contains(film)) {
            throw new NotFoundException(String.format("Фильм с текущим id не найден", film.getId()));
        }
        validateFilmByDate(film);

        if (film.getGenres() != null) {
            genreStorage.updateFilmGenreList(film);
        }

        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilmList() {
        return filmStorage.getFilmList();
    }

    public Film getFilm(Long id) {
        if (id < 0) {
            throw new NotFoundException(String.format("Фильм с текущим id не найден", id));
        }

        return filmStorage.getFilm(id);
    }

    private void validateFilmByDate(Film film) {
        if (film.getReleaseDate().isBefore(CHECK_DATE)) {
            throw new ValidationException("Фильм старше");
        }
    }

    public List<Film> getMostLikedFilms(Integer count) {
        if (count == null || count <= 0) {
            throw new ValidationException("Значение не должно быть меньше или равно нулю");
        }

        return filmStorage.getMostLikedFilms(count);
    }

    public List<Mpa> getMpaList() {
        return mpaStorage.getMpaList();
    }

    public Mpa getMpa(Long mpaId) throws NotFoundException {
        if (mpaId < 0) {
            throw new NotFoundException(String.format("Mpa не существует", mpaId));
        }
        Mpa resultMpa = mpaStorage.getMpaById(mpaId);

        if (resultMpa == null) {
            throw new NotFoundException(String.format("Mpa не найден в базе данных", mpaId));
        }

        return resultMpa;
    }

    public Like addLike(Long filmId, Long userId) {
        validateId(filmId, userId);

        return likeStorage.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        validateId(filmId, userId);

        likeStorage.deleteLike(filmId, userId);
    }

    public List<Genre> getGenreList() {
        return genreStorage.getGenreList();
    }

    public Genre getGenre(Long genreId) {
        if (genreId == null || genreId < 0) {
            throw new NotFoundException(String.format("Id Значение не должно быть меньше или равно нулю", genreId));
        }

        return genreStorage.getGenre(genreId);
    }

    private void validateId(Long filmId, Long userId) {
        if (userId <= 0 || userId == null) {
            throw new NotFoundException(String.format("Пользователь не найден.", userId));
        }

        if (filmId <= 0 || filmId == null) {
            throw new NotFoundException(String.format("Фильм не найден.", userId));
        }
    }
}

