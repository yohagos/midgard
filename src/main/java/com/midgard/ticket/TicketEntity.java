package com.midgard.ticket;

import com.midgard.comment.CommentEntity;
import com.midgard.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TicketEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @ManyToOne
    private UserEntity owner;

    @ManyToMany
    private List<UserEntity> includedUsers;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    @OneToMany
    private List<CommentEntity> comments;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    private List<TicketCategories> categories;

    private String priority;


    public TicketEntity(
            String title,
            UserEntity owner,
            List<UserEntity> includedUsers,
            String content,
            TicketStatus status,
            List<TicketCategories> categories
    ) {
        this.title = title;
        this.owner = owner;
        this.includedUsers = includedUsers;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.status = status;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return String.format("Ticket=[id=%s, title=%s, owner=%s, content=%s]",
                getId(), getTitle(), getOwner(), getContent());
    }

}
