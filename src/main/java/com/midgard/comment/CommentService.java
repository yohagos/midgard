package com.midgard.comment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository repository) {
        commentRepository = repository;
    }

    public List<CommentEntity> getAllComments() {
        return commentRepository.findAll();
    }
}
