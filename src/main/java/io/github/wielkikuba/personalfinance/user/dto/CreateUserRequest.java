package io.github.wielkikuba.personalfinance.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotNull(message = "Name of user cannot be null")
    private String name;
    @NotNull(message = "Surname of user cannot be null")
    private String surname;
}
