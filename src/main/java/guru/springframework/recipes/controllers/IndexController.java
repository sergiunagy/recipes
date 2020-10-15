package guru.springframework.recipes.controllers;

import guru.springframework.recipes.services.RecipesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    //private RecipeRepository recipeRepository;
    private RecipesService recipesService;

    public IndexController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @RequestMapping({"/", "/index", "/index.html"})
    public String getRecipes(Model model){
        log.debug("Recipes service request received");
        model.addAttribute("recipes", recipesService.getRecipes());
        return "index";
    }
}
