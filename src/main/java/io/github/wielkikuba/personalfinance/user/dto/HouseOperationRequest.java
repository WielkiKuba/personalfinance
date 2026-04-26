package io.github.wielkikuba.personalfinance.user.dto;

import lombok.Data;

@Data
public class HouseOperationRequest {
    private Long userId;
    private Long userToModifyId;
    private Long houseId;
}
