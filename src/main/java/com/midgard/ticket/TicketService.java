package com.midgard.ticket;

import com.midgard.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;


    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<TicketEntity> findTicketByOwner(String email) {
        var user = userRepository.findUserByEmail(email);
        if (!user.isPresent())
            throw new IllegalStateException("no user by email " + email);
        var optionalOwners = ticketRepository.findTicketByOwner(user.get());
        if (optionalOwners.isPresent())
            return optionalOwners.get();
        throw new IllegalStateException("could not find Tickets");
    }

    public TicketEntity findTicketById(Long id) {
        var optionalTicket = ticketRepository.findById(id);
        if (!optionalTicket.isPresent())
            throw new IllegalStateException("No ticket with id of " + id);
        return optionalTicket.get();
    }
}
