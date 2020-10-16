package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface RecipesService {

    public Set<Recipe> getRecipes();
    public Recipe findById(Long id);

    @Transactional
    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
