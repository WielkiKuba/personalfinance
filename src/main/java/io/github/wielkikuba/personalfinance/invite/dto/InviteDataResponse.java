package io.github.wielkikuba.personalfinance.invite.dto;

import io.github.wielkikuba.personalfinance.invite.Invite;
import io.github.wielkikuba.personalfinance.user.dto.UserDataResponse;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class InviteDataResponse {
    public static InviteDataResponse from(Invite invite){
        return new InviteDataResponse(invite.getId(),UserDataResponse.from(invite.getSender()),UserDataResponse.from(invite.getRecipient()));
    }

    public static List<InviteDataResponse> from(List<Invite> inviteList){
        List<InviteDataResponse> inviteDataResponseList = new ArrayList<>();
        for(Invite invite:inviteList){
            inviteDataResponseList.add(InviteDataResponse.from(invite));
        }
        return inviteDataResponseList;
    }

    private Long id;
    private UserDataResponse sender;
    private UserDataResponse recipient;
}
