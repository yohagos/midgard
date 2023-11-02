package com.midgard.ticket;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository repository) {
        ticketRepository = repository;
    }

    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }
}
