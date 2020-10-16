package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.converters.RecipeCommandToRecipe;
import guru.springframework.recipes.converters.RecipeToRecipeCommand;
import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.domain.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipesServiceImpl implements RecipesService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe obsRecipeCommandToRecipe;
    private final RecipeToRecipeCommand obsRecipeToRecipeCommand;

    public RecipesServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe obsRecipeCommandToRecipe, RecipeToRecipeCommand obsRecipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.obsRecipeCommandToRecipe = obsRecipeCommandToRecipe;
        this.obsRecipeToRecipeCommand = obsRecipeToRecipeCommand;
    }

    public Set<Recipe> getRecipes() {
        /*Solution from course - create a local hashSet and return it. Unclear what benefits this has*/
        log.debug("In the service ..");
        Set<Recipe> rec = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(rec::add);
        return rec;
        /*My solution: use the HashSet returned by the repository*/
        //return recipeRepository.findAll();
    }

    public Recipe findById(Long id) {

        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()) {
            throw new RuntimeException("No recipe fiound with ID:" + id);
        }
        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = obsRecipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return obsRecipeToRecipeCommand.convert(savedRecipe);
    }
}
