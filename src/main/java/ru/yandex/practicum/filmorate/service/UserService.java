package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;


import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
@Service("userService")
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService(@Qualifier("dbUser") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public User getUser(Long id) throws NotFoundException {
        if (id < 0) {
            throw new NotFoundException(String.format("Id не должен быть меньше нуля", id));
        }

        final User user = userStorage.getUser(id);

        if (user == null) {
            throw new NotFoundException(String.format("Пользователь не найден", id));
        }

        return user;
    }

    public User createUser(User user) {
        validate(user);

        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        validate(user);
        final User resultUser = userStorage.getUser(user.getId());

        if (resultUser == null) {
            throw new NotFoundException(String.format("Пользователь не найден в базе данных.", user.getId()));
        }

        return userStorage.updateUser(user);
    }

    public List<User> getUserList() {
        return userStorage.getUserList();
    }

    private User validate(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин должен быть без пробелов");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("День рождения не может быть в будущем");
        }
        return user;
    }

    public Friend addFriendship(@Valid @Positive Long id,
                                    @Valid @Positive Long friendId) {
        return friendStorage.addFriendship(id, friendId);
    }

    public void deleteFriendship(@Valid @Positive Long id,
                                 @Valid @Positive Long friendId) {
        friendStorage.deleteFriendship(id, friendId);
    }

    public List<User> getFriendsList(@Valid @Positive Long userId) {
        return friendStorage.getFriendsOfUser(userId);
    }

    public List<User> getMutualFriendList(@Valid @Positive Long userOneId,
                                          @Valid @Positive Long userTwoId) {
        return friendStorage.getMutualFriendList(userOneId, userTwoId);
    }
}

