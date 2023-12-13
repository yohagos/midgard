package com.midgard.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.midgard.comment.CommentEntity;
import com.midgard.files.FilesEntity;
import com.midgard.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketEntity implements Serializable {

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

    @ManyToMany
    private List<CommentEntity> comments;

    @ManyToMany
    private List<FilesEntity> files;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Enumerated(EnumType.STRING)
    private List<TicketCategories> categories;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    private LocalDateTime deadline;

    public TicketEntity(
            String title,
            UserEntity owner,
            List<UserEntity> includedUsers,
            String content,
            TicketStatus status,
            List<TicketCategories> categories,
            TicketPriority priority
    ) {
        this.title = title;
        this.owner = owner;
        this.includedUsers = includedUsers;
        this.content = content;
        this.status = status;
        this.categories = categories;
        this.createdAt = LocalDateTime.now();
        this.priority = priority;
    }

    @Override
    public String toString() {
        return String.format("Ticket=[id=%s, title=%s, owner=%s, content=%s]",
                getId(), getTitle(), getOwner(), getContent());
    }

}
