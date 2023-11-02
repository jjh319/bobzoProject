package org.zerock.myapp.controller;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.myapp.domain.Recipe;
import org.zerock.myapp.domain.RecipeDTO;
import org.zerock.myapp.service.RecipeService;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Log4j2
@NoArgsConstructor


@RequestMapping("/recipe/*")
@Controller
public class RecipeController {

    @Setter(onMethod_ = @Autowired)
    private RecipeService recipeService;

    @GetMapping("/recipe")
    public String showRecipeList(Model model) {
        log.trace("showRecipeList() invoked.", model);
        List<Recipe> recipes = recipeService.getAllRecipesOrderedByRecipeNumberDesc();
        model.addAttribute("recipes", recipes);
        return "recipe/recipe";
    }


    @GetMapping("/write")
    String write(Model model){
        log.trace("write() Invoked.");

        model.addAttribute("recipeDTO", new RecipeDTO()); // 폼을 위한 빈 RecipeDTO 생성
        return "/recipe/write"; // Thymeleaf 템플릿 이름


    } // write 글작성

    @PostMapping("/updateRecipe")
    public String updateRecipe(@ModelAttribute("recipeDTO") RecipeDTO recipeDTO, Principal principal){

        if(principal != null) {
            String loggedInUserId = principal.getName();
            recipeService.recipeWrite(recipeDTO, loggedInUserId);
        }

        return "redirect:/recipe/recipe";
    } // updateRecipe


} // end class
