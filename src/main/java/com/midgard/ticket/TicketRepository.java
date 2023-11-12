package com.midgard.ticket;

import com.midgard.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    /*void updateTicketContent(Long id, String content);

    void updateTicketOwner(Long id, UserEntity user);*/

    Optional<List<TicketEntity>> findTicketByOwner(UserEntity user);

    Optional<List<TicketEntity>> findTicketByTitle(String title);


}
