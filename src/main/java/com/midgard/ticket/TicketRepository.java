package com.midgard.ticket;

import com.midgard.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    Optional<List<TicketEntity>> findTicketByOwner(UserEntity user);

    Optional<List<TicketEntity>> findTicketByTitleContaining(String title);
}
