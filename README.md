# java-filmorate
Template repository for Filmorate project.
![QuickDBD-ER-Диаграмма java-filmorate (1)](https://user-images.githubusercontent.com/98983555/196054994-779754c8-ba49-4713-8bb8-5eadf9f885fa.png)

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



films
-
film_id integer pk
name varchar
description varchar(200)
releaseDate date
duration time
mpa_id

users
-
user_id integer pk
email varchar
login varchar
name varchar
birthday date

genres
-
genre_id integer pk
genre_name varchar

genre_films
-
film_id integer pk FK >- films.film_id
genre_id integer FK >- genres.genre_id

mpa
-
mpa_id pk FK >- films.mpa_id
mpa_name varchar

friends
-
user_id_request integer pk FK >-< users.user_id
is_a_friend boolean 
user_id_friend integer pk FK >-< users.user_id


likes
-
user_id FK >- users.user_id
film_id FK >- films.film_id
