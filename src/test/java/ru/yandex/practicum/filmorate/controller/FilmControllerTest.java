package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


//@ContextConfiguration(classes = {FilmController.class})
//@SpringBootTest
class FilmControllerTest {
//     Film film = new Film();
//
//     FilmController filmController;
//     InMemoryFilmStorage inMemoryFilmStorage;
//
//    @BeforeEach
//    void fileTest() {
//        inMemoryFilmStorage = new InMemoryFilmStorage();
//        film.setName("nisi eiusmod");
//        film.setDescription("adipisicing");
//        film.setReleaseDate(LocalDate.of(1967, 03, 25));
//        film.setDuration(100);
//    }
//
//    @Test
//    void nameNull() {
//        film.setName(null);
//        Exception exception = assertThrows(ValidationException.class, () -> inMemoryFilmStorage.validate(film));
//        assertEquals("Hазвание не может быть пустым", exception.getMessage());
//    }
//
//    @Test
//    void nameIsEmpty() {
//        film.setName("");
//        Exception exception = assertThrows(ValidationException.class, () -> inMemoryFilmStorage.validate(film));
//        assertEquals("Hазвание не может быть пустым", exception.getMessage());
//    }
//
//    @Test
//    void description200() {
//        film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят " +
//                "разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о " +
//                "Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");
//        Exception exception = assertThrows(ValidationException.class, () -> inMemoryFilmStorage.validate(film));
//        assertEquals("Максимальная длина описания — 200 символов", exception.getMessage());
//    }
//
//    //Дата релиза — не раньше 28 декабря 1895 года
//    @Test
//    void dateRelease() {
//        film.setReleaseDate(inMemoryFilmStorage.data.minusDays(1));
//        Exception exception = assertThrows(ValidationException.class, () -> inMemoryFilmStorage.validate(film));
//        assertEquals("Дата релиза — не раньше 28 декабря 1895 года", exception.getMessage());
//    }
//
//    @Test
//    void validateDuration() {
//        film.setDuration(-1);
//        Exception exception = assertThrows(ValidationException.class, () -> inMemoryFilmStorage.validate(film));
//        assertEquals("Продолжительность фильма должна быть положительной", exception.getMessage());
//    }
//
//    @Test
//    void negativeId(){
//        film.setId(-1);
//        Exception exception = assertThrows(ValidationException.class, () -> inMemoryFilmStorage.validate(film));
//        assertEquals("Идентификатор должен быть положительным", exception.getMessage());
//    }
}