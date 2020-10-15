package guru.springframework.recipes.controllers;

import guru.springframework.recipes.services.RecipesService;
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

    @RequestMapping({"/recipe/show/{id}", "recipe/show/{id}", "recipe/show.html"})
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipesService.findById(new Long(id)));

        return ("recipes/show");
    }
}
