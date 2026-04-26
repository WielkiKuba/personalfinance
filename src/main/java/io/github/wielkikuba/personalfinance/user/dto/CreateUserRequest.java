package io.github.wielkikuba.personalfinance.user.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String surname;
}
