package playground.springframework.recipes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import playground.springframework.recipes.commands.IngredientCommand;
import playground.springframework.recipes.services.IngredientsService;
import playground.springframework.recipes.services.RecipesService;
import playground.springframework.recipes.services.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {

    private final RecipesService recipesService;
    private final IngredientsService ingredientsService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipesService recipesService, IngredientsService ingredientsService, UnitOfMeasureService unitOfMeasureService) {
        this.recipesService = recipesService;
        this.ingredientsService = ingredientsService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping({"/recipe/{recipeId}/ingredients"})
    public String getIngredients(@PathVariable Long recipeId, Model model) {

        log.debug("Getting ingredient list for recipe id:" + recipeId);


        model.addAttribute("recipe", recipesService.findRecipeCommandById(Long.valueOf(recipeId)));

        return "recipes/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String displayIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {

        model.addAttribute("ingredient", ingredientsService.findByRecipeIdAndId(recipeId, ingredientId));
        return "recipes/ingredient/show";
    }

    @GetMapping
    @RequestMapping({"recipe/{recipeId}/ingredient/{ingredientId}/update"})
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long ingredientId,
                                         Model model) {

        model.addAttribute("ingredient", ingredientsService.findByRecipeIdAndId(recipeId, ingredientId));

        model.addAttribute("uomList", unitOfMeasureService.listAllUOMs());

        return "recipes/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {

        IngredientCommand savedCommand = ingredientsService.saveIngredientCommand(ingredientCommand);

        log.debug("saved recipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId()+ "/ingredient/" + savedCommand.getId() +"/show";
    }
}
