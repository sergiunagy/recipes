package playground.springframework.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import playground.springframework.recipes.commands.IngredientCommand;
import playground.springframework.recipes.converters.IngredientToIngredientCommand;
import playground.springframework.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import playground.springframework.recipes.domain.Ingredient;
import playground.springframework.recipes.domain.Recipe;
import playground.springframework.recipes.domain.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
class IngredientsServiceImplTest {

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    IngredientsServiceImpl ingredientsService;


    IngredientCommand ingredientCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientCommand = IngredientCommand.builder()
                .id(255L)
                .description("Test")
                .build();
    }

    @Test
    void findByRecipeIdAndId() {

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
        when(ingredientToIngredientCommand.convert(any(Ingredient.class))).thenReturn(ingredientCommand);

        IngredientCommand ingredient = ingredientsService.findByRecipeIdAndId(1L, 255L);

        // then
        assertNotNull(ingredient);
        assertEquals(255, ingredient.getId());

        verify(recipeRepository, times(1)).findById(anyLong());

    }
}