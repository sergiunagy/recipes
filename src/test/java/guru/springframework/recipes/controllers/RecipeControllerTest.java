package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.services.RecipesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RecipeControllerTest {

    @InjectMocks
    RecipeController controller;

    @Mock
    RecipesService recipesService;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showById() throws Exception{

        Recipe recipe = Recipe.builder().id(1L).description("test").build();

        when(recipesService.findById(anyLong())).thenReturn(recipe);

        mvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/show"))
                .andExpect(model().attributeExists("recipe"));
    }
}