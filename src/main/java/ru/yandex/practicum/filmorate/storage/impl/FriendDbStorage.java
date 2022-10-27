package ru.yandex.practicum.filmorate.storage.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;

import java.util.List;

@Slf4j
@Component("dbFriend")
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserStorage userStorage;

    public FriendDbStorage(JdbcTemplate jdbcTemplate, @Qualifier("dbUser") UserStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }

    @Override
    public Friend addFriendship(Long id, Long friendId) {
        if (userStorage.getUser(id) == null) {
            throw new NotFoundException(String.format("Пользователь не найден ", id));
        } else if (userStorage.getUser(friendId) == null) {
            throw new NotFoundException(String.format("Пользователь не найден ", friendId));
        }
        String sqlQuery = "insert into friends(friend_one, friend_two) " +
                "values(?, ?)";
        jdbcTemplate.update(sqlQuery, id, friendId);
        return new Friend(id, friendId);
    }

    @Override
    public void deleteFriendship(Long id, Long friendId) {
        String sqlQuery = "delete from friends where friend_one = ? and friend_two = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public List<User> getFriendsOfUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ValidationException(String.format(
                    "Значение не может быть меньше, или равно нулю ",
                    userId));
        }
        String sqlQuery = "select u.user_id, u.email, u.login, u.name, u.birthday " +
                "from friends f " +
                "join users u on f.friend_two=u.user_id " +
                "where f.friend_one = ?";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userId);
    }

    @Override
    public List<User> getMutualFriendList(Long userOneId, Long userTwoId) {
        String sqlQuery = "select u.user_id, u.email, u.login, u.name, u.birthday " +
                "from users u " +
                "join friends one on u.user_id=one.friend_two " +
                "join friends two on u.user_id=two.friend_two " +
                "where one.friend_one = ? and two.friend_one = ?";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser, userOneId, userTwoId);
    }
}

