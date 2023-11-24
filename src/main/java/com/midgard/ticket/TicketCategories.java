package com.midgard.ticket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TicketCategories {
    INFRASTRUCTURE("Infrastructure"),
    DATABASE("Database"),
    BACKEND("Backend Engineering"),
    FRONTEND("Frontend Engineering"),
    SECURITY("Security");

    @Getter
    public final String category;
}
