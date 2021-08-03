package com.codegym.repository;

import com.codegym.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface ICommentRepository extends CrudRepository<Comment, Long> {
}
