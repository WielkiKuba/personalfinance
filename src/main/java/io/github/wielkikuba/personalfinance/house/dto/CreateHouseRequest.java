package io.github.wielkikuba.personalfinance.house.dto;

import lombok.Data;

@Data
public class CreateHouseRequest {

    private String street;
    private String number;
    private Long owner_id;
}
