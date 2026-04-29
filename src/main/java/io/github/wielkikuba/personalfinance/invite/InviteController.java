package io.github.wielkikuba.personalfinance.invite;

import io.github.wielkikuba.personalfinance.invite.dto.CreateInviteRequest;
import io.github.wielkikuba.personalfinance.invite.dto.InviteDataResponse;
import io.github.wielkikuba.personalfinance.user.dto.UserDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    @PostMapping
    public ResponseEntity<InviteDataResponse> createInvite(@Valid @RequestBody CreateInviteRequest createInviteRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(InviteDataResponse.from(inviteService.createInvite(createInviteRequest.getSenderId(),createInviteRequest.getRecipientId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InviteDataResponse> getInviteById(@PathVariable("id") Long id){
        return ResponseEntity.ok(InviteDataResponse.from(inviteService.getInviteById(id)));
    }

    @GetMapping("/sender/{id}")
    public ResponseEntity<List<InviteDataResponse>> getInviteBySender(@PathVariable("id") Long senderId){
        return ResponseEntity.ok(InviteDataResponse.from(inviteService.getInviteBySender(senderId)));
    }

    @GetMapping("/recipient/{id}")
    public ResponseEntity<List<InviteDataResponse>> getInviteByRecipient(@PathVariable("id") Long recipientId){
        return ResponseEntity.ok(InviteDataResponse.from(inviteService.getInviteByRecipient(recipientId)));
    }

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<InviteDataResponse> getInviteBySenderAndRecipient(@PathVariable("senderId") Long senderId, @PathVariable("recipientId") Long recipientId){
        return ResponseEntity.ok(InviteDataResponse.from(inviteService.getInviteBySenderAndRecipient(senderId,recipientId)));
    }

    @PostMapping("accept/{inviteId}")
    public ResponseEntity<UserDataResponse> inviteAccept(@PathVariable("inviteId") Long inviteId, @RequestHeader("X-Session-User-Id") Long sessionUserId){
        return ResponseEntity.ok(inviteService.inviteAccept(inviteId,sessionUserId));
    }

    @DeleteMapping("/{inviteId}")
    public ResponseEntity<Void> deleteInvite(@PathVariable("inviteId") Long inviteId){
        inviteService.deleteInvite(inviteId);
        return ResponseEntity.noContent().build();
    }
}