package com.midgard.ticket;

import com.midgard.ticket.requests.*;
import com.midgard.ticket.responses.*;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/ticket")
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

    @PostMapping("/add")
    public ResponseEntity<TicketCreateResponse> addTicket(
            @RequestBody TicketCreateRequest request
    ) {
        log.info(request.toString());
        return ResponseEntity.ok(ticketService.createTicket(request));
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
    public ResponseEntity<TicketStatusResponse> changeTicketStatus(
            @RequestBody TicketStatusRequest request
    ) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(request));
    }

    @GetMapping("/categories")
    public List<TicketCategories> getListOfCategories() {
        return ticketService.getCategoriesList();
    }

    @PutMapping("/categories")
    public ResponseEntity<TicketCategoriesResponse> updateCategoriesForTicket(
            @RequestBody TicketCategoriesRequest request
    ) {
        return ResponseEntity.ok(ticketService.updateTicketCategories(request));
    }

    @GetMapping("/priorities")
    public List<TicketPriority> getListOfPriorities() {
        return ticketService.getPrioritesList();
    }

    @PutMapping("/priorities")
    public ResponseEntity<TicketPriorityResponse> updateTicketPriority(
            @RequestBody TicketPriorityRequest request
    ) {
        return ResponseEntity.ok(ticketService.updateTicketPriority(request));
    }
}
