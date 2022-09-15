package ru.yandex.practicum.filmorate.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;

@ContextConfiguration(classes = {UserController.class})
@SpringBootTest
class UserControllerTest {

     User user = new User();
     @Autowired
     UserController userController;

    @BeforeEach
    void userTest() {
        userController = new UserController();
        user.setLogin("dolore");
        user.setName("Nick Name");
        user.setEmail("mail@mail.ru");
        user.setBirthday(LocalDate.of(1946, 8, 20));
    }


    //Добавьте тесты для валидации. Убедитесь, что она работает на граничных условиях.
//Проверьте, что валидация не пропускает пустые или неверно заполненные поля.
// Посмотрите, как контроллер реагирует на пустой запрос.


    @Test
    void loginNull() {
        user.setLogin(null);
        user.setLogin(" ");
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    void EmptyName() throws ValidationException {
        user.setName("");
        userController.validate(user);
        assertEquals(user.getLogin(), user.getName());

    }

    @Test
    void emailNull() {
        user.setEmail(null);
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    @Test
    void emailNotSymbol() {
        user.setEmail("mailmail.ru");
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", exception.getMessage());
    }

    @Test
    void negativeId() {
        user.setId(-1);
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Идентификатор должен быть положительным", exception.getMessage());
    }

    @Test
    void birthdayFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Exception exception = assertThrows(ValidationException.class, () -> userController.validate(user));
        assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }
}