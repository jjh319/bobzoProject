package org.zerock.myapp.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.Comments;
import org.zerock.myapp.domain.Recipe;
import org.zerock.myapp.repository.CommentsRepository;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService{

    @Setter(onMethod_ = @Autowired)
    private CommentsRepository commentsRepo;


    @Override
    public List<Comments> getCommentsByRecipe(Recipe recipe) {
        return commentsRepo.findByFkRecipe(recipe);
    }  // getCommentsByRecipe


    @Override
    public void addComment(Comments comment) {
        commentsRepo.save(comment);
    } // addComment

    @Override
    public Comments getCommentById(Long id) {
        return commentsRepo.findById(id).orElse(null);
    } // getCommentById

    @Override
    public List<Comments> getAllComments(Long commentsNum) {

        return commentsRepo.findAll();
    } // getAllComments


} // CommentsServiceImpl
