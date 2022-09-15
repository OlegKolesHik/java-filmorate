package ru.yandex.practicum.filmorate.model;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Data
public class User {

    @Null
    private  int id;

    @NotNull
    private String email;
    private String login;

    @NotNull
    @NotEmpty
    private String name;
    private LocalDate birthday;
}
