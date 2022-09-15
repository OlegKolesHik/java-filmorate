package controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {FilmController.class})
class FilmControllerTest {

    Film film = new Film();
    FilmController filmController;

    @BeforeEach
    void creatTestFilm() {
        filmController = new FilmController();
        film.setName("nisi eiusmod");
        film.setDescription("adipisicing");
        film.setReleaseDate(LocalDate.of(1967, 3, 25));
        film.setDuration(100);
    }

    @Test
    void filmName() {
        film.setName(null);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.validate(film));
        assertEquals("Hазвание не может быть пустым", exception.getMessage());
    }

    @Test
    void maxDescription() {
        film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят" +
                " разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов," +
                " который за время «своего отсутствия», стал кандидатом Коломбани.");
        Exception exception = assertThrows(ValidationException.class, () -> filmController.validate(film));
        assertEquals("Максимальная длина описания — 200 символов", exception.getMessage());

    }

    @Test
    void duration() {
        film.setDuration(-1); {
            Exception exception = assertThrows(ValidationException.class, () -> filmController.validate(film));
            assertEquals("Продолжительность фильма должна быть положительной", exception.getMessage());

        }
    }
}