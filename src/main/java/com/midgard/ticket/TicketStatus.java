package com.midgard.ticket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum TicketStatus {
    OPEN("open"),
    IMPLEMENTING("implementing"),
    REVIEWING("reviewing"),
    CLOSED("closed");

    @Getter
    private final String status;
}
