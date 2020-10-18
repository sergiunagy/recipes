package playground.springframework.recipes.converters;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import playground.springframework.recipes.commands.IngredientCommand;
import playground.springframework.recipes.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Slf4j
@Getter
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        log.debug("In converter:" + ingredient);
         if (ingredient == null) {
             log.debug("Converter received null");
            return null;
        }

        IngredientCommand ingredientCommand = IngredientCommand.builder()
                .id(ingredient.getId())
                .recipeId((ingredient.getRecipe()!=null)?ingredient.getRecipe().getId():null)
                .description(ingredient.getDescription())
                .amount(ingredient.getAmount())
                .uom(uomConverter.convert(ingredient.getUom()))
                .build();

        return ingredientCommand;
    }
}