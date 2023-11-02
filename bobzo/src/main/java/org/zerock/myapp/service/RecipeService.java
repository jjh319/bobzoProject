package org.zerock.myapp.service;

import org.zerock.myapp.domain.Recipe;
import org.zerock.myapp.domain.RecipeDTO;

import java.util.List;

public interface RecipeService {

    public abstract void addRecipe(Recipe recipe);
    public abstract void recipeWrite(RecipeDTO recipeDTO, String LoggedInUserId);
    public abstract List<Recipe> getAllRecipes();
    public abstract List<Recipe> getAllRecipesOrderedByRecipeNumberDesc();
    public abstract Integer findHighestRecipeNumber();

} // end interface