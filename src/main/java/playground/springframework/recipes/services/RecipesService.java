package playground.springframework.recipes.services;

import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.domain.Ingredient;
import playground.springframework.recipes.domain.Recipe;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface RecipesService {

    public Set<Recipe> getRecipes();
    public Recipe findById(Long id);

    @Transactional
    RecipeCommand saveRecipeCommand(RecipeCommand command);

    public RecipeCommand findRecipeCommandById(Long id);

    public void deleteById(Long id);

}
