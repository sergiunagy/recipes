package playground.springframework.recipes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import playground.springframework.recipes.commands.IngredientCommand;
import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.services.IngredientsService;
import playground.springframework.recipes.services.RecipesService;

@Slf4j
@Controller
public class IngredientController {

    private final RecipesService recipesService;
    private final IngredientsService ingredientsService;


    public IngredientController(RecipesService recipesService, IngredientsService ingredientsService) {
        this.recipesService = recipesService;
        this.ingredientsService = ingredientsService;
    }

    @GetMapping
    @RequestMapping({"/recipe/{recipeId}/ingredients"})
    public String getIngredients(@PathVariable Long recipeId, Model model){

        log.debug("Getting ingredient list for recipe id:" + recipeId);


        model.addAttribute("recipe", recipesService.findRecipeCommandById(Long.valueOf(recipeId)));

        return "recipes/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String displayIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model){

        model.addAttribute("ingredient", ingredientsService.findByRecipeIdAndId(recipeId, ingredientId));
        return "recipes/ingredient/show";
    }

}
