package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {

//    //Методы из InMemoryUserStorage

Friend addFriendship(Long id, Long friendId);

    void deleteFriendship(Long id, Long friendId);

    List<User> getFriendsOfUser(Long userId);
    List<User> getMutualFriendList(Long userOneId, Long userTwoId);
}
