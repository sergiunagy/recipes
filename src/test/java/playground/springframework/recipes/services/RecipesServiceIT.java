package playground.springframework.recipes.services;

import static org.junit.jupiter.api.Assertions.*;

import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.converters.RecipeCommandToRecipe;
import playground.springframework.recipes.converters.RecipeToRecipeCommand;
import playground.springframework.recipes.domain.Recipe;
import playground.springframework.recipes.domain.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by jt on 6/21/17.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipesServiceIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipesService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe obsRecipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand obsRecipeToRecipeCommand;

    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = obsRecipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}