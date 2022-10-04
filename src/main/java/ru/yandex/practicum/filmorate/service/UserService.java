package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Service//бизнес-логика находится на уровне сервиса, поэтому мы используем аннотацию @Service, чтобы указать, что
// класс принадлежит этому уровню
public class UserService {

    //Service - Бизнес-логика

    private final UserStorage userStorage;
    public InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        inMemoryUserStorage.validate(user);
        return userStorage.create(user);
    }

    public void validateNewUsers(long id) {
        if (!userStorage.containsNewUsers(id)) {
            throw new NotFoundException(String.format("Пользователь не найден", id));
        }
    }

    public User update(User user) {
        validateNewUsers(user.getId());
        inMemoryUserStorage.validate(user);
        return userStorage.update(user);
    }

    public Collection<User> allUsers() {
        return userStorage.allUsers();
    }

    //добавление в друзья.
    public void putFriend(Long id, Long friendId) {
        validateNewUsers(id);
        validateNewUsers(friendId);
        if (id < 0 || friendId < 0) {
            throw new NotFoundException("Id не может быть отрицательным");
            }
        userStorage.putFriend(id, friendId);
    }

    //удаление из друзей.
    public void deleteFriends(Long id, Long friendId) {
        validateNewUsers(id);
        validateNewUsers(friendId);
        if (id < 0 || friendId < 0) {
            throw new NotFoundException("Id не может быть отрицательным");
        }
        userStorage.deleteFriends(id, friendId);
    }

    //возвращаем список пользователей, являющихся его друзьями.
    public List<User> allFriendUser(Long id) {
        validateNewUsers(id);
        if (id <= 0) {
            throw new NotFoundException("Id не может быть отрицательным");
        }
        return userStorage.allFriendUser(id);
    }

    //список друзей, общих с другим пользователем.
    public List<User> listFriendsCommon(Long id, Long otherId) {
        validateNewUsers(id);
        validateNewUsers(otherId);
        if (id < 0 || otherId < 0) {
            throw new NotFoundException("Id не может быть отрицательным");
        }
        return userStorage.listFriendsCommon(id, otherId);
    }

    public User userById(Long id) {
        validateNewUsers(id);
        if(id < 0)
            throw new NotFoundException("Id не может быть отрицательным");
        return userStorage.userById(id);
    }
}
