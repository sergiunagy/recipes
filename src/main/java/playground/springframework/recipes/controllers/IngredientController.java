package playground.springframework.recipes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import playground.springframework.recipes.services.RecipesService;

@Slf4j
@Controller
public class IngredientController {

    private final RecipesService recipesService;


    public IngredientController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @GetMapping
    @RequestMapping({"/recipe/{recipeId}/ingredients"})
    public String getIngredients(@PathVariable Long recipeId, Model model){

        log.debug("Getting ingredient list for recipe id:" + recipeId);


        model.addAttribute("recipe", recipesService.findRecipeCommandById(Long.valueOf(recipeId)));

        return "recipes/ingredient/list";
    }
}
