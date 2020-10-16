package guru.springframework.recipes.converters;

import guru.springframework.recipes.commands.CategoryCommand;
import guru.springframework.recipes.commands.IngredientCommand;
import guru.springframework.recipes.commands.NotesCommand;
import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.domain.Difficulty;
import guru.springframework.recipes.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {
    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
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

    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = RecipeCommandToRecipe.builder()
                .categoryCommandToCategory(new CategoryCommandToCategory())
                .ingredientCommandToIngredient(new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()))
                .notesCommandToNotes(new NotesCommandToNotes())
                .build();
    }

    @Test
    void convert_null_source() {

        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Set<IngredientCommand> ingredients = new HashSet<>();
        ingredients.add(new IngredientCommand().builder().id(INGRED_ID_1).build());
        ingredients.add(new IngredientCommand().builder().id(INGRED_ID_2).build());

        Set<CategoryCommand> categories = new HashSet<>();
        categories.add(new CategoryCommand().builder().id(CAT_ID_1).build());
        categories.add(new CategoryCommand().builder().id(CAT_ID_2).build());

        RecipeCommand recipeCommand = RecipeCommand.builder()
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

        //when
        Recipe recipe = converter.convert(recipeCommand);

        //assert
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

}