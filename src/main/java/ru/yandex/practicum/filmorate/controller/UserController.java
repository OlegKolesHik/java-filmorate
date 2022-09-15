package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int generateId = 0;

    //создание пользователя;
    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        validate(user);
        if (users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь уже зарегистрирован.");
        } else {
            user.setId(generateId++);
            users.put(user.getId(), user);
            log.info("Пользователь {} добавлен", user.getEmail());
            return user;
        }
    }

    //электронная почта не может быть пустой и должна содержать символ @;
    //логин не может быть пустым и содержать пробелы;
    //имя для отображения может быть пустым — в таком случае будет использован логин;
    //дата рождения не может быть в будущем.
    public void validate(User user) throws ValidationException {
        if(user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.debug("Ошибка добавления почты");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin().isBlank() || user.getLogin() == null) {
            log.debug("Ошибка добавления логина");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if(user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Ошибка добавления даты рождения");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if(user.getName().isBlank() || user.getName().equals(" ")) {
            log.debug("Ошибка имени");
            throw new ValidationException("Имя для отображения может быть пустым — в таком случае будет использован логин");
        }
    }

    //обновление пользователя;
    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        validate(user);
        users.put(user.getId(), user);
        log.info("Пользователь {} обновлен", user.getEmail());
        return user;
    }
    //получение списка всех пользователей.
    @GetMapping
    public List<User> allUsers() {
        return new ArrayList<>(users.values());
    }

}