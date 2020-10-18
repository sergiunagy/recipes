package playground.springframework.recipes.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.services.RecipesService;

@ExtendWith(SpringExtension.class)
class IngredientControllerTest {

    @Mock
    RecipesService recipesService;

    @InjectMocks
    IngredientController controller;

    MockMvc mockMvc;

    RecipeCommand command;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        command = RecipeCommand.builder().id(255L).build();
    }

    @Test
    void listIngredients()throws Exception{

        when(recipesService.findRecipeCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/255/ingredients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/ingredient/list"));
    }
}