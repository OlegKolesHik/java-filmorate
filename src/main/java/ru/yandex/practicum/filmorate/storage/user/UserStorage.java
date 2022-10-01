package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserStorage {

    //Storage - хранение того, что нужно бизнес-логике и то, что работает с хранилищем
    User update(User user);
    User create(User user);
    Collection<User> allUsers();
    void putFriend(Long id, Long friendId);

    User userById(Long id);

    void deleteFriends(Long id, Long friendId);

    // возвращаем список пользователей, являющихся его друзьями
    List<User> allFriendUser(Long id);

    List<User> listFriendsCommon(Long id, Long otherId);


}
