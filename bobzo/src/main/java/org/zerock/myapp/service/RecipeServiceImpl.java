package org.zerock.myapp.service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.myapp.domain.Categories;
import org.zerock.myapp.domain.Recipe;
import org.zerock.myapp.domain.RecipeDTO;
import org.zerock.myapp.domain.Users;
import org.zerock.myapp.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@NoArgsConstructor

@Service
public class RecipeServiceImpl implements RecipeService{

    @Setter(onMethod_ = @Autowired)
    private RecipeRepository recipeRepo;

    @Setter(onMethod_ = @Autowired)
    private UsersService usersService;

    @Setter(onMethod_ = @Autowired)
    private CategoriesService categoriesService;



    @Override
    public void addRecipe(Recipe recipe) {
        Integer highestRecipeNumber = recipeRepo.findHighestRecipeNumber();

        if (highestRecipeNumber == null) {
            highestRecipeNumber = 0;
        }

        // 다음 recipeNumber 값을 설정
        recipe.setRecipeNumber(highestRecipeNumber + 1);

        // 게시글 저장
        recipeRepo.save(recipe);
    }


    @Override
    public void recipeWrite(RecipeDTO recipeDTO, String LoggedInUserId) {

        Users loggedInUserId =  usersService.getUserById(LoggedInUserId);

        if(loggedInUserId == null){
            ;;
            return;
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setContent(recipeDTO.getContent());

        // 로그인 된 유저 ID값을 불러온다.
        recipe.setFkUsers(loggedInUserId);

        // 카테고리 처리
        String selectedCategoryName = recipeDTO.getCategories();
        Categories selectedCategories = categoriesService.getCategoryByName(selectedCategoryName);

        if(selectedCategories != null){
            recipe.setFkCategories(selectedCategories);
            recipeRepo.save(recipe);
        } // end class




    } // recipeWrite

    @Override
    public List<Recipe> getAllRecipes() {
        return  recipeRepo.findAll();
    }

    @Override
    public List<Recipe> getAllRecipesOrderedByRecipeNumberDesc() {
        return recipeRepo.findAllByOrderByRecipeNumberDesc();
    }

    @Override
    public Integer findHighestRecipeNumber() {
        return recipeRepo.findHighestRecipeNumber();
    }
} // end class
