package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.domain.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
public class RecipesController {

    private RecipeRepository recipeRepository;

    public RecipesController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"/recipes", "/recipes.index"})
    public String getRecipes(Model model){

        model.addAttribute("recipes", recipeRepository.findAll());
        return "recipes/index";
    }
}
