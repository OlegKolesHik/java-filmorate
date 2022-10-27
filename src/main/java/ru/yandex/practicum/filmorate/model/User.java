package ru.yandex.practicum.filmorate.model;

import lombok.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of="id")
public class User {
    private Long id;
    @NotBlank
    private final String login;
    private String name;
    @Email
    private final String email;
    private final LocalDate birthday;
}
