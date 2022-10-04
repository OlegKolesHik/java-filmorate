package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component //Добавьте к InMemoryFilmStorage и InMemoryUserStorage аннотацию @Component, чтобы впоследствии пользоваться
// внедрением зависимостей и передавать хранилища сервисам.
public class InMemoryFilmStorage implements FilmStorage {

    //Создайте классы InMemoryFilmStorage и InMemoryUserStorage, имплементирующие новые интерфейсы,
    // и перенесите туда всю логику хранения, обновления и поиска объектов

    public LocalDate data = LocalDate.of(1895, Month.DECEMBER, 28);
    private Long generateId =0L;
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public boolean containsNewFilms(long id) {
        return films.containsKey(id);
    }


    public void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.debug("Ошибка1");
            throw new ValidationException("Hазвание не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Ошибка2");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(data)) {
            log.debug("Ошибка3");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.debug("Ошибка4");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }

    //добавление фильма;
    @Override
    public Film createFilm(Film film) throws  ValidationException{
        if (films.containsKey(film.getId())) {
            throw new ValidationException("Фильм уже есть в списке");
        } else {
            film.setId(++generateId);
            films.put(film.getId(), film);
        }
        return film;
    }

    //обновление фильма;
    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("фильм не найден");
        } else {
            films.put(film.getId(), film);
            log.info("Фильм {} обновлен.", film.getName());
        }
        return film;
    }

    //получение всех фильмов.
    @Override
    public Collection<Film> allFilms() {
        log.info("Список фильмов {}", films.size());
        return films.values();
    }

    //пользователь ставит лайк фильму.
    @Override
    public Film likeFilm(Long idFilm, Long idUser) {
        Film film = films.get(idFilm);
        film.getLike().add(idUser);
        log.info("Добавление лайка " + film.getLike());
        return film;
    }

    //пользователь удаляет лайк
    @Override
    public Film likeDelete(Long idUser, Long idFilm) {
        Film film = films.get(idFilm);
        film.getLike().remove(idUser);
        log.info("Удаление лайка " + film.getLike());
        return film;
    }

    //возвращает список из первых count фильмов по количеству лайков. Если значение
    //параметра count не задано, верните первые 10
    @Override
    public List<Film> returnFilmsPopular(int count) {
        if (count > films.size()) {
            count = films.size();
        }
        Collection<Film> film = films.values();
        return film.stream().sorted(Comparator.comparingInt(Film::countLikes).reversed())
                .limit(count).collect(Collectors.toList());
    }

    @Override
    public Film filmById(Long id) {
        return films.get(id);
    }


}