package playground.springframework.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.springframework.recipes.commands.IngredientCommand;
import playground.springframework.recipes.converters.IngredientToIngredientCommand;
import playground.springframework.recipes.domain.Ingredient;
import playground.springframework.recipes.domain.Recipe;
import playground.springframework.recipes.domain.repositories.RecipeRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final RecipeRepository recipeRepository;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientsServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Transactional
    @Nullable
    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            log.error("Recipe not found for id:" + recipeId);
            //return null;
        }
        log.debug("Service, using recipe:" + recipeOptional.get().getId());

        Optional<IngredientCommand> ingredientOptional = recipeOptional.get().getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if(ingredientOptional == null){
            log.debug("Ingredient not found for id:" + ingredientId);
        }

        return ingredientOptional.get();
    }
}
