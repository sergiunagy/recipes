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

import playground.springframework.recipes.commands.CategoryCommand;
import playground.springframework.recipes.commands.IngredientCommand;
import playground.springframework.recipes.commands.NotesCommand;
import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.domain.Difficulty;
import playground.springframework.recipes.services.IngredientsService;
import playground.springframework.recipes.services.RecipesService;
import playground.springframework.recipes.services.UnitOfMeasureService;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
class IngredientControllerTest {
    public static final Long RECIPE_ID = Long.valueOf(255L);
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe -test create";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID_2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;
    @Mock
    RecipesService recipesService;

    @Mock
    IngredientsService ingredientsService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    IngredientController controller;

    MockMvc mockMvc;

    RecipeCommand command;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        createDummyCommandObject();
    }

    @Test
    void listIngredients()throws Exception{

        when(recipesService.findRecipeCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/255/ingredients"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/ingredient/list"));
    }

    @Test
    void viewIngredient() throws Exception{
        IngredientCommand dummyIngredient = IngredientCommand.builder().id(255L).description("dummy").build();

        when(ingredientsService.findByRecipeIdAndId(anyLong(), anyLong())).thenReturn(dummyIngredient);

        mockMvc.perform(get("/recipe/255/ingredient/255/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipes/ingredient/show"));
    }



    private void createDummyCommandObject() {
        Set<IngredientCommand> ingredients = new HashSet<>();
        ingredients.add(new IngredientCommand().builder().id(INGRED_ID_1).build());
        ingredients.add(new IngredientCommand().builder().id(INGRED_ID_2).build());

        Set<CategoryCommand> categories = new HashSet<>();
        categories.add(new CategoryCommand().builder().id(CAT_ID_1).build());
        categories.add(new CategoryCommand().builder().id(CAT_ID_2).build());

        command = RecipeCommand.builder()
                .id(RECIPE_ID)
                .cookTime(COOK_TIME)
                .prepTime(PREP_TIME)
                .description(DESCRIPTION)
                .difficulty(DIFFICULTY)
                .directions(DIRECTIONS)
                .servings(SERVINGS)
                .source(SOURCE)
                .url(URL)
                .notes(new NotesCommand().builder().id(NOTES_ID).build())
                .ingredients(ingredients)
                .categories(categories)
                .build();
    }
}