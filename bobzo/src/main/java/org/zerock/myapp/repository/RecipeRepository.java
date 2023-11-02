package org.zerock.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.myapp.domain.Recipe;
import org.zerock.myapp.domain.Users;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>{
    List<Recipe> findByFkUsers(Users users);
    List<Recipe> findAllByOrderByRecipeNumberDesc();


    @Query("SELECT MAX(r.recipeNumber) FROM Recipe r")
    Integer findHighestRecipeNumber();
} // end interface
