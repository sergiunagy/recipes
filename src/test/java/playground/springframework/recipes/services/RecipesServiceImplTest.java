package playground.springframework.recipes.services;

import playground.springframework.recipes.converters.RecipeCommandToRecipe;
import playground.springframework.recipes.converters.RecipeToRecipeCommand;
import playground.springframework.recipes.domain.Recipe;
import playground.springframework.recipes.domain.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipesServiceImplTest {

    RecipesServiceImpl recipesService;

    @Mock
    RecipeToRecipeCommand obsRecipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe obsRecipeCommandToRecipe;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);

        recipesService=new RecipesServiceImpl(recipeRepository, obsRecipeCommandToRecipe, obsRecipeToRecipeCommand);
    }

    @Test
    void getRecipes() throws Exception {
        Recipe recipe = new Recipe();
        Set recipesData = new HashSet();
        recipesData.add(recipe);

        // program mock to return the recipe
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipesService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById(){
        Long testId = 255L;
        Recipe retRecipe = Recipe.builder().id(testId).description("Test recipe").build();

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(retRecipe));

        Recipe recipe = recipesService.findById(testId);

        assertNotNull(recipe);
        assertEquals(testId, recipe.getId());

    }

    @Test
    void deleteById() {
        // given
        Long idToDelete = 1L;

        // when
        recipesService.deleteById(idToDelete);

        // No when because mathod has void return type
        // then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}