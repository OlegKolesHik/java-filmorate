package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

//Storage - хранение того, что нужно бизнес-логике и то, что работает с хранилищем

public interface UserStorage {

    User createUser(User user);

    void deleteUser(User user);

    User updateUser(User user) throws NotFoundException;

    User getUser(Long id) throws NotFoundException;

    List<User> getUserList();
}
