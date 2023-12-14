package com.midgard.comment;

import com.midgard.configs.JwtService;
import com.midgard.ticket.TicketRepository;
import com.midgard.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        var user = userRepository.findUserByEmail(newComment.getUserEmail()).orElseThrow();


        var ticket = ticketRepository.findById(newComment.getTicket_id());
        if (!ticket.isPresent())
            throw new IllegalStateException("Can not find ticket with id " + newComment.getTicket_id());

        var comment = new CommentEntity();
        comment.setContent(newComment.getContent());
        comment.setUser(user);
        comment.setTicket(ticket.get());
        comment.setTimestamp(LocalDateTime.now());
        commentRepository.save(comment);
        log.info(comment.toString());
        return new CommentResponse(
                LocalDateTime.now(),
                user.getUsername(),
                newComment.getContent(),
                true
        );
    }

    public CommentResponse editComment(CommentEditRequest request) {
        var user = userRepository.findUserByEmail(request.getUser_email()).orElseThrow();
        var optionalComment = commentRepository.findById(request.getComment_id());
        if (!optionalComment.isPresent())
            throw new IllegalStateException("Could not find comment");
        var newComment = optionalComment.get();

        if (!newComment.getUser().getUsername().equals(user))
            throw new IllegalStateException("Current user cannot edit the comment");

        newComment.setContent(request.getContent());
        newComment.setTimestamp(LocalDateTime.now());
        commentRepository.save(newComment);
        return new CommentResponse(
                LocalDateTime.now(),
                user.getUsername(),
                newComment.getContent(),
                true
        );
    }

    public List<CommentEntity> findCommentsForTicket(Long ticketId) {
        var optionalComments = commentRepository.findAll().stream().filter(commentEntity -> Objects.equals(commentEntity.getTicket().getId(), ticketId)).toList();
        if (optionalComments.isEmpty())
            return List.of();
        return optionalComments;
    }

    public void deleteComment(Long id) {
        var optionalComment = commentRepository.findById(id).orElseThrow();
        commentRepository.deleteById(id);
    }

}
