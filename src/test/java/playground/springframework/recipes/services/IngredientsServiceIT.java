package playground.springframework.recipes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import playground.springframework.recipes.commands.IngredientCommand;
import playground.springframework.recipes.converters.IngredientCommandToIngredient;
import playground.springframework.recipes.converters.IngredientToIngredientCommand;
import playground.springframework.recipes.converters.UnitOfMeasureCommandToUnitOfMeasure;
import playground.springframework.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import playground.springframework.recipes.domain.Ingredient;
import playground.springframework.recipes.domain.Recipe;
import playground.springframework.recipes.domain.repositories.RecipeRepository;
import playground.springframework.recipes.domain.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientsServiceIT {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientCommand ingredientCommand;

    IngredientsService ingredientsService;

    public IngredientsServiceIT() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientCommand = IngredientCommand.builder()
                .id(255L)
                .description("Test")
                .build();

        ingredientsService = new IngredientsServiceImpl(
                recipeRepository,
                unitOfMeasureRepository,
                ingredientToIngredientCommand,
                ingredientCommandToIngredient);
    }

    @Test
    void testFindByRecipeIdAndId() {
        // given
        // ingredientCommand
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.builder().id(1L).build());
        ingredients.add(Ingredient.builder().id(2L).build());
        ingredients.add(Ingredient.builder().id(255L).build());

        Recipe recipe = Recipe.builder()
                .id(1L)
                .ingredients(ingredients)
                .build();

        // when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        //when(toIngredientCommand.convert(any(Ingredient.class))).thenReturn(ingredientCommand);

        IngredientCommand ingredient = ingredientsService.findByRecipeIdAndId(1L, 255L);

        // then
        assertNotNull(ingredient);
        assertEquals(255, ingredient.getId());

        verify(recipeRepository, times(1)).findById(anyLong());
    }
}