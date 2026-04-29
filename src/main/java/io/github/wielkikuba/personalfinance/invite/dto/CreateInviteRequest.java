package io.github.wielkikuba.personalfinance.invite.dto;

import lombok.Data;

@Data
public class CreateInviteRequest {
    private Long senderId;
    private Long recipientId;
}
