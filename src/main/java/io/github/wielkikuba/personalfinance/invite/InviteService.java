package io.github.wielkikuba.personalfinance.invite;

import io.github.wielkikuba.personalfinance.user.User;
import io.github.wielkikuba.personalfinance.user.UserService;
import io.github.wielkikuba.personalfinance.user.dto.UserDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;
    private final UserService userService;

    @Transactional
    public Invite createInvite(Long senderId,Long recipientId){
        Invite invite = Invite.builder().sender(userService.getUserById(senderId)).recipient(userService.getUserById(recipientId)).build();
        return inviteRepository.save(invite);
    }

    @Transactional(readOnly = true)
    public Invite getInviteById(Long id){
        return inviteRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Invite "+id+" does not exists"));
    }

    @Transactional(readOnly = true)
    public Invite getInviteBySenderAndRecipient(Long senderId,Long recipientId){
        return inviteRepository.findBySenderIdAndRecipientId(senderId,recipientId).orElseThrow(()-> new NoSuchElementException("Invite does not exists"));
    }

    @Transactional(readOnly = true)
    public List<Invite> getInviteBySender(Long senderId){
        return inviteRepository.findBySenderId(senderId);
    }

    @Transactional(readOnly = true)
    public List<Invite> getInviteByRecipient(Long recipientId){
        return inviteRepository.findByRecipientId(recipientId);
    }

    @Transactional
    public void deleteInvite(Long id){
        Invite invite = getInviteById(id);
        inviteRepository.delete(invite);
    }

    @Transactional
    public UserDataResponse inviteAccept(Long inviteId, Long sessionUserId){
        User sessionUser = userService.getUserById(sessionUserId);
        Invite invite = getInviteById(inviteId);
        boolean isOwner = false;
        if(sessionUser.getId().equals(invite.getRecipient().getId())){isOwner = true;}
        if(isOwner){
            UserDataResponse userDataResponse = UserDataResponse.from(userService.modifyUser(sessionUserId,null,null,invite.getSender().getHouse().getId()));
            deleteInvite(inviteId);
            return userDataResponse;
        }else{
            throw new SecurityException("Permission denied");
        }
    }
}
