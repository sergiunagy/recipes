package playground.springframework.recipes.converters;

import playground.springframework.recipes.commands.RecipeCommand;
import playground.springframework.recipes.domain.Category;
import playground.springframework.recipes.domain.Ingredient;
import playground.springframework.recipes.domain.Notes;
import playground.springframework.recipes.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final CategoryCommandToCategory categoryCommandToCategory;
    private final NotesCommandToNotes notesCommandToNotes;

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null) {
            return null;
        }


        Set<Category> categories = source.getCategories()
                .stream()
                .map(categoryCommand -> categoryCommandToCategory.convert(categoryCommand))
                .collect(Collectors.toSet());

        Notes notes = notesCommandToNotes.convert(source.getNotes());

        Set<Ingredient> ingredients = source.getIngredients()
                .stream()
                .map(ingredientCommand -> ingredientCommandToIngredient.convert(ingredientCommand))
                .collect(Collectors.toSet());

        Recipe recipe = Recipe.builder()
                .id(source.getId())
                .description(source.getDescription())
                .cookTime(source.getCookTime())
                .prepTime(source.getPrepTime())
                .servings(source.getServings())
                .source(source.getSource())
                .url(source.getUrl())
                .directions(source.getDirections())
                .difficulty(source.getDifficulty())
                .categories(categories)
                .notes(notes)
                .ingredients(ingredients)
                .build();


        return recipe;
    }
}
