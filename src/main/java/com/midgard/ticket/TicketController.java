package com.midgard.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

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
}
