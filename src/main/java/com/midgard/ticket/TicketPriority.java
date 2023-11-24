package com.midgard.ticket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TicketPriority {

    LOW("LOW"),
    SEMI_LOW("SEMI_LOW"),
    MEDIUM("MEDIUM"),
    SEMI_HIGH("SEMI_HIGH"),
    HIGH("HIGH");

    @Getter
    public final String priority;
}
