package guru.springframework.recipes.commands;

import lombok.*;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasureCommand {
    private Long id;
    private String description;
}
