package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {

    @NotBlank
    @Size(min = 1, max = 200)
    @NotNull
    @Min(1)
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;


}
