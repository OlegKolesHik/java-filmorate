package ru.yandex.practicum.filmorate.storage.logic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private long id = 0L;
    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public User createUser(User user) {
        validate(user);

        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User updateUser(User user) throws NotFoundException {
        validate(user);

        if (users.containsKey(user.getId())) {
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
        return user;
    }

    @Override
    public User getUser(Long id) throws NotFoundException {
        if (id < 0) {
            throw new NotFoundException(String.format("Id не может быть меньше нуля", id));
        }

        return users.get(id);
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    private long generateId() {
        return id += 1;
    }

    private void validate(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен содержать пробелов.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("День рождения не может быть в будущем.");
        }
    }
}

