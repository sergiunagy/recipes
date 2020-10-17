package playground.springframework.recipes.controllers;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.services.RecipesService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Getter
@Setter
@Builder
@Controller
public class RecipeController {

    private final RecipesService recipesService;

    public RecipeController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @RequestMapping({"/recipe/{id}/show", "/recipe/{id}/show/","recipe/{id}/show/", "recipe/{id}/show.html"})
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipesService.findById(new Long(id)));

        return ("recipes/show");
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model){

        model.addAttribute("recipe", new RecipeCommand());
        return "recipes/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipesService.findRecipeCommandById(Long.valueOf(id)));

        return "recipes/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand commands){

        RecipeCommand command = recipesService.saveRecipeCommand(commands);

        return "redirect:/recipe/" + command.getId() + "/show" ;
    }
}
