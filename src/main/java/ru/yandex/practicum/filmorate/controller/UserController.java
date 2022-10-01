package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

//@RequiredArgsConstructor //Чтобы скомбинировать краткость внедрения зависимостей через поля с надёжностью внедрения
//// через конструктор можно использовать библиотеку Lombok и аннотацию @RequiredArgsConstructor
////Увидев аннотацию @RequiredArgsConstructor, Lombok создаст примерно такой же конструктор, который был бы
//// при внедрении через конструктор, только без аннотации @Autowired
@Slf4j
@RestController //способ конвертировать объект в тело ответа — объявить контроллер с помощью аннотации @RestController.
// Она делает всё то же самое, что и аннотация @Controller, а также добавляет аннотацию @ResponseBody для каждого
// метода обработчика запросов. Это позволяет немного упростить написание контроллера.
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    //В контроллере прописывается валидация из внешнего мира
    private final UserService userService;

    //создание пользователя;
    @PostMapping
    public User create(@Valid @RequestBody  User user) {
        return userService.create(user);
    }

    //обновление пользователя;
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    //получение списка всех пользователей.
    @GetMapping
    public Collection<User> allUsers() {
        return userService.allUsers();
    }

    //С помощью аннотации @PathVariable добавьте возможность получать каждый фильм и данные о пользователях
    // по их уникальному идентификатору: GET .../users/{id}
    @GetMapping("/{id}")
    public User userById(@PathVariable Long id) {
        return userService.userById(id);
    }

    //PUT /users/{id}/friends/{friendId} — добавление в друзья.
    @PutMapping("/{id}/friends/{friendId}")
    public void putFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.putFriend(id, friendId);
    }

    //DELETE /users/{id}/friends/{friendId} — удаление из друзей.
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriends(id, friendId);
        }

    //GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
    @GetMapping("/{id}/friends")
    public List<User> allFriendUser(@PathVariable Long id) {
        return userService.allFriendUser(id);
    }

    //GET /users/{id}/friends/common/{otherId} — список друзей, общих с другим пользователем.
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> listFriendsCommon(@PathVariable Long id, @PathVariable Long otherId) {
         return userService.listFriendsCommon(id, otherId);
    }
}