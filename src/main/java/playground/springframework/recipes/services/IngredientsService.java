package playground.springframework.recipes.services;

import playground.springframework.recipes.commands.IngredientCommand;

public interface IngredientsService {

    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long id);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
}
