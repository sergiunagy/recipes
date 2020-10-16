package guru.springframework.recipes.converters;

import guru.springframework.recipes.commands.CategoryCommand;
import guru.springframework.recipes.commands.IngredientCommand;
import guru.springframework.recipes.commands.NotesCommand;
import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RecipeToRecipeCommandTest {
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

    @Mock
    CategoryToCategoryCommand categoryToCategoryCommand;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    NotesToNotesCommand notesToNotesCommand;

    @InjectMocks
    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        // test if constructor still needed since we inject the mocks
    }

        
    @Test
    void convertNullSource() {

        assertNull(converter.convert(null));
    }   
    
    @Test
    void convertEmptySource() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient().builder().id(INGRED_ID_1).build());
        ingredients.add(new Ingredient().builder().id(INGRED_ID_2).build());

        Set<Category> categories = new HashSet<>();
        categories.add(new Category().builder().id(CAT_ID_1).build());
        categories.add(new Category().builder().id(CAT_ID_2).build());

        Recipe recipeCommand = Recipe.builder()
                .id(RECIPE_ID)
                .cookTime(COOK_TIME)
                .prepTime(PREP_TIME)
                .description(DESCRIPTION)
                .difficulty(DIFFICULTY)
                .directions(DIRECTIONS)
                .servings(SERVINGS)
                .source(SOURCE)
                .url(URL)
                .notes(new Notes().builder().id(NOTES_ID).build())
                .ingredients(ingredients)
                .categories(categories)
                .build();
    }
}