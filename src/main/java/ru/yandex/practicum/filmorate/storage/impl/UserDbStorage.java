package ru.yandex.practicum.filmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserStorage;
import java.sql.*;
import java.util.List;

@Slf4j
@Component("dbUser")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users(login, name, email, birthday) values(?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public void deleteUser(User user) {
        String sqlQuery = "delete from users where user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
    }

    @Override
    public User updateUser(User user) throws NotFoundException {
        String sqlQuery = "update users set " +
                "login = ?, name = ?, email = ?, birthday = ? " +
                "where user_id = ?";

        try {
            jdbcTemplate.update(sqlQuery,
                    user.getLogin(),
                    user.getName(),
                    user.getEmail(),
                    user.getBirthday(),
                    user.getId());
        } catch(DataAccessException e) {
            throw new ValidationException("Ошибка при получения из базы данных");
        }
        return getUser(user.getId());
    }

    @Override
    public User getUser(Long id) {
        int checkUser = jdbcTemplate.update(
                "update users set user_id=? where user_id=?", id, id);
        if (checkUser == 0) {
            return null;
        }
        String sqlQuery = "select user_id, login, name, email, birthday " +
                "from users where user_id = ?";

        return jdbcTemplate.queryForObject(sqlQuery, UserDbStorage::makeUser, id);
    }

    static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        User resultUser = new User(
                rs.getString("login"),
                rs.getString("email"),
                rs.getDate("birthday").toLocalDate()
        );
        resultUser.setId(rs.getLong("user_id"));
        resultUser.setName(rs.getString("name"));
        return resultUser;
    }

    @Override
    public List<User> getUserList() {
        String sqlQuery = "select user_id, login, name, email, birthday from users";
        return jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser);
    }
}

