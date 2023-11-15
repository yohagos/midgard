package com.midgard.ticket;

import com.midgard.configs.JwtService;
import com.midgard.user.UserRepository;
import com.midgard.util.TokenUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService implements TokenUtil {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

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

    public List<TicketEntity> findTicketByTitle(String title) {
        var optionalTickets = ticketRepository.findTicketByTitleContaining(title);
        if (!optionalTickets.isPresent())
            throw new IllegalStateException("No ticket with title " + title + " was found");
        return optionalTickets.get();
    }

    public TicketUpdateResponse updateTicket(TicketUpdateRequest request) {
        var ticket = findTicketById(request.getId());

        Optional.ofNullable(request.getContent())
                .filter(
                        content -> !content.isEmpty()
                                || !content.contentEquals(ticket.getContent())
                ).ifPresent(ticket::setContent);
        Optional.ofNullable(request.getStatus())
                .filter(
                        stat -> stat != null
                )
                .ifPresent(ticket::setStatus);
        Optional.ofNullable(request.getTitle())
                .filter(
                        title -> !title.isEmpty()
                                || !title.contentEquals(ticket.getTitle())
                )
                .ifPresent(ticket::setTitle);
        Optional.ofNullable(request.getComments())
                .filter(
                        comment -> !comment.isEmpty()
                )
                .ifPresent(ticket::setComments);
        Optional.ofNullable(request.getOwnerUser())
                .filter(
                        user -> !user.equals(
                                userRepository.findUserByEmail(user.getEmail()).get()
                        )
                )
                .ifPresent(ticket::setOwner);
        Optional.ofNullable(request.getIncludedUsers())
                .filter(
                        users -> !users.isEmpty()
                )
                .ifPresent(ticket.getIncludedUsers()::addAll);
        ticketRepository.save(ticket);
        return TicketUpdateResponse.builder().response("updated successfully").build();
    }

    public void removeUsersByUsername(Long ticket_id, Long user_id) {
        var ticket = findTicketById(ticket_id);
        var users = ticket.getIncludedUsers();
        var iterator = users.iterator();
        while (iterator.hasNext()) {
            var user = iterator.next();
            if (user.getId().equals(user_id)) {
                iterator.remove();
                break;
            }
        }
        ticket.setIncludedUsers(users);
        ticketRepository.save(ticket);
    }

    @Override
    public String getCurrentUsername() {
        var request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        var header = request.getHeader("Authorization").split(" ")[1];
        return jwtService.extractUsername(header);
    }

    public TicketStatusResponse updateTicketStatus(TicketStatusRequest request) {
        var requestUser = userRepository.findUserByEmail(getCurrentUsername());
        var optionalTicket = ticketRepository.findById(request.getTicket_id());
        if (!requestUser.isPresent() || !optionalTicket.isPresent())
            throw new IllegalStateException("Cannot find user by " + getCurrentUsername() + " or ticket cannot be found id " + request.getTicket_id());
        var ticket = optionalTicket.get();
        var user = requestUser.get();
        if (ticket.getStatus().equals(TicketStatus.CLOSED))
            throw new IllegalStateException("Ticket already closed. Please create a new ticket to continue");

        switch (request.getChangeTo()) {
            case "IMPLEMENTING":
                ticket.setStatus(TicketStatus.IMPLEMENTING);
                break;
            case "REVIEWING":
                ticket.setStatus(TicketStatus.REVIEWING);
                break;
            case "CLOSED":
                ticket.setStatus(TicketStatus.CLOSED);
                break;
            default:
                break;

        }

        ticketRepository.save(ticket);

        return new TicketStatusResponse(
                ticket.getStatus().toString(),
                ticket.getId(),
                true
        );
    }
}
