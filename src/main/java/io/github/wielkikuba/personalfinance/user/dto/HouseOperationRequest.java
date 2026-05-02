package io.github.wielkikuba.personalfinance.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HouseOperationRequest {

    @NotNull(message = "Id of user to be modified cannot be null")
    private Long userToModifyId;
    @NotNull(message = "House id cannot be null")
    private Long houseId;
}
