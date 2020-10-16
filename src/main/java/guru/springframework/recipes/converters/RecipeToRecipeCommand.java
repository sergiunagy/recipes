package guru.springframework.recipes.converters;

import guru.springframework.recipes.commands.CategoryCommand;
import guru.springframework.recipes.commands.IngredientCommand;
import guru.springframework.recipes.commands.NotesCommand;
import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryToCategoryCommand;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    private final NotesToNotesCommand notesToNotesCommand;

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if(source == null) {
            return null;
        }

        Set<CategoryCommand> categories = source.getCategories()
                .stream()
                .map(category -> categoryToCategoryCommand.convert(category))
                .collect(Collectors.toSet());

        Set<IngredientCommand> ingredients = source.getIngredients()
                .stream()
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .collect(Collectors.toSet());

        NotesCommand notes = notesToNotesCommand.convert(source.getNotes());

        RecipeCommand recipe =  RecipeCommand.builder()
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
