package com.midgard.ticket;

import com.midgard.user.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    @GetMapping("")
    public List<TicketEntity> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping(path = "/{id}")
    public TicketEntity findTicketById(
            @PathVariable Long id
    ) {
        return ticketService.findTicketById(id);
    }

    @GetMapping(params = "email")
    public List<TicketEntity> findTicketByOwner(
            @RequestParam String email
    ) {
        return ticketService.findTicketByOwner(email);
    }

    @GetMapping(params = "title")
    public List<TicketEntity> findTicketByTitle(
            @RequestParam String title
    ) {
        return ticketService.findTicketByTitle(title);
    }

    @PutMapping("/update")
    public ResponseEntity<TicketUpdateResponse> updateTicket(
            @RequestBody TicketUpdateRequest request
    ) {
        return ResponseEntity.ok(ticketService.updateTicket(request));
    }

    @DeleteMapping("/delete/{ticket_id}/{user_id}")
    public void deleteUserFromIncludedUsers(
            @PathVariable("ticket_id") Long ticket_id,
            @PathVariable("user_id") Long user_id
    ) {
        ticketService.removeUsersByUsername(ticket_id, user_id);
    }

    @PutMapping("/status")
    public ResponseEntity<TicketUpdateResponse> changeTicketStatus(
            @RequestBody TicketUpdateRequest request
    ) {
        ticketService.updateTicketStatus(request);

        return ResponseEntity.ok().build();
    }
}
