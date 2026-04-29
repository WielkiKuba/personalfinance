package io.github.wielkikuba.personalfinance.house.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateHouseRequest {
    @NotNull(message = "Street of house cannot be null")
    private String street;
    @NotNull(message = "Number of house cannot be null")
    private String number;
    @NotNull(message = "Owner id of house cannot be null")
    private Long owner_id;
}
