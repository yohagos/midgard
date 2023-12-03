package com.midgard.ticket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public enum TicketCategories implements Serializable {
    INFRASTRUCTURE("INFRASTRUCTURE"),
    DATABASE("DATABASE"),
    BACKEND("BACKEND"),
    FRONTEND("FRONTEND"),
    SECURITY("SECURITY");

    @Getter
    public final String category;
}
