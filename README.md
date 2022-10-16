# java-filmorate
Template repository for Filmorate project.
![ER-Диаграмма java-filmorate](https://user-images.githubusercontent.com/98983555/195184300-fb778145-c056-443d-8df1-0c8f8c459ec4.png)

//получение всех фильмов.
SELECT
f.film_id,
f.name,
f.description,
f.releaseDate,
f.duration,
f.mpa_id
FROM films AS f
JOIN mpa ON mpa_id=f.mpa_id
ORDER BY f.mpa_id


//получение списка всех пользователей.
SELECT
user_id,
email,
login,
name,
birthday
FROM users
ORDER BY user


//возвращает список из первых count фильмов по количеству лайков.
SELECT


