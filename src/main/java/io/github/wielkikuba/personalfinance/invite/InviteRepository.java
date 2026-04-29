package io.github.wielkikuba.personalfinance.invite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
    List<Invite> findBySenderId(Long senderId);
    List<Invite> findByRecipientId(Long recipientId);
    Optional<Invite> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}
