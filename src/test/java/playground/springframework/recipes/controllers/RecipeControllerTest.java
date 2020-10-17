package playground.springframework.recipes.controllers;

import org.springframework.http.MediaType;
import playground.springframework.recipes.commands.CategoryCommand;
import playground.springframework.recipes.commands.IngredientCommand;
import playground.springframework.recipes.commands.NotesCommand;
import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.domain.Difficulty;
import playground.springframework.recipes.domain.Recipe;
import playground.springframework.recipes.services.RecipesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class RecipeControllerTest {

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

    RecipeCommand recipeCommand;

    @InjectMocks
    RecipeController controller;

    @Mock
    RecipesService recipesService;

    MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        createDummyCommandObject();
    }

    @Test
    void showById() throws Exception{

        Recipe recipe = Recipe.builder().id(1L).description("test").build();

        when(recipesService.findById(anyLong())).thenReturn(recipe);

        mvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void newRecipe() throws Exception {

        RecipeCommand command = RecipeCommand.builder().id(2L).build();

        mvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/recipeform"));
    }


    @Test
    void saveNewRecipe() throws Exception{

        // given
        // generic dummy command object

        when(recipesService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(recipeCommand);

        mvc.perform(post("/recipe" )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("description","Some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" +recipeCommand.getId()+ "/show"));

    }

    @Test
    void updateRecipe() throws Exception{
        // given
        // generic dummy command object

        //when-then
        when(recipesService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mvc.perform(get("/recipe/" + recipeCommand.getId() + "/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/recipeform"));
        //expect

    }

    private void createDummyCommandObject() {
        Set<IngredientCommand> ingredients = new HashSet<>();
        ingredients.add(new IngredientCommand().builder().id(INGRED_ID_1).build());
        ingredients.add(new IngredientCommand().builder().id(INGRED_ID_2).build());

        Set<CategoryCommand> categories = new HashSet<>();
        categories.add(new CategoryCommand().builder().id(CAT_ID_1).build());
        categories.add(new CategoryCommand().builder().id(CAT_ID_2).build());

        recipeCommand = RecipeCommand.builder()
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