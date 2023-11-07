package org.zerock.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.domain.Comments;
import org.zerock.myapp.domain.Recipe;
import org.zerock.myapp.domain.Users;

import java.util.List;


@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByFkRecipe(Recipe recipe);


} // end interface
