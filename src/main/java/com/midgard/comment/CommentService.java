package com.midgard.comment;

import com.midgard.configs.JwtService;
import com.midgard.ticket.TicketRepository;
import com.midgard.user.UserRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    public List<CommentEntity> getAllComments() {
        return commentRepository.findAll();
    }

    public CommentEntity findCommentById(Long id) {
        var optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent())
            throw new IllegalStateException("Cannot find comment by id " + id);
        return optionalComment.get();
    }

    public CommentResponse addNewComment(CommentRequest newComment) {
        var request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        var header = request.getHeader("Authorization").split(" ")[1];
        var username = jwtService.extractUsername(header);

        var ticket = ticketRepository.findById(newComment.getTicket_id());
        if (!ticket.isPresent())
            throw new IllegalStateException("Can not find ticket with id " + newComment.getTicket_id());
        var user = userRepository.findUserByEmail(username);
        if (!user.isPresent())
            throw new IllegalStateException("Can not find user by email " + username);
        var comment = new CommentEntity();
        comment.setContent(newComment.getContent());
        comment.setUser(user.get());
        comment.setTicket(ticket.get());
        commentRepository.save(comment);
        return new CommentResponse(
                LocalDateTime.now(),
                username,
                newComment.getContent(),
                true
        );
    }
}
