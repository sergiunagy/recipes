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
    private UnitOfMeasureCommand uom;

    public static IngredientCommandBuilder builder() {
        return new IngredientCommandBuilder();
    }

    public static class IngredientCommandBuilder {
        private Long id;
        private String description;
        private BigDecimal amount;
        private UnitOfMeasureCommand uom;

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
            this.uom = unitOfMeasure;
            return this;
        }

        public IngredientCommand build() {
            return new IngredientCommand(id, description, amount, uom);
        }

        public String toString() {
            return "IngredientCommand.IngredientCommandBuilder(id=" + this.id + ", description=" + this.description + ", amount=" + this.amount + ", unitOfMeasure=" + this.uom + ")";
        }
    }
}
