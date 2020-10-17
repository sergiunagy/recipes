package playground.springframework.recipes.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;

    public static IngredientCommandBuilder builder() {
        return new IngredientCommandBuilder();
    }

    public static class IngredientCommandBuilder {
        private Long id;
        private String description;
        private BigDecimal amount;
        private UnitOfMeasureCommand unitOfMeasure;

        IngredientCommandBuilder() {
        }

        public IngredientCommandBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public IngredientCommandBuilder description(String description) {
            this.description = description;
            return this;
        }

        public IngredientCommandBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public IngredientCommandBuilder unitOfMeasure(UnitOfMeasureCommand unitOfMeasure) {
            this.unitOfMeasure = unitOfMeasure;
            return this;
        }

        public IngredientCommand build() {
            return new IngredientCommand(id, description, amount, unitOfMeasure);
        }

        public String toString() {
            return "IngredientCommand.IngredientCommandBuilder(id=" + this.id + ", description=" + this.description + ", amount=" + this.amount + ", unitOfMeasure=" + this.unitOfMeasure + ")";
        }
    }
}
