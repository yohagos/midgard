package com.midgard.ticket;

import com.midgard.comment.CommentEntity;
import com.midgard.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    private UserEntity owner;

    @ManyToMany
    private List<UserEntity> includedUsers;

    @Column(name = "content_text")
    private String content;

    @Column(name = "created_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "closed_timestamp")
    private LocalDateTime closedAt;

    @OneToMany
    private List<CommentEntity> comments;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;


    public TicketEntity(String title, UserEntity owner, List<UserEntity> includedUsers, String content, TicketStatus status) {
        this.title = title;
        this.owner = owner;
        this.includedUsers = includedUsers;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Ticket=[id=%s, title=%s, owner=%s, content=%s]",
                getId(), getTitle(), getOwner(), getContent());
    }

}
