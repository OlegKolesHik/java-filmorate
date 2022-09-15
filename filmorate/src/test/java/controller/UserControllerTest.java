package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {UserController.class})
class UserControllerTest {

    //Добавьте тесты для валидации. Убедитесь, что она работает на граничных условиях.
    //Проверьте, что валидация не пропускает пустые или неверно заполненные поля.
    User user = new User();
    UserController userController;

    @BeforeEach
    void creatTestUser() {
        userController = new UserController();
        user.setEmail("mail@mail.ru");
        user.setLogin("dolore");
        user.setName("Nick Name");
        user.setBirthday(LocalDate.of(1946, 8, 20));
        user.setId(100);
    }

    //Пустая почта
    @Test
    void nullEmail() {
        user.setEmail(null);
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    //Пустой логин
    @Test
    void loginNull() {
        user.setLogin(null);
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
    }


    //Отрицательный логин
    @Test
    void negativeId() {
        user.setId(-100);
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Логин не может быть отрицательным", exception.getMessage());
    }

    //Дата рождения в будущем
    @Test
    void notBirthdayFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }

    //пустое имя
    @Test
    void nullName() {
        user.setName(null);
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Имя для отображения может быть пустым — в таком случае будет использован " +
                "логин", exception.getMessage());
    }
}
