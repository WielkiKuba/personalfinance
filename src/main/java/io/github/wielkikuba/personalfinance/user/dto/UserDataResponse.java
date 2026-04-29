package io.github.wielkikuba.personalfinance.user.dto;

import io.github.wielkikuba.personalfinance.house.House;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDataResponse {

    private Long id;
    private String name;
    private String surname;
    private House house;
}
