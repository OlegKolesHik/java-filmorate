package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;

public interface LikeStorage {

    Like addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}
