package org.zerock.myapp.service;

import org.zerock.myapp.domain.Comments;
import org.zerock.myapp.domain.Recipe;

import java.util.List;

public interface CommentsService {

    List<Comments> getCommentsByRecipe(Recipe recipe);
    void addComment(Comments comment);
    List<Comments> getCommentsWithUsersByRecipe(Recipe recipe);
    void updateComment(Long commentId, String commentContent);
    Comments getCommentById(Long commentId);

    // 댓글 삭제
    void deleteComment(Long commentId);

} // CommentsService
