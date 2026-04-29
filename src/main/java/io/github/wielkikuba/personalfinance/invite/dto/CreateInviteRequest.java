package io.github.wielkikuba.personalfinance.invite.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInviteRequest {
    @NotNull(message = "Sender id cannot be null")
    private Long senderId;
    @NotNull(message = "Recipient id cannot be null")
    private Long recipientId;
}
