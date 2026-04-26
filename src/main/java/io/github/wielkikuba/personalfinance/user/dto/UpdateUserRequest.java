package io.github.wielkikuba.personalfinance.user.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String surname;
    private Long houseId;
}
