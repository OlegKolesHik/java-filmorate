package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component //Добавьте к InMemoryFilmStorage и InMemoryUserStorage аннотацию @Component, чтобы впоследствии пользоваться
// внедрением зависимостей и передавать хранилища сервисам.
public class InMemoryUserStorage implements UserStorage {

    //Создайте классы InMemoryFilmStorage и InMemoryUserStorage, имплементирующие новые интерфейсы,
    // и перенесите туда всю логику хранения, обновления и поиска объектов

    private final Map<Long, User> users = new HashMap<>();
    private Long generateId =0l;

    //создание пользователя;
    @Override
    public User create(User user) {
        if (users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь уже зарегистрирован.");
        } else {
            user.setId(++generateId);
            users.put(generateId, user);
            log.info("Пользователь {} добавлен", user.getEmail());
            return user;
        }
    }

    @Override
    //обновление пользователя;
    public User update(User user) {
        Long id = user.getId();
        if (id < 0) {
            throw new NotFoundException("Id не может быть отрицательным");
        }
        users.replace(id, user);
        log.info("Пользователь {} обновлен", user.getEmail());
        return user;
    }


    @Override
    //получение списка всех пользователей.
    public Collection<User> allUsers() {
        log.info("Количество пользователей {}", users.size());
        return users.values();
    }


    public void validate(User user)  {
        if(user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.debug("Ошибка добавления почты");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ") || user.getLogin().isBlank()) {
            log.debug("Ошибка добавления логина");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if(user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            log.debug("Ошибка добавления даты рождения");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if(user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
            log.debug("Имя для отображения может быть пустым — в таком случае будет использован логин");

        }
    }

    //добавление в друзья
    @Override
    public void putFriend(Long id, Long friendId) {
        User requestFriend = users.get(id);; // отправленный запрос
        User receivedRequest = users.get(friendId); // полученный запрос
        requestFriend.getFriends().add(friendId); // добавили в Set отправленный запрос
        receivedRequest.getFriends().add(id); // добавили в Set полученный запрос
    }

    //удаление из друзей
    @Override
    public void deleteFriends(Long id, Long friendId){
        User requestFriend = users.get(id); // отправленный запрос
        User receivedRequest = users.get(friendId); // полученный запрос
        requestFriend.getFriends().remove(friendId); // удалили из Set отправленный запрос
        receivedRequest.getFriends().remove(id); // удалили из Set полученный запрос

    }

    @Override
    public User userById(Long id) {
        return users.get(id);
    }


    // список друзей, общих с другим пользователем.
    @Override
    public List<User> listFriendsCommon(Long oneId, Long twoId) {
        Set<Long> newOneId = users.get(oneId).getFriends();
        Set<Long> newTwoId = users.get(twoId).getFriends();
        Set<Long> common = new HashSet<>(newOneId);
        common.retainAll(newTwoId);
        List<User> listUsers = new ArrayList<>();
        for (Long newUsers : common) {
            listUsers.add(users.get(newUsers));
        }
        return listUsers;
    }

    // возвращаем список пользователей, являющихся его друзьями
    @Override
    public  List<User> allFriendUser(Long id) {
        Set<Long> allFriendsUser = users.get(id).getFriends();
        List<User> friends= new ArrayList<>();
           for(Long idFriends: allFriendsUser) {
           friends.add(users.get(idFriends));

          }
        return friends;
    }
}
