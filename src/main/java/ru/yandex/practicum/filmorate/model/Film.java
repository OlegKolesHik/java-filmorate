package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Data
public class Film {

    @Null
    private int id;

    @NotNull
    @NotEmpty
    private String name;

    private String description;
    private LocalDate releaseDate;
    private int duration;
}
