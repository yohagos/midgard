package com.midgard.ticket.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusRequest {

    private String currentStatus;
    private String changeTo;
    private Long ticket_id;
}
