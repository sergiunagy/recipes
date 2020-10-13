package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.domain.repositories.RecipeRepository;
import guru.springframework.recipes.services.RecipesService;
import guru.springframework.recipes.services.RecipesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipesControllerTest {

    RecipesController recipesController;

    @Mock
    RecipesService recipesService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipesController=new RecipesController(recipesService);
    }

    @Test
    void getRecipes() throws Exception{

        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe().builder().id(1L).build());
        recipes.add(new Recipe().builder().id(2L).build());

        // program mock return
        when(recipesService.getRecipes()).thenReturn(recipes);
        // when - trigger action
        recipesController.getRecipes(model);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // assert model attribute is added once
       // verify(model, times(1)).addAttribute(eq("recipes"), anySet());
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> recipeSetActual = argumentCaptor.getValue();
        assertEquals(2, recipeSetActual.size());
        // assert get recipes is called once
        verify(recipesService, times(1)).getRecipes();
        // assert the correct string is returned
        assertEquals("recipes/index", recipesController.getRecipes(model));


    }
}