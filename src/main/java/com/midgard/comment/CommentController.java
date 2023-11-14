package com.midgard.comment;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    @GetMapping
    public List<CommentEntity> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public CommentEntity findCommentById(
            @PathVariable("id") Long id
    ) {
        return commentService.findCommentById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CommentResponse> addCommentToTicket(
            @RequestBody CommentRequest newComment
    ) {
        return ResponseEntity.ok(commentService.addNewComment(newComment));
    }

    @PutMapping("/edit")
    public ResponseEntity<CommentResponse> editCommentToTicket(
            @RequestBody CommentEditRequest request
    ) {
        log.info(request.toString());
        return ResponseEntity.ok(commentService.editComment(request));
    }

    @GetMapping("/ticket/{ticket_id}")
    public List<CommentEntity> findAllCommentsForTicket(
            @PathVariable("ticket_id") Long ticket_id
    ) {
        return commentService.findCommentsForTicket(ticket_id);
    }
}
