package playground.springframework.recipes.services;

import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.converters.RecipeCommandToRecipe;
import playground.springframework.recipes.converters.RecipeToRecipeCommand;
import playground.springframework.recipes.domain.Recipe;
import playground.springframework.recipes.domain.repositories.RecipeRepository;
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
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipesServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe RecipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = RecipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
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
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findRecipeCommandById(Long id){

        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        return recipeToRecipeCommand.convert(recipeOptional.get());
    }
}
