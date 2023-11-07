package org.zerock.myapp.service;

import org.zerock.myapp.domain.Comments;
import org.zerock.myapp.domain.Recipe;

import java.util.List;

public interface CommentsService {
    List<Comments> getCommentsByRecipe(Recipe recipe);
    void addComment(Comments comment);

    Comments getCommentById(Long id);
    List<Comments> getAllComments(Long commentsNum);

} // CommentsService
