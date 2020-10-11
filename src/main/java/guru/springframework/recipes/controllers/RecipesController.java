package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.domain.repositories.RecipeRepository;
import guru.springframework.recipes.services.RecipesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class RecipesController {

    //private RecipeRepository recipeRepository;
    private RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @RequestMapping({"/recipes", "/recipes.index"})
    public String getRecipes(Model model){

        model.addAttribute("recipes", recipesService.getRecipes());
        return "recipes/index";
    }
}
